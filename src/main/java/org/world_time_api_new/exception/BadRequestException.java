package org.world_time_api_new.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {
    public BadRequestException(String message){ super(HttpStatus.BAD_REQUEST, message); }
}
