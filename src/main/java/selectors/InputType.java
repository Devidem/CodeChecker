package selectors;

import converters.ExArray;
import converters.ExSql;
import enums.ConstString;
import exceptions.myExceptions.MyFileIOException;
import interfaces.ToPromsArray;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Работа с типами данных.
 */
public class InputType extends Selectors implements ToPromsArray {

    public InputType(String input) {
        super(input);
    }

    /**
     * Создает итоговый массив проверки акций в зависимости от типа входных данных из {@link #result}.
     */
    public String [][] toFinalArray() throws MyFileIOException {
        selector();

        if (result.contains("file")) {
            //Адрес папки проекта для входных файлов
            String input = ConstString.InputFileDirectory.getValue();

            Files files = new Files(input);
            return files.toFinalArray();

        } else if (result.contains("sql")) {
            try {
                return ExSql.toFinalArray();

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else {
            throw new MyFileIOException("Некорректная работа селектора InputType.selector()");

        }

    }

    /**
     * Выбирает тип входных данных в зависимости от значения {@link #input} и передает результат в {@link #result}.
     * Предлагает сделать ручной выбор при неправильном вводе.
     */
    public void selector () {

        String Type = input.toLowerCase();
        String [] inputList = {"file", "sql"};

        if (Type.contains("file")) {
            result = "file";

        } else if (Type.contains("sql")) {
            result = "sql";

        } else {
            System.out.println("Unknown Type");
            result = ExArray.selector1D(inputList);
        }

    }

}
