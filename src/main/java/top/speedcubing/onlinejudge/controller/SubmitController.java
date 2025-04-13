package top.speedcubing.onlinejudge.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.speedcubing.onlinejudge.data.execute.ExecuteRequest;
import top.speedcubing.onlinejudge.data.execute.ExecuteResult;
import top.speedcubing.onlinejudge.data.submit.request.SubmitSubmitRequest;
import top.speedcubing.onlinejudge.data.submit.SubmitResult;
import top.speedcubing.onlinejudge.data.submit.request.SubmitRandomRequest;
import top.speedcubing.onlinejudge.data.submit.request.SubmitTestRequest;
import top.speedcubing.onlinejudge.service.ExecuteService;
import top.speedcubing.onlinejudge.service.SubmitService;

@RestController
@RequestMapping("/api/submission")
@Tag(name = "Submission API", description = "description")
public class SubmitController {

    @Autowired
    SubmitService submitService;

    @Autowired
    ExecuteService executeService;

    @PostMapping("/execute")
    @ResponseBody
    @Operation(summary = "summary", description = "execute code with custom stdin")
    public ExecuteResult execute(@RequestBody ExecuteRequest request) {
        return executeService.execute(request);
    }

    @PostMapping("/finalsubmit")
    @ResponseBody
    @Operation(summary = "summary", description = "submit code to see if the code is correct")
    public SubmitResult finalsubmit(@RequestBody SubmitSubmitRequest request) {
        return submitService.submit(request);
    }

    @PostMapping("/randomsubmit")
    @ResponseBody
    @Operation(summary = "summary", description = "submit code with server-generated stdin")
    public SubmitResult randomsubmit(@RequestBody SubmitRandomRequest request) {
        return submitService.submit(request);
    }

    @PostMapping("/testsubmit")
    @ResponseBody
    @Operation(summary = "summary", description = "submit code with custom stdin")
    public SubmitResult testsubmit(@RequestBody SubmitTestRequest request) {
        return submitService.submit(request);
    }
}