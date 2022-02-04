package org.world_time_api_new.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.world_time_api_new.exception.BadRequestException;
import org.world_time_api_new.exception.DatetimeParseException;
import org.world_time_api_new.model.DateTimeResponseFormat;
import java.time.OffsetDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@Log4j2
public class DatetimeFormatterImpl implements DatetimeFormatter {

    @Override
    public String formatOffsetDateTime(String str, DateTimeResponseFormat format, String timezone){
        OffsetDateTime datetime;
        try {
            datetime = OffsetDateTime.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(str));
        }
        catch (DateTimeParseException e){
            log.error("Error while parsing to OffsetDateTime", e);
            throw new DatetimeParseException(e.getMessage());
        }
        return format(datetime, format, timezone);
    }

    @Override
    public String formatLocalDateAndLocalTime(String localDateStr, String localTimeStr, DateTimeResponseFormat format, String timezone){
        OffsetDateTime datetime;
        try {
            LocalDate date = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(localDateStr));
            LocalTime time = LocalTime.from(DateTimeFormatter.ISO_LOCAL_TIME.parse(localTimeStr));
            datetime = OffsetDateTime.of(date, time, ZoneId.of(timezone).getRules().getOffset(Instant.now()));
        }
        catch (DateTimeParseException e){
            log.error("Error while parsing to OffsetDateTime", e);
            throw new DatetimeParseException(e.getMessage());
        }
        return format(datetime, format, timezone);
    }

    private String format(OffsetDateTime datetime, DateTimeResponseFormat format, String timezone) {
        if (DateTimeResponseFormat.ISO_LOCAL_DATE_TIME.equals(format)){
            return datetime.toLocalDateTime().toString();
        }
        if (DateTimeResponseFormat.ISO_ZONED_DATE_TIME.equals(format)){
            return datetime.atZoneSimilarLocal(ZoneId.of(timezone)).toString();
        }
        if (DateTimeResponseFormat.ISO_LOCAL_TIME.equals(format)){
            return datetime.toLocalTime().toString();
        }
        if (DateTimeResponseFormat.ISO_OFFSET_TIME.equals(format)){
            return datetime.toOffsetTime().toString();
        }
        return datetime.toString();
    }
}
