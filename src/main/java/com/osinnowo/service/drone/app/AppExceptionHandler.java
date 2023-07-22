package com.osinnowo.service.drone.app;

import com.osinnowo.service.drone.exception.*;
import com.osinnowo.service.drone.models.common.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final Mono<ResponseEntity<BaseResponse<?>>> handleNotFoundException(Exception ex, ServerWebExchange exchange) {
        return Mono.just(new ResponseEntity<>(BaseResponse.fail(ex.getMessage()), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(WeightLimitExceededException.class)
    public final Mono<ResponseEntity<BaseResponse<?>>> handleWeightLimitException(Exception ex, ServerWebExchange exchange) {
        return Mono.just(new ResponseEntity<>(BaseResponse.fail(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(DroneBatteryLowException.class)
    public final Mono<ResponseEntity<BaseResponse<?>>> handleBatteryLowException(Exception ex, ServerWebExchange exchange) {
        return Mono.just(new ResponseEntity<>(BaseResponse.fail(ex.getMessage()), HttpStatus.SERVICE_UNAVAILABLE));
    }

    @ExceptionHandler(DuplicateItemException.class)
    public final Mono<ResponseEntity<BaseResponse<?>>> handleDuplicateItemException(Exception ex, ServerWebExchange exchange) {
        return Mono.just(new ResponseEntity<>(BaseResponse.fail(ex.getMessage()), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(DroneNotAvailableException.class)
    public final Mono<ResponseEntity<BaseResponse<?>>> handleDroneNotAvailableException(Exception ex, ServerWebExchange exchange) {
        return Mono.just(new ResponseEntity<>(BaseResponse.fail(ex.getMessage()), HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<Object>> handleWebExchangeBindException(WebExchangeBindException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        handleExceptionInternal(ex, null, headers, status, exchange);
        return Mono.just(new ResponseEntity<>(BaseResponse.fail(ex.getFieldError().getDefaultMessage()), HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Override
    public Mono<ResponseEntity<Object>> handleMethodNotAllowedException(MethodNotAllowedException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        handleExceptionInternal(ex, null, headers, status, exchange);
        return Mono.just(new ResponseEntity<>(BaseResponse.fail(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY));
    }
}
