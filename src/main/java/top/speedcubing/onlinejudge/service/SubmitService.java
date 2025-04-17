package top.speedcubing.onlinejudge.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

    @Async("asyncThreadPoolExecutor")
    public CompletableFuture<SubmitResult> submit(AbstractSubmitRequest request) {
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
        try {
            CompletableFuture<ExecuteResult> executeFuture = executeService.execute(new ExecuteRequest(stdin, request.getSourceCode()), true).exceptionally(ex -> {
                ex.printStackTrace();
                return null;
            });
            ExecuteResult executeResult = executeFuture.join();

            if (!executeResult.getCompileResult().isSuccess()) {
                return CompletableFuture.completedFuture(new SubmitResult(executeResult, Verdict.CE));
            }
            if (!executeResult.getRunResult().isSuccess()) {
                return CompletableFuture.completedFuture(new SubmitResult(executeResult, Verdict.RE));
            }

            CompletableFuture<ExecuteResult> officialExecuteFuture = executeService.execute(new ExecuteRequest(stdin, problemService.getAnswer(problem, language)), false).exceptionally(ex -> {
                ex.printStackTrace();
                return null;
            });

            ExecuteResult officialExecuteResult = officialExecuteFuture.join();

            SubmitResult submitResult = new SubmitResult(executeResult, officialExecuteResult);

            if (!officialExecuteResult.getRunResult().isSuccess()) {
                submitResult.setVerdict(Verdict.ARE);
                return CompletableFuture.completedFuture(submitResult);
            }

            if (!executeResult.getRunResult().getStdout().equals(officialExecuteResult.getRunResult().getStdout())) {
                submitResult.setVerdict(Verdict.WA);
                submitResult.setDiff(ShellExecutor.exec(
                        "diff -u --label \"your output\" %sstdout.txt --label \"correct output\" %sstdout.txt"
                                .formatted(executeResult.getBox().getAbsBoxDir(), officialExecuteResult.getBox().getAbsBoxDir())
                ));
            } else {
                submitResult.setVerdict(Verdict.AC);
            }
            return CompletableFuture.completedFuture(submitResult);
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
