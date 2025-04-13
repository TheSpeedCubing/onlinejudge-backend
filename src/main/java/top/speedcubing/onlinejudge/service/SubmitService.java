package top.speedcubing.onlinejudge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.LanguageSelector;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.Verdict;
import top.speedcubing.onlinejudge.data.compile.CompileResult;
import top.speedcubing.onlinejudge.data.exception.exception.BadRequestException;
import top.speedcubing.onlinejudge.data.exception.errorresponse.ErrorResponseList;
import top.speedcubing.onlinejudge.data.exception.exception.ProblemNotFoundException;
import top.speedcubing.onlinejudge.data.exception.exception.UnsupportedLanguageException;
import top.speedcubing.onlinejudge.data.execute.ExecuteResult;
import top.speedcubing.onlinejudge.data.execute.ExecuteSession;
import top.speedcubing.onlinejudge.data.run.RunResult;
import top.speedcubing.onlinejudge.data.submit.SubmitResult;
import top.speedcubing.onlinejudge.data.submit.request.AbstractSubmitRequest;
import top.speedcubing.onlinejudge.data.submit.request.SubmitTestRequest;
import top.speedcubing.onlinejudge.utils.FileUtils;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Service
public class SubmitService {

    @Autowired
    private LanguageSelector languageSelector;
    int i = 0;

    public SubmitResult submit(AbstractSubmitRequest request) {
        ErrorResponseList errorResponseList = new ErrorResponseList();

        // get problem
        String problemId = request.getProblemId();
        if (problemId.equals("0")) {
            errorResponseList.add(new ProblemNotFoundException(problemId));
        }

        // get language
        String language = request.getLanguage();

        IExecutor compiler = null;
        try {
            compiler = languageSelector.get(language);
        } catch (UnsupportedLanguageException ex) {
            errorResponseList.add(ex);
        }

        if (!errorResponseList.isEmpty()) {
            throw new BadRequestException(errorResponseList);
        }

        // get stdin
        String stdin = "";
        if (request instanceof SubmitTestRequest r) {
            stdin = r.getStdin();
        }

        String code = request.getCode();

        try {
            // isolate environment
            String tempDir = "isolate-temp" + "-" + (i++) + "/";
            String absTempDir = "/app/" + tempDir;
            ShellExecutor.exec("rm -r " + absTempDir);
            ShellExecutor.exec("mkdir " + absTempDir);

            String box = ShellExecutor.execAt(absTempDir, "isolate --init");
            box = box.substring(0, box.length() - 1) + "/box/";

            // prepare executor
            ExecuteSession executeSession = new ExecuteSession(box, absTempDir, code, stdin, 5120000);
            compiler.init(executeSession);

            // prepare I/O file
            FileUtils.write(box, "input.txt", stdin);
            executeSession.executeInBox("touch compile_stdout.txt");
            executeSession.executeInBox("touch compile_stderr.txt");
            executeSession.executeInBox("touch stdout.txt");
            executeSession.executeInBox("touch stderr.txt");


            SubmitResult submitResult = new SubmitResult();

            ExecuteResult executeResult = new ExecuteResult();
            submitResult.setExecuteResult(executeResult);

            // compile
            CompileResult compileResult = compiler.compile(executeSession);
            submitResult.getExecuteResult().setCompileResult(compileResult);
            if (!compileResult.isSuccess()) {
                submitResult.setVerdict(Verdict.CE);
                return submitResult;
            }

            // run
            RunResult runResult = compiler.run(executeSession);
            submitResult.getExecuteResult().setRunResult(runResult);
            if (!runResult.isSuccess()) {
                submitResult.setVerdict(Verdict.RE);
                return submitResult;
            }

            submitResult.setVerdict(Verdict.AC);
            return submitResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
