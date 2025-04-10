package top.speedcubing.onlinejudge.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.speedcubing.onlinejudge.data.submit.SubmitRequest;
import top.speedcubing.onlinejudge.data.submit.SubmitResult;
import top.speedcubing.onlinejudge.service.SubmitService;

@RestController
@RequestMapping("/api/submission")
@Tag(name = "Submission API", description = "description")
public class SubmitController {

    @Autowired
    SubmitService submitService;

    @PostMapping("/submit")
    @ResponseBody
    @Operation(summary = "summary", description = "description")
    public SubmitResult runCode(@RequestBody SubmitRequest request) {
        Integer problemId = request.getProblemId();
        String stdin = request.getStdin();
        String code = request.getCode();
        String language = request.getLanguage();

        return submitService.submit(problemId, stdin, code, language);
    }
}