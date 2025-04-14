package top.speedcubing.onlinejudge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.speedcubing.onlinejudge.data.dto.problem.ProblemInfoRequest;
import top.speedcubing.onlinejudge.data.dto.problem.ProblemInfoResponse;
import top.speedcubing.onlinejudge.data.dto.submit.request.SubmitSubmitRequest;
import top.speedcubing.onlinejudge.data.dto.submit.response.SubmitResponse;
import top.speedcubing.onlinejudge.service.ProblemService;
import top.speedcubing.onlinejudge.service.SubmitService;
@RestController
@RequestMapping("/api/problem")
@Tag(name = "Problem API", description = "browse problem")
public class ProblemController {

    @Autowired
    ProblemService problemService;

    @PostMapping("/info")
    @ResponseBody
    @Operation(summary = "Get problem info", description = "Get problem info")
    public ProblemInfoResponse finalsubmit(@RequestBody ProblemInfoRequest request) {
        return problemService.search(request);
    }
}