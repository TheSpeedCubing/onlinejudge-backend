package top.speedcubing.onlinejudge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.LanguageSelector;
import top.speedcubing.onlinejudge.data.Verdict;
import top.speedcubing.onlinejudge.data.dto.submit.request.AbstractSubmitRequest;
import top.speedcubing.onlinejudge.data.dto.submit.request.SubmitTestRequest;
import top.speedcubing.onlinejudge.data.dto.submit.response.SubmitResponse;
import top.speedcubing.onlinejudge.exception.errorresponse.ErrorResponseList;
import top.speedcubing.onlinejudge.exception.exception.BadRequestException;
import top.speedcubing.onlinejudge.exception.exception.ProblemNotFoundException;
import top.speedcubing.onlinejudge.exception.exception.UnsupportedLanguageException;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteRequest;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteResponse;

@Service
public class SubmitService {

    @Autowired
    LanguageSelector languageSelector;

    @Autowired
    ExecuteService executeService;

    public SubmitResponse submit(AbstractSubmitRequest request) {
        ErrorResponseList errorResponseList = new ErrorResponseList();

        // get problem
        String problemId = request.getProblemId();
        if (problemId.equals("0")) {
            errorResponseList.add(new ProblemNotFoundException(problemId));
        }

        // check language
        String language = request.getSourceCode().getLanguage();
        try {
            languageSelector.checkLanguage(language);
        } catch (UnsupportedLanguageException ex) {
            errorResponseList.add(ex);
        }

        // throw Exception
        if (!errorResponseList.isEmpty()) {
            throw new BadRequestException(errorResponseList);
        }

        // get stdin
        String stdin = "";
        if (request instanceof SubmitTestRequest r) {
            stdin = r.getStdin();
        }

        ExecuteResponse executeResponse = executeService.execute(new ExecuteRequest(stdin, request.getSourceCode()));

        SubmitResponse submitResponse = new SubmitResponse(executeResponse);

        if (!executeResponse.getCompileResult().isSuccess()) {
            submitResponse.setVerdict(Verdict.CE);
            return submitResponse;
        }

        if (!executeResponse.getRunResponse().isSuccess()) {
            submitResponse.setVerdict(Verdict.RE);
            return submitResponse;
        }

        submitResponse.setVerdict(Verdict.AC);
        return submitResponse;
    }
}
