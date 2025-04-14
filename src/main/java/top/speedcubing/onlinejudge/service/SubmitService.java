package top.speedcubing.onlinejudge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.data.Verdict;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteRequest;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteResponse;
import top.speedcubing.onlinejudge.data.dto.problem.Problem;
import top.speedcubing.onlinejudge.data.dto.submit.request.AbstractSubmitRequest;
import top.speedcubing.onlinejudge.data.dto.submit.request.SubmitSampleRequest;
import top.speedcubing.onlinejudge.data.dto.submit.request.SubmitTestRequest;
import top.speedcubing.onlinejudge.data.dto.submit.response.SubmitResponse;
import top.speedcubing.onlinejudge.exception.errorresponse.ErrorResponseList;
import top.speedcubing.onlinejudge.exception.exception.BadRequestException;
import top.speedcubing.onlinejudge.exception.exception.ProblemNotFoundException;
import top.speedcubing.onlinejudge.exception.exception.UnsupportedLanguageException;

@Service
public class SubmitService {

    @Autowired
    LanguageService languageService;

    @Autowired
    ExecuteService executeService;

    @Autowired
    ProblemService problemService;

    public SubmitResponse submit(AbstractSubmitRequest request) {
        ErrorResponseList errorResponseList = new ErrorResponseList();

        // get problem
        String problemId = request.getProblemId();
        Problem problem = null;
        try {
            problem = problemService.get(problemId);
        } catch (ProblemNotFoundException ex) {
            errorResponseList.add(ex);
        }
        // check language
        String language = request.getSourceCode().getLanguage();
        try {
            languageService.get(language);
        } catch (UnsupportedLanguageException ex) {
            errorResponseList.add(ex);
        }
        // throw Exception
        if (!errorResponseList.isEmpty()) {
            throw new BadRequestException(errorResponseList);
        }

        if (problem == null) {
            return null;
        }

        // get stdin
        String stdin = "";
        if (request instanceof SubmitTestRequest r) {
            stdin = r.getStdin();
        }
        if (request instanceof SubmitSampleRequest) {
            stdin = problem.getSampleInput();
        }

        SubmitResponse submitResponse = new SubmitResponse();

        ExecuteResponse executeResponse = executeService.execute(new ExecuteRequest(stdin, request.getSourceCode()), true);
        submitResponse.setExecuteResponse(executeResponse);

        if (!executeResponse.getCompileResult().isSuccess()) {
            submitResponse.setVerdict(Verdict.CE);
            return submitResponse;
        }
        if (!executeResponse.getRunResponse().isSuccess()) {
            submitResponse.setVerdict(Verdict.RE);
            return submitResponse;
        }

        ExecuteResponse officialExecuteResponse = executeService.execute(new ExecuteRequest(stdin, problemService.getAnswer(problem,language)), false);
        submitResponse.setOfficialExecuteResponse(officialExecuteResponse);

        if(!executeResponse.getCompileResult().isSuccess()) {
            submitResponse.setVerdict(Verdict.ARE);
            return submitResponse;
        }

        if(!executeResponse.getRunResponse().getStdout().equals(officialExecuteResponse.getRunResponse().getStdout())) {
            submitResponse.setVerdict(Verdict.WA);
        } else {
            submitResponse.setVerdict(Verdict.AC);
        }

        return submitResponse;
    }
}
