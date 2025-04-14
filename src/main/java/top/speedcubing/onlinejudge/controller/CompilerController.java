package top.speedcubing.onlinejudge.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.speedcubing.onlinejudge.service.LanguageService;
import top.speedcubing.onlinejudge.data.dto.compiler.CompilerVersionRequest;
import top.speedcubing.onlinejudge.data.dto.compiler.CompilerVersionResponse;

@RestController
@RequestMapping("/api/compilerinfo")
@Tag(name = "Compiler Info API", description = "compiler's info")
public class CompilerController {

    @Autowired
    LanguageService languageService;

    @PostMapping("/version")
    @ResponseBody
    @Operation(summary = "Get compiler's version", description = "Get compiler's version")
    public CompilerVersionResponse execute(@RequestBody CompilerVersionRequest language) {
        return new CompilerVersionResponse(languageService.get(language.getLanguage()).getVersionString());
    }
}
