package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class GeneratorDataCardOrder {
    private GeneratorDataCardOrder() {
    }

    @Data
    @RequiredArgsConstructor
    public static class UserInfo {
        private final String city;
        private final String fullName;
        private final String mobilePhone;
        private final String meetingDate;
        private final String dateRescheduling;
    }

    public static UserInfo getUserInfo() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Faker faker = new Faker(new Locale("ru"));
        return new UserInfo(faker.address().city(),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                LocalDate.now().plusDays(4).format(df),
                LocalDate.now().plusDays(7).format(df));
    }
}
