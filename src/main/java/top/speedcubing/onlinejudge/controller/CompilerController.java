package top.speedcubing.onlinejudge.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.dto.compiler.CompilerInfoRequest;
import top.speedcubing.onlinejudge.data.dto.compiler.CompilerInfoResponse;
import top.speedcubing.onlinejudge.service.LanguageService;

@RestController
@RequestMapping("/api/compiler")
@Tag(name = "Compiler Info API", description = "compiler's info")
public class CompilerController {

    @Autowired
    LanguageService languageService;

    @PostMapping("/info")
    @ResponseBody
    @Operation(summary = "Get compiler's info", description = "Get compiler's info")
    public CompilerInfoResponse execute(@RequestBody CompilerInfoRequest compilerInfoRequest) {
        IExecutor executor = languageService.get(compilerInfoRequest.getLanguage());
        return new CompilerInfoResponse(executor.getVersionString(), executor.getCompileCommand(), executor.getRunCommand());
    }
}
