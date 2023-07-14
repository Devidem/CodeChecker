package selectors;

import converters.ArrayEx;
import enums.ConstantsString;
import exceptions.myExceptions.MyFileIOException;
import interfaces.ToPromsArray;

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
            String input = ConstantsString.InputFileDirectory.getValue();

            Files files = new Files(input);
            return files.toFinalArray();

        } else {
            throw new MyFileIOException("Некорректная работа селектора InputType.selector()");

        }

    }

    /**
     * Выбирает тип входных данных в зависимости от значения {@link #input} и передает результат в {@link #result}.
     * Предлагает сделать ручной выбор при неправильном вводе.
     */
    public void selector () {

        String Type = input.toLowerCase();
        String [] inputList = {"file"};

        if (Type.contains("file")) {
            result = "file";

        } else if (Type.contains("sql")) {
            System.out.println("Пока не реализован!");
            result = ArrayEx.selector1D(inputList);

        } else {
            System.out.println("Unknown Type");
            result = ArrayEx.selector1D(inputList);
        }

    }

}
