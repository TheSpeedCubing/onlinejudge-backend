package top.speedcubing.onlinejudge.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.data.dto.problem.Problem;

@Service
public class ProblemPoolService {

    private final Cache<String, Problem> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(100)
            .build();

    @Autowired
    private ProblemService problemService;

    public Problem get(String problemId) {
        return cache.get(problemId, problemService::get);
    }

    public void invalidate(String problemId) {
        cache.invalidate(problemId);
    }

    public void clear() {
        cache.invalidateAll();
    }

    public void refresh(String problemId) {
        cache.put(problemId, problemService.get(problemId));
    }
}