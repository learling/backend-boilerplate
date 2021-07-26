package de.boilerplate.springbootbackend.data.item.search;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateRange {

    public LocalDateTime from, to;

    private static final String DATE_FORMAT = "yyyy-MM-dd hh-mm-ss a";

    public DateRange(String range) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String[] fromTo = range.split(" - ");
        try {
            if (fromTo.length == 2) {
                LocalDateTime from = LocalDateTime.parse(fromTo[0].trim(), formatter);
                LocalDateTime to = LocalDateTime.parse(fromTo[1].trim(), formatter);
                if (from.isAfter(to)) {
                    this.from = to;
                    this.to = from;
                } else {
                    this.from = from;
                    this.to = to;
                }
            } else {
                String date = range.trim();
                this.from = this.to = LocalDateTime.parse(date, formatter);
            }
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
            this.from = this.to = LocalDateTime.now();
        }
    }
}