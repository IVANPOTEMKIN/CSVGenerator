package ru.effective_mobile.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.effective_mobile.annotation.CSVClass;
import ru.effective_mobile.annotation.CSVField;
import ru.effective_mobile.exception.*;
import ru.effective_mobile.model.BusinessObject;
import ru.effective_mobile.model.Months;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CSVTest {

    @Test
    @Order(value = 1)
    void successWritingData() {
        BusinessObject object = BusinessObject.builder()
                .firstName("firstName")
                .lastName("lastName")
                .dayOfBirth(1)
                .monthOfBirth(Months.JANUARY)
                .yearOfBirth(2000)
                .build();

        assertTrue(CSV.writeToFile(List.of(object), "test.csv"));

        String expectedHeaders = "Заголовки: [Имя, Фамилия, День, Месяц, Год]";
        String expectedValue = "Объект 1: [firstName, lastName, 1, JANUARY, 2000]";

        String actual = CSV.readFromFile("test.csv");
        assertEquals(expectedHeaders
                .concat("\n")
                .concat(expectedValue)
                .concat("\n"), actual);
    }

    @Test
    @Order(value = 2)
    void successOverWritingData() {
        BusinessObject object = BusinessObject.builder()
                .firstName("new firstName")
                .lastName("new lastName")
                .dayOfBirth(10)
                .monthOfBirth(Months.APRIL)
                .yearOfBirth(1995)
                .build();

        assertTrue(CSV.writeToFile(List.of(object), "test.csv"));

        String expectedHeaders = "Заголовки: [Имя, Фамилия, День, Месяц, Год]";
        String expectedValue = "Объект 1: [new firstName, new lastName, 10, APRIL, 1995]";

        String actual = CSV.readFromFile("test.csv");
        assertEquals(expectedHeaders
                .concat("\n")
                .concat(expectedValue)
                .concat("\n"), actual);
    }

    @Test
    @Order(value = 3)
    void successAddData() {
        BusinessObject object = BusinessObject.builder()
                .firstName("add firstName")
                .lastName("add lastName")
                .dayOfBirth(15)
                .monthOfBirth(Months.DECEMBER)
                .yearOfBirth(2005)
                .build();

        assertTrue(CSV.writeToFile(List.of(object), "test.csv", true));

        String expectedHeaders = "Заголовки: [Имя, Фамилия, День, Месяц, Год]";
        String expectedValue1 = "Объект 1: [new firstName, new lastName, 10, APRIL, 1995]";
        String expectedValue2 = "Объект 2: [add firstName, add lastName, 15, DECEMBER, 2005]";

        String actual = CSV.readFromFile("test.csv");
        assertEquals(expectedHeaders
                .concat("\n")
                .concat(expectedValue1)
                .concat("\n")
                .concat(expectedValue2)
                .concat("\n"), actual);
    }

    @Test
    @Order(value = 4)
    void getClassAnnotationException() {

        @Data
        @AllArgsConstructor
        class BusinessObject {

            @CSVField
            private final String firstName;
        }

        BusinessObject object = new BusinessObject("firstName");
        assertThrows(ClassAnnotationException.class,
                () -> CSV.writeToFile(List.of(object), "test.csv"));
    }

    @Test
    @Order(value = 5)
    void getListDataException() {
        assertThrows(ListDataException.class,
                () -> CSV.writeToFile(null, "test.csv"));
        assertThrows(ListDataException.class,
                () -> CSV.writeToFile(List.of(), "test.csv"));
    }

    @Test
    @Order(value = 6)
    void getIncorrectObjectException() {
        List<BusinessObject> list = new ArrayList<>();
        list.add(null);
        assertThrows(IncorrectObjectException.class,
                () -> CSV.writeToFile(list, "test.csv"));
    }

    @Test
    @Order(value = 7)
    void getEmptyHeadersException() {

        @CSVClass
        @Data
        @AllArgsConstructor
        class BusinessObject {
            private final String firstName;
        }

        BusinessObject object = new BusinessObject("firstName");
        assertThrows(EmptyHeadersException.class,
                () -> CSV.writeToFile(List.of(object), "test.csv"));
    }

    @Test
    @Order(value = 8)
    void getFileNotFoundException() {
        assertThrows(FileNotFoundException.class,
                () -> CSV.readFromFile("new_test.csv"));
    }
}