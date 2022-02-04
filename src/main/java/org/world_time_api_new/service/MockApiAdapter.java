package org.world_time_api_new.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.world_time_api_new.exception.WorldTimeApiException;
import org.world_time_api_new.model.DateTimeResponseFormat;
import org.world_time_api_new.model.MockTimeDto;
import org.world_time_api_new.model.WorldTimeDto;
import reactor.core.publisher.Mono;

@Component
@Profile("local")
@RequiredArgsConstructor
@Log4j2
public class MockApiAdapter implements ExternalApi{
    private final WebClient webClient;
    private final DatetimeFormatter datetimeFormatter;

    @Override
    public WorldTimeDto getWorldTime(String locationName, DateTimeResponseFormat format) {
        MockTimeDto response = requestWorldTime(webClient, locationName);
        String formattedDateTime = datetimeFormatter.formatLocalDateAndLocalTime(
                response.getDate(), response.getTime(), format, response.getTimezone()
        );
       return WorldTimeDto.builder()
               .datetime(formattedDateTime)
               .timezone(response.getTimezone())
               .build();
    }

    private MockTimeDto requestWorldTime(WebClient client, String location) {
        return client.get().uri(location)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        Mono.error(new WorldTimeApiException(clientResponse.statusCode(), "Some error of mock api occurred")))
                .bodyToMono(MockTimeDto.class)
                .block();
    }
}
