package org.world_time_api_new.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DatetimeParseException extends ResponseStatusException {
    public DatetimeParseException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }
}
