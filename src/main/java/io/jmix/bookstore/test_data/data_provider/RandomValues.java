package io.jmix.bookstore.test_data.data_provider;

import net.datafaker.DateAndTime;
import net.datafaker.Faker;
import net.datafaker.Number;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomValues {

    public static LocalDateTime randomPastLocalDateTime(int upUntilInYears) {
        DateAndTime date = new Faker().date();
        Number number = new Faker().number();
        int hour = number.numberBetween(8, 18);
        int minute = number.numberBetween(0, 60);
        int upUntilFutureInDays = upUntilInYears * 365;

        return date.past(upUntilFutureInDays, TimeUnit.DAYS)
                .toLocalDateTime()
                .withHour(hour)
                .withMinute(minute);
    }

    public static int randomPositiveNumber(int upUntil) {
        return new Faker().number().numberBetween(1, upUntil);
    }

    public static int randomPositiveNumber(int from, int until) {
        return new Faker().number().numberBetween(from, until);
    }

    public static LocalDate randomPastLocalDate(int upUntilPastInYears) {
        return randomPastLocalDateTime(upUntilPastInYears).toLocalDate();
    }

    public static LocalDateTime randomFutureLocalDateTime(int upUntilInYears) {
        DateAndTime date = new Faker().date();
        Number number = new Faker().number();
        int hour = number.numberBetween(8, 18);
        int minute = number.numberBetween(0, 60);
        int upUntilFutureInDays = upUntilInYears * 365;

        return date.future(upUntilFutureInDays, TimeUnit.DAYS)
                .toLocalDateTime()
                .withHour(hour)
                .withMinute(minute);
    }

    public static LocalDate randomFutureLocalDate(int upUntilFutureInYears) {
        return randomFutureLocalDateTime(upUntilFutureInYears).toLocalDate();
    }

    public static  <T> T randomEnum(T[] tValues) {
        int pick = random().nextInt(tValues.length);
        return tValues[pick];
    }


    public static <T> T randomOfList(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(random().nextInt(list.size()));
    }


    public static <T> T randomOfList(T... list) {
        List<T> collection = Arrays.asList(list);
        if (CollectionUtils.isEmpty(collection)) {
            return null;
        }
        return collection.get(random().nextInt(collection.size()));
    }

    private static Random random() {
        return new Random();
    }

}
