package converters;

import exceptions.myExceptions.MyFileIOException;
import interfaces.Selector1D;
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
 * Обработка 1,2-х мерных массивов
 */
// Неиспользуемые toExcel методы созданы для демонстрации перегрузки методов
// Вымышленная задумка - дать возможность использовать методы с созданием класса, если бы у нас были другие задачи,
// требующие использовать несколько методов для работы с .xls документами
public class ArrayEx implements Selector1D {
    private String[] input1D;
    private String[][] input2D;

    public ArrayEx(String[] input1D) {
        this.input1D = input1D;
    }

    public ArrayEx(String[][] input2D) {
        this.input2D = input2D;
    }

    private String result1D;
    private String[] result2D;

    /**
     * Преобразует двумерный String массив в .xls файл.
     *
     * @param array   Преобразуемый массив
     * @param outName Имя .xls файла
     * @param outPath Адрес для созданного файла (без / в конце)
     */
    public static void toExcel(String[][] array, String outName, String outPath) throws IOException {

        Workbook excelOut = new HSSFWorkbook();
        Sheet sheet = excelOut.createSheet("Test result");

        for (int i = 0; i < array.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o < array[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(array[i][o]);

            }
        }

        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("(hh_mm_ss a)");
        String outDirect = outPath + "/" + outName + "_" + currentDate + "_" + simpleDate.format(dateNow) + ".xls";

        FileOutputStream outFile = new FileOutputStream(outDirect);
        excelOut.write(outFile);
        excelOut.close();
    }

    /**
     * Преобразует input2D класса в .xls файл.
     *
     * @param outName Имя .xls файла
     * @param outPath Адрес для созданного файла (без / в конце)
     */
    public void toExcel(String outName, String outPath) throws IOException {

        Workbook excelOut = new HSSFWorkbook();
        Sheet sheet = excelOut.createSheet("Test1 result");

        for (int i = 0; i < input2D.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o < input2D[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(input2D[i][o]);

            }
        }

        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("(hh_mm_ss a)");
        String outDir = outPath + "/" + outName + "_" + currentDate + "_" + simpleDate.format(dateNow) + ".xls";

        FileOutputStream outFile = new FileOutputStream(outDir);
        excelOut.write(outFile);
        excelOut.close();
    }

    /**
     * Преобразует двумерный массив в итоговый .xls файл проверки.
     * Содержит в имени Дату создания и результат проверки.
     * Хранится по адресу ./Outputs/Excel/
     *
     * @param Array Массив содержащий коды товаров и акции.
     */
    public static void toExcelTest(String[][] Array) throws MyFileIOException {

        Workbook excelOut = new HSSFWorkbook();
        Sheet sheet = excelOut.createSheet("Test1 result");
        String testResult = "Passed";
        int resChecker = 0;

        for (int i = 0; i < Array.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o < Array[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(Array[i][o]);
                if (resChecker == 0 && (Objects.equals(Array[i][o], "FAILED") | Objects.toString(Array[i][o]).equals("404"))) {
                    testResult = "FAILED";
                    resChecker = 1;

                }

            }

        }

        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("(hh_mm_ss a)");
        String outDirect = "./Outputs/Excel/CodesToCheck_Result_" + testResult + "_" + currentDate + "_" + simpleDate.format(dateNow) + ".xls";

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
     * Создает настоящий клон массива.
     *
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
     * Выбирает строку из String [] input1D и помещает результат в String result1D.
     * Предлагает выбрать значение вручную, если в переданном массиве несколько элементов.
     */
    //Сделан не статическим для демонстрации
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
     *
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

    public void setResult1D(String result1D) {
        this.result1D = result1D;
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

    public void setResult2D(String[] result2D) {
        this.result2D = result2D;
    }
}
