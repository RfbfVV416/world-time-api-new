package org.world_time_api_new.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.world_time_api_new.exception.WrongAreaException;
import org.world_time_api_new.model.DateTimeResponseFormat;
import org.world_time_api_new.model.WorldTimeDto;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
@Profile("prod")
@RequiredArgsConstructor
@Log4j2
public class WorldTimeApiAdapter implements ExternalApi{
    private final WebClient webClient;
    private final RequestFailureHandler requestFailureHandler;
    private final DatetimeFormatter datetimeFormatter;

    @Override
    public WorldTimeDto getWorldTime(String locationName, DateTimeResponseFormat format) {
        WorldTimeDto response;
        try {
            response =
                    requestWorldTime(webClient, "Europe/", locationName,
                            this.requestFailureHandler::handleExhaustedRequest,
                            this.requestFailureHandler::handleIntermediateRequest);
        }
        catch (WrongAreaException e){
            log.error("Unknown location for Europe area", e);
            response =
                    requestWorldTime(webClient, "Asia/", locationName,
                            this.requestFailureHandler::handleExhaustedRequest,
                            this.requestFailureHandler::handleRequest);
        }
        response.setDatetime(
                datetimeFormatter.formatOffsetDateTime(response.getDatetime(), format, response.getTimezone())
        );
        return response;
    }

    private WorldTimeDto requestWorldTime(WebClient client, String area, String location,
                                                  BiFunction<RetryBackoffSpec, Retry.RetrySignal, Throwable> mapper,
                                                  Function<? super WebClientResponseException, ? extends Throwable> webClientResponseMapper) {

        return client.get().uri(area + location)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WorldTimeDto.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorMap(WebClientResponseException.class, webClientResponseMapper)
                .retryWhen(
                        Retry.backoff(2, Duration.ofMillis(2))
                                .filter(throwable -> throwable instanceof TimeoutException || throwable instanceof IOException)
                                .onRetryExhaustedThrow(mapper)
                )
                .block();
    }
}
