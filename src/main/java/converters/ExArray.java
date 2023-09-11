package converters;

import enums.ConstInt;
import exceptions.myExceptions.MyFileIOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

/**
 * Класс для обработки 1,2-х мерных массивов
 */

public class ExArray{

    /**
     * Преобразует двумерный массив в итоговый .xls файл проверки.
     * Содержит в имени Дату создания и результат проверки.
     * Файл сохраняется по адресу ./Outputs/Excel/
     * @param Array Массив содержащий коды товаров и акции.
     */
    public static void toExcelTest(String[][] Array) throws MyFileIOException {

        //Создаем книгу с листом Test result
        Workbook excelOut = new HSSFWorkbook();
        Sheet sheet = excelOut.createSheet("Test1 result");

        //Заполняем значение результата прохождения теста (может измениться на Failed в дальнейшем)
        String testResult = "Passed";
        int resChecker = 0;

        //Переписываем данные из массива в книгу
        for (int i = 0; i < Array.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o < Array[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(Array[i][o]);

                //Проверяем значение результатов теста до нахождения первой ошибки
                if (resChecker == 0 && (Objects.equals(Array[i][o], "Failed") | Objects.toString(Array[i][o]).contains("404"))) {
                    testResult = "Failed";
                    resChecker = 1;
                }
            }
        }

        //Добавляем дату для дальнейшей записи в имя файла
        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("(hh_mm_ss a)");
        //Прописываем итоговый адрес и имя файла, содержащее дату создания и результат проверки
        String outDirect = "./Outputs/Excel/CodesToCheck_Result_" + testResult + "_" + currentDate + "_" + simpleDate.format(dateNow) + ".xls";

        //Сохраняем книгу в файл Excel
        FileOutputStream outFile;
        try {
            outFile = new FileOutputStream(outDirect);
            excelOut.write(outFile);
            excelOut.close();
        } catch (IOException e) {
            throw new MyFileIOException("Некорректное полное имя файла для сохранения! (outDirect)", e);
        }
    }

    /**
     * Создает истинный клон String[][] массива через конкатенацию значений с пустой строкой.
     * @param array Преобразуемый массив.
     * @return Клон массива.
     */
    public static String[][] clone2d(String[][] array) {
        String[][] updated = new String[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                updated[i][j] = array[i][j] + "";
            }
        }
        return updated;
    }

    /**
     * Выбирает строку из списка.
     * Предлагает выбрать значение вручную, если в переданном массиве несколько элементов.
     * @param checkList Массив элементов.
     * @return Выбранная строка.
     */
    public static String selector1D(String[] checkList) {

        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";

        if (checkList.length > 1) {
            System.out.println(ANSI_RED + "Выберите из:" + ANSI_RESET);

            int objectNum = 1;
            for (String objectValue : checkList) {
                System.out.println(ANSI_GREEN + objectNum + "_" + objectValue + ANSI_RESET);
                objectNum++;
            }

            int scanNum = 0;
            while (scanNum > checkList.length | scanNum < 1) {
                System.out.print("Введите номер (1,2,etc.): ");

                Scanner in = new Scanner(System.in);
                scanNum = in.nextInt();
            }
            return checkList[scanNum - 1];

        } else {
            return checkList[0];
        }
    }

    /**
     * Разбивает таблицу на набор одиночных таблиц.
     * @param input Таблица для разбиения.
     * @param result Коллекция для хранения результата.
     */
    public static void separateTableQueue (String [][] input, Queue <String [][]> result) {

        //Ряд с которого начинают перечисляться объекты
        int startRow = ConstInt.startRow.getValue();

        //Создаем чек-лист для одного товара
        for (int i = startRow; i < input.length; i++) {

            //Создаем одиночный массив
            String [][] single = new String[2][input[0].length];
            System.arraycopy(input[0], 0, single[0], 0, single[0].length);
            System.arraycopy(input[i], 0, single[1], 0, single[0].length);

            //Добавляем одиночный массив в коллекцию
            result.add(single);
        }
    }

    /**
     * Объединяет набор таблиц в одну.
     * @param input Набор таблиц.
     * @param result Таблица для хранения результата слияния.
     */
    public static void unionTablesQueue (Queue <String [][]> input, String [][] result) {

        //Прописываем заголовки столбцов
        for (int i = 0; i < input.element()[0].length; i++) {
            result[0][i] = input.element()[0][i];
        }

        //Переписываем значения
        for (int i = 1; !input.isEmpty() ; i++) {
            result[i] = input.remove()[1];
        }
    }
}
