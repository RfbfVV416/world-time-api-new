package org.world_time_api_new.service;

import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

public interface RequestFailureHandler {
    Throwable handleIntermediateRequest(WebClientResponseException e);
    Throwable handleRequest(WebClientResponseException e);
    Throwable handleExhaustedRequest(RetryBackoffSpec retryBackoffSpec, Retry.RetrySignal retrySignal);
}
