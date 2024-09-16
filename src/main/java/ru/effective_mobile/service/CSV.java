package ru.effective_mobile.service;

import ru.effective_mobile.annotation.CSVClass;
import ru.effective_mobile.annotation.CSVField;
import ru.effective_mobile.exception.FileNotFoundException;
import ru.effective_mobile.exception.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility класс для генерации файла формата CSV из передаваемого списка данных
 */
public final class CSV {

    private static final String DELIMITER = ";";

    private CSV() {
        throw new ClassInstantiationException();
    }

    /**
     * Метод выполняет запись данных из передаваемого списка в файл, указанный по передаваемому имени
     *
     * @param data     передаваемый список с данными
     * @param fileName передаваемое имя файла
     * @return WritingDataException
     * <br> или true в случае успеха
     */
    public static boolean writeToFile(List<?> data, String fileName) {
        boolean append = false;
        return writeToFile(data, fileName, append);
    }

    /**
     * Метод выполняет запись данных из передаваемого списка в файл, указанный по передаваемому имени
     *
     * @param data     передаваемый список с данными
     * @param fileName передаваемое имя файла
     * @param append   флаг на перезапись или дополнение файла
     * @return WritingDataException
     * <br> или true в случае успеха
     */
    public static boolean writeToFile(List<?> data, String fileName, boolean append) {
        File file = new File(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            if (isEmptyFile(file, data)) {
                bw.write(getHeaders(data.get(0)));
            }
            for (Object object : data) {
                bw.write(getValues(object));
            }
            bw.flush();
        } catch (IOException e) {
            throw new WritingDataException();
        }
        return true;
    }

    /**
     * Метод возвращает строку после прочтения данных из файла, найденного по передаваемому имени
     *
     * @param fileName передаваемое имя
     * @return строка, содержащая данные файла
     */
    public static String readFromFile(String fileName) {
        File file = new File(fileName);
        StringBuilder result = new StringBuilder();
        if (isCorrectFile(file)) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String[] headers = br.readLine().split(DELIMITER);
                result.append(String.format("Заголовки: %s\n", Arrays.toString(headers)));
                long id = 1L;
                while (br.ready()) {
                    String[] values = br.readLine().split(DELIMITER);
                    result.append(String.format("Объект %s: %s\n", id++, Arrays.toString(values)));
                }
            } catch (IOException e) {
                throw new ReadingDataException();
            }
        }
        return result.toString();
    }

    /**
     * Метод проверяет передаваемый файл на пустоту
     *
     * @param file передаваемый файл
     * @return false или true
     */
    private static boolean isEmptyFile(File file, List<?> data) {
        if (file.length() == 0) return isCorrectData(data);
        else return false;
    }

    /**
     * Метод проверяет передаваемый файл на корректность
     *
     * @param file передаваемый файл
     * @return FileNotFoundException
     * <br> или true в случае успеха
     */
    private static boolean isCorrectFile(File file) {
        if (!file.exists() || file.length() == 0) throw new FileNotFoundException();
        else return true;
    }

    /**
     * Метод проверяет передаваемый список на корректность
     *
     * @param data передаваемый список
     * @return ListDataException IncorrectObjectException EmptyHeadersException
     * <br> или true в случае успеха
     */
    private static boolean isCorrectData(List<?> data) {
        if (data == null || data.isEmpty()) throw new ListDataException();
        if (data.get(0) == null) throw new IncorrectObjectException();
        if (getHeaders(data.get(0)).isBlank()) throw new EmptyHeadersException();
        return true;
    }

    /**
     * Метод возвращает значения полей передаваемого объекта в виде строки
     *
     * @param object передаваемый объект
     * @return строка, содержащая значения
     */
    private static String getValues(Object object) {
        Field[] fields = getFields(object);
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(CSVField.class))
                .map(value -> {
                    value.setAccessible(true);
                    try {
                        return value.get(object).toString();
                    } catch (IllegalAccessException e) {
                        throw new FieldAccessException();
                    }
                })
                .collect(Collectors.joining(DELIMITER))
                .concat(System.lineSeparator());
    }

    /**
     * Метод возвращает заголовки (ключи или названия полей передаваемого объекта) в виде строки
     *
     * @param object передаваемый объект
     * @return строка, содержащая заголовки
     */
    private static String getHeaders(Object object) {
        Field[] fields = getFields(object);
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(CSVField.class))
                .map(field -> {
                    String key = field.getDeclaredAnnotation(CSVField.class).key();
                    return key.isEmpty() ? field.getName() : key;
                })
                .collect(Collectors.joining(DELIMITER))
                .concat(System.lineSeparator());
    }

    /**
     * Метод возвращает массив всех полей аннотированного класса передаваемого объекта
     *
     * @param object передаваемый объект
     * @return массив полей
     */
    private static Field[] getFields(Object object) {
        if (object.getClass().isAnnotationPresent(CSVClass.class)) {
            return object.getClass().getDeclaredFields();
        } else throw new ClassAnnotationException();
    }
}