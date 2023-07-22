package com.osinnowo.service.drone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DroneBatteryLowException extends RuntimeException {
    public DroneBatteryLowException(String message) {
        super(message);
    }
}
