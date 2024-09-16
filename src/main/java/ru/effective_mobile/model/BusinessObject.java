package ru.effective_mobile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.effective_mobile.annotation.CSVClass;
import ru.effective_mobile.annotation.CSVField;

@CSVClass
@Data
@Builder
@AllArgsConstructor
public class BusinessObject {

    @CSVField(key = "Имя")
    private final String firstName;

    @CSVField(key = "Фамилия")
    private final String lastName;

    @CSVField(key = "День")
    private final int dayOfBirth;

    @CSVField(key = "Месяц")
    private final Months monthOfBirth;

    @CSVField(key = "Год")
    private final int yearOfBirth;
}