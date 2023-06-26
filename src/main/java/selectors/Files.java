package selectors;

import converters.ArrayEx;
import converters.Xls;
import exceptions.myExceptions.MyFileIOException;
import interfaces.ToPromsArray;

import java.io.File;
import java.io.IOException;

/**
 * Работа с файлами в папке {@link #input}
 */
public class Files extends Selectors implements ToPromsArray {

    public Files(String input) {
        super(input);
    }

    /**
     * Преобразует .xls файл с полным путем {@link #result} в итоговый массив проверки акций
     */
    public String [][] toFinalArray() throws MyFileIOException {

        selector();

        if (result.endsWith(".xls")){
            Xls xls = new Xls();
            try {
                return xls.toFinalArray(result);
            } catch (IOException e) {
                throw new MyFileIOException("Неправильно отработал селектор или код из if", e);
            }

        } else {
            throw new MyFileIOException("Ошибка селектора Files.selector()!");
        }

    }

    /**
     * Выбирает файл из директории {@link #input} и передает полный путь в {@link #result}.
     */
    public void selector() throws MyFileIOException {
        File dir = new File(input);
        String[] arrFiles = dir.list();
        if (arrFiles == null) {
            throw new MyFileIOException("Папка пуста");
        }
        result = input + "/" + ArrayEx.selector1D(arrFiles);

    }

}
