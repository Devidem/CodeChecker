package selectors;

import converters.ExArray;
import converters.Xls;
import exceptions.myExceptions.MyFileIOException;

import java.io.File;
import java.io.IOException;

/**
 * Работа с файлами.
 */
public class Files{

    /**
     * Преобразует .xls файл из папки {@param inputDir} в итоговый массив проверки акций.
     */
    public static String [][] toFinalArray(String inputDir) throws MyFileIOException {

        String result = select(inputDir);

        if (result.endsWith(".xls")){
            Xls xls = new Xls();
            try {
                return xls.toFinalArray(result);
            } catch (IOException e) {
                throw new MyFileIOException("Неправильно отработал select() или код из if", e);
            }

        } else {
            throw new MyFileIOException("Неправильно выбран файл/Ошибка селектора Files.select()!");
        }
    }

    /**
     * Выбирает файл из директории {@param inputDir}.
     * Предалагает выбрать файл вручную, если найдено несколько.
     * @param inputDir - Адрес папки с файлами без "/" в конце.
     */
    public static String select (String inputDir) throws MyFileIOException {
        File dir = new File(inputDir);
        String[] arrFiles = dir.list();

        if (arrFiles == null) {
            throw new MyFileIOException("Папка пуста");
        }
        return inputDir + "/" + ExArray.selector1D(arrFiles);
    }
}
