package org.world_time_api_new.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.world_time_api_new.exception.WorldTimeApiException;
import org.world_time_api_new.exception.WrongAreaException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

@Service
public class RequestFailureHandlerImpl implements RequestFailureHandler {
    @Override
    public Throwable handleIntermediateRequest(WebClientResponseException e) {
        HttpStatus status = e.getStatusCode();
        String message = e.getStatusText();

        if (HttpStatus.NOT_FOUND.equals(status)){
            return new WrongAreaException(message, e);
        }
        return new WorldTimeApiException(e.getStatusCode(), e.getStatusText());
    }

    @Override
    public Throwable handleRequest(WebClientResponseException e) {
        return new WorldTimeApiException(e.getStatusCode(), e.getStatusText());
    }

    @Override
    public Throwable handleExhaustedRequest(RetryBackoffSpec retryBackoffSpec, Retry.RetrySignal retrySignal){
        return new WorldTimeApiException(HttpStatus.SERVICE_UNAVAILABLE, retrySignal.failure().getMessage());
    }
}
