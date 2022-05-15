package ru.netology;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GeneratorDataCardOrder {
    private final String city;
    private final String fullName;
    private final String mobilePhone;
    private final String meetingDate1;
    private final String meetingDate2;
}
