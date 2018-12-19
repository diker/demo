package com.diker.basic.jdk.time;

import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author diker
 * @since 2018/11/5
 */
public class LocalDateTest {

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.of(2018, Month.NOVEMBER, 5);

        System.out.println(localDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(localDate.format(DateTimeFormatter.ISO_WEEK_DATE));
        System.out.println(localDate.format(DateTimeFormatter.ISO_ORDINAL_DATE));
        System.out.println(localDate.get(ChronoField.DAY_OF_YEAR));

        LocalDate date1 = localDate.with(ChronoField.YEAR, 2019);
        System.out.println(date1.format(DateTimeFormatter.ISO_LOCAL_DATE));

        LocalDate date2 = localDate.minus(1, ChronoUnit.DAYS);
        System.out.println(date2.format(DateTimeFormatter.ISO_LOCAL_DATE));

        LocalDate date3 = localDate.minusDays(1L);
        System.out.println(date3.format(DateTimeFormatter.ISO_LOCAL_DATE));

        DateTimeFormatter dtf = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR).appendLiteral("/")
                .appendValue(ChronoField.MONTH_OF_YEAR,2).appendLiteral("/")
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .toFormatter(Locale.CHINESE);
        System.out.println(localDate.format(dtf));

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        System.out.println(localDate.format(dtf2));
        System.out.println(LocalDate.parse("2018/09/01", dtf2));

    }
}
