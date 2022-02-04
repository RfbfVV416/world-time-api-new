package org.world_time_api_new.config;

import org.springframework.core.convert.converter.Converter;
import org.world_time_api_new.exception.BadRequestException;
import org.world_time_api_new.model.DateTimeResponseFormat;

public class StringToEnumConverter implements Converter<String, DateTimeResponseFormat> {
    @Override
    public DateTimeResponseFormat convert(String s) {
        try {
            return DateTimeResponseFormat.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid format parameter");
        }
    }
}
