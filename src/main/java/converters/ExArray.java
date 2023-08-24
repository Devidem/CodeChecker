package converters;

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
import java.util.Scanner;

/**
 * Класс для обработки 1,2-х мерных массивов
 */
// Неиспользуемые методы созданы для демонстрации перегрузки методов
// Вымышленная задумка - дать возможность использовать методы с созданием класса
public class ExArray{
    private String[] input1D;
    private String[][] input2D;

    public ExArray(String[] input1D) {
        this.input1D = input1D;
    }

    public ExArray(String[][] input2D) {
        this.input2D = input2D;
    }

    private String result1D;
    private String[] result2D;

    /**
     * Преобразует двумерный String массив в .xls файл.
     * @param array   Преобразуемый массив
     * @param outName Имя .xls файла
     * @param outPath Адрес для создаваемого файла (без / в конце)
     */
    public static void toExcel(String[][] array, String outName, String outPath) throws IOException {

        //Создаем книгу с листом Test result
        Workbook excelOut = new HSSFWorkbook();
        Sheet sheet = excelOut.createSheet("Test result");

        //Переписываем данные из массива в книгу
        for (int i = 0; i < array.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o < array[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(array[i][o]);

            }
        }

        //Добавляем дату для дальнейшей записи в имя файла
        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("(hh_mm_ss a)");

        //Прописываем итоговый адрес и имя файла, содержащее дату создания
        String outDirect = outPath + "/" + outName + "_" + currentDate + "_" + simpleDate.format(dateNow) + ".xls";

        //Сохраняем книгу в файл Excel
        FileOutputStream outFile = new FileOutputStream(outDirect);
        excelOut.write(outFile);
        excelOut.close();
    }

    /**
     * Преобразует {@link #input2D} класса в .xls файл.
     * @param outName Имя .xls файла
     * @param outPath Адрес для создаваемого файла (без / в конце)
     */
    public void toExcel(String outName, String outPath) throws IOException {

        //Создаем книгу с листом Test result
        Workbook excelOut = new HSSFWorkbook();
        Sheet sheet = excelOut.createSheet("Test1 result");

        //Переписываем данные из массива в книгу
        for (int i = 0; i < input2D.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o < input2D[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(input2D[i][o]);

            }
        }
        //Добавляем дату для дальнейшей записи в имя файла
        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("(hh_mm_ss a)");

        //Прописываем итоговый адрес и имя файла, содержащее дату создания
        String outDir = outPath + "/" + outName + "_" + currentDate + "_" + simpleDate.format(dateNow) + ".xls";

        //Сохраняем книгу в файл Excel
        FileOutputStream outFile = new FileOutputStream(outDir);
        excelOut.write(outFile);
        excelOut.close();
    }

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
                if (resChecker == 0 && (Objects.equals(Array[i][o], "FAILED") | Objects.toString(Array[i][o]).equals("404"))) {
                    testResult = "FAILED";
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
     * Создает истинный клон String[][] массива через конкатенацию значений с пустой строкой
     * @param array Преобразуемый массив
     * @return Клон массива
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
     * Выбирает строку из {@link #input1D}  и помещает результат в {@link #result1D}
     * Предлагает выбрать значение вручную, если в переданном массиве несколько элементов.
     */
    public void selector1D() {

        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";

        if (input1D.length > 1) {
            System.out.println(ANSI_RED + "Выберите из:" + ANSI_RESET);

            int objectNum = 1;
            for (String objectValue : input1D) {
                System.out.println(ANSI_GREEN + objectNum + "_" + objectValue + ANSI_RESET);
                objectNum++;
            }

            int scanNum = 0;
            while (scanNum > input1D.length | scanNum < 1) {
                System.out.print("Введите номер (1,2,etc.): ");

                Scanner in = new Scanner(System.in);
                scanNum = in.nextInt();
            }

            result1D = input1D[scanNum - 1];

        } else {
            result1D = input1D[0];
        }
    }

    /**
     * Выбирает строку из 1-го массива.
     * Предлагает выбрать значение вручную, если в переданном массиве несколько элементов.
     * @param checkList Массив элементов
     * @return Выбранная строка
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


    //Геттеры и Сеттеры
    public String[] getInput1D() {
        return input1D;
    }

    public void setInput1D(String[] input1D) {
        this.input1D = input1D;
    }

    public String getResult1D() {
        return result1D;
    }


    public String[][] getInput2D() {
        return input2D;
    }

    public void setInput2D(String[][] input2D) {
        this.input2D = input2D;
    }

    public String[] getResult2D() {
        return result2D;
    }


}
