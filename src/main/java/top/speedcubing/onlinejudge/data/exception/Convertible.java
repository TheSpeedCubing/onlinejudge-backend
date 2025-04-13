package top.speedcubing.onlinejudge.data.exception;

import top.speedcubing.onlinejudge.data.exception.errorresponse.ErrorResponse;

public interface Convertible {
    ErrorResponse toResponse();
}
