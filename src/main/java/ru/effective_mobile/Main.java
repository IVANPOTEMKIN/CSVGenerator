package ru.effective_mobile;

import ru.effective_mobile.model.BusinessObject;
import ru.effective_mobile.model.BusinessObject2;
import ru.effective_mobile.model.Months;
import ru.effective_mobile.service.CSV;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BusinessObject obj1 = BusinessObject.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .dayOfBirth(1)
                .monthOfBirth(Months.JANUARY)
                .yearOfBirth(1980)
                .build();

        BusinessObject obj2 = BusinessObject.builder()
                .firstName("Semen")
                .lastName("Semenov")
                .dayOfBirth(12)
                .monthOfBirth(Months.APRIL)
                .yearOfBirth(2000)
                .build();

        BusinessObject obj3 = BusinessObject.builder()
                .firstName("Sergey")
                .lastName("Sergeev")
                .dayOfBirth(23)
                .monthOfBirth(Months.DECEMBER)
                .yearOfBirth(1995)
                .build();

        List<BusinessObject> list1 = new ArrayList<>();
        list1.add(obj1);
        list1.add(obj2);

        CSV.writeToFile(list1, "file.csv");

        List<BusinessObject> list2 = new ArrayList<>();
        list2.add(obj3);

        CSV.writeToFile(list2, "file.csv", true);

        System.out.println(CSV.readFromFile("file.csv"));

        BusinessObject2 obj4 = BusinessObject2
                .builder()
                .name("Ivan")
                .score(List.of("4", "5", "3", "4", "4"))
                .build();

        BusinessObject2 obj5 = BusinessObject2
                .builder()
                .name("Semen")
                .score(List.of("5", "5", "4", "4", "4"))
                .build();

        BusinessObject2 obj6 = BusinessObject2
                .builder()
                .name("Sergey")
                .score(List.of("4", "5", "5", "3", "4"))
                .build();

        List<BusinessObject2> list3 = new ArrayList<>();
        list3.add(obj4);

        CSV.writeToFile(list3, "file2.csv");

        List<BusinessObject2> list4 = new ArrayList<>();
        list4.add(obj5);
        list4.add(obj6);

        CSV.writeToFile(list4, "file2.csv", true);

        System.out.println(CSV.readFromFile("file2.csv"));

    }
}