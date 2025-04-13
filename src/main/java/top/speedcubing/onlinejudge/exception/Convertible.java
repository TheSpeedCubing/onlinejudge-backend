package top.speedcubing.onlinejudge.exception;

import top.speedcubing.onlinejudge.exception.errorresponse.ErrorResponse;

public interface Convertible {
    ErrorResponse toResponse();
}
