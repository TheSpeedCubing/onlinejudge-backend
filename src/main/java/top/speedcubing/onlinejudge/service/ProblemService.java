package top.speedcubing.onlinejudge.service;

import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.data.dto.problem.Problem;
import top.speedcubing.onlinejudge.data.dto.problem.ProblemInfoRequest;
import top.speedcubing.onlinejudge.data.dto.problem.ProblemInfoResponse;


@Service
public class ProblemService {
    public ProblemInfoResponse search(ProblemInfoRequest request) {
        // TODO
        return new ProblemInfoResponse(new Problem(request.getProblemId()));
    }
}
