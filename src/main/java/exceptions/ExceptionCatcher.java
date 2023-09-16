package exceptions;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Сборник методов для удобной обработки исключений
 */
public class ExceptionCatcher {
    /**
     * Запрашивает ручной ввод полного имени файла и проверяет его наличие.
     * Запрос повторяется, пока файл не будет найден.
     * @param fileExtension расширение файла - например, .xls
     * @return Корректное полное имя файла
     */
    public static String filePath (String fileExtension){
        String newFilePath = "";
        Workbook proms = null;
        FileInputStream direct = null;

        while (!newFilePath.endsWith(fileExtension) || direct == null){

            System.out.println("Введите полное имя " + fileExtension + " файла: ");

            Scanner in = new Scanner(System.in);
            newFilePath = in.nextLine();

            //Проверка на исключения для повторного вызова цикла
            try {
                direct = new FileInputStream(newFilePath);
                proms = new HSSFWorkbook(direct);
            } catch (IOException ignored) {
                System.out.println("Неверное полное имя файла!");
            }
        }

        try {
            direct.close();
        } catch (IOException ignored) {
        }
        return newFilePath;
    }
}
