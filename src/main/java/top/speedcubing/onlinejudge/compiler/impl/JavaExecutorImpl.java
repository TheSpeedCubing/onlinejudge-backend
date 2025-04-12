package top.speedcubing.onlinejudge.compiler.impl;

import java.io.IOException;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.execute.ExecuteSession;
import top.speedcubing.onlinejudge.utils.FileUtils;

@Service
public class JavaExecutorImpl implements IExecutor {

    @Override
    public void execute(ExecuteSession executeSession) throws IOException, InterruptedException {
        // source code
        FileUtils.write(executeSession.getAbsBox(), "Main.java", executeSession.getCode());

        // compile
        executeSession.execute("isolate --processes --dir=etc --meta=compile.meta --stdout=compile_stdout.txt --stderr=compile_stderr.txt --run -- /usr/bin/javac Main.java");
        executeSession.loadCompileMeta();
        String exitcode = executeSession.getCompileMeta("exitcode");
        if (!exitcode.equals("0")) {
            return;
        }

        // X
        executeSession.execute("isolate --processes --mem=%d --dir=etc --meta=execute.meta --stdin=input.txt --stdout=stdout.txt --stderr=stderr.txt --run -- /usr/bin/java -Xmx128M Main".formatted(executeSession.getMemoryLimit()));
        executeSession.loadExecuteMeta();
    }
}
