package org.world_time_api_new.service;

import org.world_time_api_new.model.DateTimeResponseFormat;

public interface DatetimeFormatter {
    String formatOffsetDateTime(String str, DateTimeResponseFormat format, String timezone);
    String formatLocalDateAndLocalTime(String localDateStr, String localTimeStr, DateTimeResponseFormat format, String timezone);
}
