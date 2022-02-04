package org.world_time_api_new.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.world_time_api_new.model.DateTimeResponseFormat;
import org.world_time_api_new.model.WorldTimeDto;
import org.world_time_api_new.service.ExternalApi;

@RestController
@RequiredArgsConstructor
public class WorldTimeController {

    private final ExternalApi externalApi;

    @GetMapping("/{location}")
    private WorldTimeDto getWorldTime(@PathVariable String location,
                                      @RequestParam(defaultValue = "ISO_LOCAL_DATE_TIME") DateTimeResponseFormat format){
        return externalApi.getWorldTime(location, format);
    }
}
