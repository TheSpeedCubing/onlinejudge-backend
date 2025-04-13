package top.speedcubing.onlinejudge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.LanguageSelector;
import top.speedcubing.onlinejudge.data.SourceCode;
import top.speedcubing.onlinejudge.data.Verdict;
import top.speedcubing.onlinejudge.data.exception.errorresponse.ErrorResponseList;
import top.speedcubing.onlinejudge.data.exception.exception.BadRequestException;
import top.speedcubing.onlinejudge.data.exception.exception.ProblemNotFoundException;
import top.speedcubing.onlinejudge.data.exception.exception.UnsupportedLanguageException;
import top.speedcubing.onlinejudge.data.execute.ExecuteRequest;
import top.speedcubing.onlinejudge.data.execute.ExecuteResult;
import top.speedcubing.onlinejudge.data.submit.SubmitResult;
import top.speedcubing.onlinejudge.data.submit.request.AbstractSubmitRequest;
import top.speedcubing.onlinejudge.data.submit.request.SubmitTestRequest;

@Service
public class SubmitService {

    @Autowired
    LanguageSelector languageSelector;

    @Autowired
    ExecuteService executeService;

    public SubmitResult submit(AbstractSubmitRequest request) {
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

        ExecuteResult executeResult = executeService.execute(new ExecuteRequest(stdin, request.getSourceCode()));

        SubmitResult submitResult = new SubmitResult(executeResult);

        if (!executeResult.getCompileResult().isSuccess()) {
            submitResult.setVerdict(Verdict.CE);
            return submitResult;
        }

        if (!executeResult.getRunResult().isSuccess()) {
            submitResult.setVerdict(Verdict.RE);
            return submitResult;
        }

        submitResult.setVerdict(Verdict.AC);
        return submitResult;
    }
}
