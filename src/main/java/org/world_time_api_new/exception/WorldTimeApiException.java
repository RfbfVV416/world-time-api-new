package org.world_time_api_new.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WorldTimeApiException extends ResponseStatusException {
    public WorldTimeApiException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
