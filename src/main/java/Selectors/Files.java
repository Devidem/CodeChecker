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

}
