package top.speedcubing.onlinejudge.data.exception.errorresponse;

import java.util.ArrayList;
import java.util.List;
import top.speedcubing.onlinejudge.data.exception.Convertible;

public class ErrorResponseList {

    private final List<ErrorResponse> errors = new ArrayList<>();

    public List<ErrorResponse> asList() {
        return errors;
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }

    public void add(Convertible exception) {
        errors.add(exception.toResponse());
    }

    public ErrorResponseList(Convertible... excpetion) {
        for(Convertible e : excpetion) {
            add(e);
        }
    }
}
