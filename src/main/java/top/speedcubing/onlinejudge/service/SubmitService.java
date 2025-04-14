package top.speedcubing.onlinejudge.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.data.Verdict;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteRequest;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteResult;
import top.speedcubing.onlinejudge.data.dto.problem.Problem;
import top.speedcubing.onlinejudge.data.dto.submit.request.AbstractSubmitRequest;
import top.speedcubing.onlinejudge.data.dto.submit.request.SubmitSampleRequest;
import top.speedcubing.onlinejudge.data.dto.submit.request.SubmitTestRequest;
import top.speedcubing.onlinejudge.data.dto.submit.response.SubmitResult;
import top.speedcubing.onlinejudge.exception.errorresponse.ErrorResponseList;
import top.speedcubing.onlinejudge.exception.exception.BadRequestException;
import top.speedcubing.onlinejudge.exception.exception.ProblemNotFoundException;
import top.speedcubing.onlinejudge.exception.exception.UnsupportedLanguageException;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Service
public class SubmitService {

    @Autowired
    LanguageService languageService;

    @Autowired
    ExecuteService executeService;

    @Autowired
    ProblemService problemService;

    public SubmitResult submit(AbstractSubmitRequest request) {
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

        SubmitResult submitResult = new SubmitResult();

        ExecuteResult executeResult = executeService.execute(new ExecuteRequest(stdin, request.getSourceCode()), true);
        submitResult.setExecuteResult(executeResult);

        if (!executeResult.getCompileResult().isSuccess()) {
            submitResult.setVerdict(Verdict.CE);
            return submitResult;
        }
        if (!executeResult.getRunResult().isSuccess()) {
            submitResult.setVerdict(Verdict.RE);
            return submitResult;
        }

        ExecuteResult officialExecuteResult = executeService.execute(new ExecuteRequest(stdin, problemService.getAnswer(problem, language)), false);
        submitResult.setOfficialExecuteResult(officialExecuteResult);

        if (!executeResult.getCompileResult().isSuccess()) {
            submitResult.setVerdict(Verdict.ARE);
            return submitResult;
        }

        if (!executeResult.getRunResult().getStdout().equals(officialExecuteResult.getRunResult().getStdout())) {
            submitResult.setVerdict(Verdict.WA);
            try {
                submitResult.setDiff(ShellExecutor.exec("diff -u --label \"your output\" %sstdout.txt --label \"correct output\" %sstdout.txt".formatted(executeResult.getBox().getAbsBoxDir(), officialExecuteResult.getBox().getAbsBoxDir())));
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } else {
            submitResult.setVerdict(Verdict.AC);
        }

        return submitResult;
    }
}
