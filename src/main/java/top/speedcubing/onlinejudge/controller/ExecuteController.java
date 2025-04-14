package top.speedcubing.onlinejudge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteRequest;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteResult;
import top.speedcubing.onlinejudge.service.ExecuteService;

@RestController
@RequestMapping("/api/execution")
@Tag(name = "Execution API", description = "execute your codes")
public class ExecuteController {

    @Autowired
    ExecuteService executeService;

    @PostMapping("/execute")
    @ResponseBody
    @Operation(summary = "Execute code with custom stdin", description = "Execute code with custom stdin")
    public ExecuteResult execute(@RequestBody ExecuteRequest request) {
        return executeService.execute(request, true);
    }
}
