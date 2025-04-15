package top.speedcubing.onlinejudge.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.speedcubing.onlinejudge.data.dto.submit.request.SubmitRandomRequest;
import top.speedcubing.onlinejudge.data.dto.submit.request.SubmitSampleRequest;
import top.speedcubing.onlinejudge.data.dto.submit.request.SubmitSubmitRequest;
import top.speedcubing.onlinejudge.data.dto.submit.request.SubmitTestRequest;
import top.speedcubing.onlinejudge.data.dto.submit.response.SubmitResult;
import top.speedcubing.onlinejudge.service.SubmitService;

@RestController
@RequestMapping("/api/submission")
@Tag(name = "Submission API", description = "submit your answer to the problems")
public class SubmitController {

    @Autowired
    SubmitService submitService;

    @PostMapping("/finalsubmit")
    @ResponseBody
    @Operation(summary = "Submit code to see if the code is correct", description = "Submit code to see if the code is correct")
    public SubmitResult finalsubmit(@RequestBody SubmitSubmitRequest request) {
        return submitService.submit(request).join();
    }

    @PostMapping("/randomsubmit")
    @ResponseBody
    @Operation(summary = "Submit code with server-generated stdin", description = "Submit code with server-generated stdin")
    public SubmitResult randomsubmit(@RequestBody SubmitRandomRequest request) {
        return submitService.submit(request).join();
    }

    @PostMapping("/samplesubmit")
    @ResponseBody
    @Operation(summary = "Submit code with sample stdin", description = "Submit code with sample stdin")
    public SubmitResult samplesubmit(@RequestBody SubmitSampleRequest request) {
        return submitService.submit(request).join();
    }

    @PostMapping("/testsubmit")
    @ResponseBody
    @Operation(summary = "Submit code with custom stdin", description = "Submit code with custom stdin")
    public SubmitResult testsubmit(@RequestBody SubmitTestRequest request) {
        return submitService.submit(request).join();
    }
}