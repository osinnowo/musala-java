package com.osinnowo.service.drone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DroneNotAvailableException extends RuntimeException {
    public DroneNotAvailableException(String message) {
        super(message);
    }
}
