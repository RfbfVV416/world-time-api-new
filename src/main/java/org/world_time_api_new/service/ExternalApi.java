package org.world_time_api_new.service;

import org.world_time_api_new.model.DateTimeResponseFormat;
import org.world_time_api_new.model.WorldTimeDto;

public interface ExternalApi {
    WorldTimeDto getWorldTime(String location, DateTimeResponseFormat format);
}
