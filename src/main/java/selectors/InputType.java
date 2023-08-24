package selectors;

import converters.ExArray;
import converters.ExSql;
import enums.ConstString;
import exceptions.myExceptions.MyFileIOException;

/**
 * Работа с типами данных.
 */
public class InputType {

    /**
     * Возвращает итоговый массив проверки акций в зависимости от переданного типа входных данных из {@param input}.
     */
    public static String [][] toFinalArray(String input) throws MyFileIOException {
        //Выбираем корректное строковое значение типа данных
        String result = select(input);

        if (result.contains("file")) {
            //Адрес папки проекта для входных файлов
            String inputDir = ConstString.InputFileDirectory.getValue();

            return Files.toFinalArray(inputDir);

        } else if (result.contains("sql")) {
            return ExSql.toFinalArray();
        }

        else {
            throw new MyFileIOException("Некорректная работа селектора InputType.selector()");
        }
    }
    /**
     * Выбирает тип входных данных в зависимости от значения {@param input}.
     * Предлагает сделать ручной выбор при неправильном вводе.
     * @param input - Тип входных данных.
     */

    public static String select(String input) {

        String Type = input.toLowerCase();
        String [] inputList = {"file", "sql"};

        if (Type.contains("file")) {
            return  "file";

        } else if (Type.contains("sql")) {
            return  "sql";

        } else {
            //Предлагаем сделать выбор вручную
            System.out.println("Unknown Type");
            return ExArray.selector1D(inputList);
        }

    }

}
