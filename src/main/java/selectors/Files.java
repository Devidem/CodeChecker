package selectors;

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
    public static String [][] toFinalArray(String inputDir) throws MyFileIOException, IOException {

        String result = select(inputDir);

        if (result.endsWith(".xls")) {
            Xls xls = new Xls();
            return xls.toFinalArray(result);

        } else {
            throw new MyFileIOException("Неверный формат файла!\nДоступные форматы - .xls");
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

        } else if (arrFiles.length > 1) {
            throw new MyFileIOException("В папке " + inputDir + " более одного файла!");

        }
        return inputDir + "/" + arrFiles[0];
    }
}
