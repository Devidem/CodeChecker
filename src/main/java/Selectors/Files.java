package Selectors;

import Converts.ArrayEx;
import Converts.Xls;
import Interfaces.CheckPromArray;

import java.io.File;
import java.io.IOException;

/**
 * Работа с файлами
 */
public class Files extends Selectors implements CheckPromArray {

    public Files(String input) {
        super(input);
    }

    /**
     * Преобразует .xls файл с полным путем {@link #result} в итоговый массив проверки акций
     */
    public String [][] toFinalArray() throws IOException {

        selector();

        if (result.endsWith(".xls")){
            Xls excel = new Xls();
            return excel.toFinalArray(result);

        } else {
            System.out.println("Wrong File");
            return null;
        }

    }

    /**
     * Выбирает файл из директории {@link #input} и передает полный путь в {@link #result}.
     */
    public void selector() {
        File dir = new File(input);
        String[] arrFiles = dir.list();
        result = input + "/" + ArrayEx.selector1D(arrFiles);

    }

//    /**
//     * Выбирает файл из папки.
//     * Предлагает сделать ручной выбор если файлов несколько.
//     * @param dirPath Путь папки с файлами (без / в конце)
//     */
//    public static String selector(String dirPath) {
//        File dir = new File(dirPath);
//        String[] filesList = dir.list();
//
//        return dirPath + "/" + ArrayEx.selector1D(filesList);
//    }


    //    /**
//     * Преобразует .xls файл в итоговый массив проверки акций.
//     * @param filePath Полный путь файла
//     */
//    public static String [][] toFinalArray(String filePath) throws IOException {
//
//        if (filePath.endsWith(".xls")){
//            Xls excel = new Xls();
//            return excel.toFinalArray(filePath);
//
//        } else {
//            System.out.println("Wrong File");
//            return null;
//        }
//
//    }

}
