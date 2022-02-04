package org.world_time_api_new.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WorldTimeDto {
    private String datetime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String timezone;
}
