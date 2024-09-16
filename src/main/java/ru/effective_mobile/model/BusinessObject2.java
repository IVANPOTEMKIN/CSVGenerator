package ru.effective_mobile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.effective_mobile.annotation.CSVClass;
import ru.effective_mobile.annotation.CSVField;

import java.util.List;

@CSVClass
@Data
@Builder
@AllArgsConstructor
public class BusinessObject2 {

    @CSVField(key = "Имя")
    private final String name;

    @CSVField(key = "Оценки")
    private final List<String> score;
}