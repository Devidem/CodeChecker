package selectors;

import converters.ExSql;
import enums.ConstString;
import exceptions.myExceptions.MyFileIOException;
import exceptions.myExceptions.MyInputParamException;

import java.io.IOException;

/**
 * Работа с типами данных.
 */
public class InputType {

    /**
     * Возвращает итоговый массив проверки акций в зависимости от переданного типа входных данных из {@param input}.
     */
    public static String [][] toFinalArray(String input) throws MyFileIOException, IOException, MyInputParamException {

        //Выбираем корректное строковое значение типа данных
        String result = select(input);

        if (result.contains("file")) {
            String inputDir = ConstString.InputFileDirectory.getValue();
            return Files.toFinalArray(inputDir);

        } else if (result.contains("sql")) {
            return ExSql.toFinalArray();
        }

        else {
            throw new MyFileIOException("Некорректная работа селектора InputType.select()");
        }
    }

    /**
     * Выбирает тип входных данных в зависимости от значения {@param input}.
     * Предлагает сделать ручной выбор при неправильном вводе.
     * @param input - Тип входных данных.
     */
    public static String select(String input) throws MyInputParamException {

        String Type = input.toLowerCase();

        if (Type.contains("file")) {
            return  "file";

        } else if (Type.contains("sql")) {
            return  "sql";

        } else {
            throw new MyInputParamException("Неверный входной параметр \"inputType\" \nДоступные варианты - File/Sql");

        }
    }
}
