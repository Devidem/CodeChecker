package Selectors;

import Converts.ArrayEx;
import Interfaces.CheckPromArray;

import java.io.IOException;

/**
 * Работа с типами данных.
 */
public class InputType extends Selectors implements CheckPromArray {

    public InputType(String input) {
        super(input);
    }

    /**
     * Создает итоговый массив проверки акций в зависимости от типа входных данных из {@link #result}.
     */
    public String [][] toFinalArray() throws IOException {
        selector();

        if (result.contains("file")) {
            String input = "./Inputs/Files"; //Определено конкретное значение, поскольку в проекте предусмотрена папка для входных файлов.
            Files files = new Files(input);
            return files.toFinalArray();

        } else {
            System.out.println("Wrong Type!"); //Скорее всего забыли про selector()!
            return null;

        }

    }

    /**
     * Выбирает тип входных данных в зависимости от значения {@link #input} и передает результат в {@link #result}.
     * Предлагает сделать ручной выбор при неправильном вводе.
     */
    public void selector () {

        String Type = input.toLowerCase();
        String [] inputList = {"file", "sql(для примера)"};

        if (Type.contains("file")) {
            result = "file";

        } else if (Type.contains("sql")) {
            System.out.println("Кто такой этот ваш SQl? Пока не сделан!");

        } else {
            System.out.println("Unknown Type");
            result = ArrayEx.selector1D(inputList);
        }

    }

    //    /**
//     * Создает итоговый массив проверки акций.
//     * @param inputType Тип данных.
//     */
//    public static String [][] toFinalArray(String inputType) throws IOException {
//
//        String selectType = selector(inputType);
//
//        if (selectType.contains("file")) {
//            String dirPath = "./Inputs/Files";
//            return Files.toFinalArray(Files.selector(dirPath));
//
//        } else {
//            System.out.println("Wrong Type!"); //Скорее всего забыли про selector()!
//            return null;
//
//        }
//
//    }

//    /**
//     * Выбирает тип входных данных.
//     * Предлагает сделать ручной выбор при неправильном вводе.
//     * @param inputType Тип данных.
//     */
//    public static String selector (String inputType) {
//
//        String Type = inputType.toLowerCase();
//        String [] inputList = {"file", "sql(для примера)"};
//
//        if (Type.contains("file")) {
//            return "file";
//
//        } else {
//            System.out.println("Unknown Type");
//            return ArrayEx.selector1D(inputList);
//
//        }
//
//    }

}
