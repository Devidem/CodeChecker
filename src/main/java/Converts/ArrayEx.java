package Converts;

import Selectors.Selector1D;
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

public class ArrayEx implements Selector1D {
    String [] input1D;
    String result1D;
    String [][] input2D;
    String [] result2D;

    public static void toExcel (String [][] array, String outName, String outPath) throws IOException {

        Workbook excelOut = new HSSFWorkbook();
        Sheet sheet = excelOut.createSheet("Test1 result");

        for (int i = 0; i<array.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o<array[0].length; o++) {
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
    }

    public static void toExcel (int [][] array, String outName, String outPath) throws IOException {

        Workbook excelOut = new HSSFWorkbook();
        Sheet sheet = excelOut.createSheet("Test1 result");

        for (int i = 0; i<array.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o<array[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(array[i][o]);

            }
        }

        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("(hh_mm_ss a)");
        String outDir = outPath + "/" + outName + "_" + currentDate + "_" + simpleDate.format(dateNow) + ".xls";

        FileOutputStream outFile = new FileOutputStream(outDir);
        excelOut.write(outFile);
    }

    public void toExcel (String outName, String outPath) throws IOException {

        Workbook excelOut = new HSSFWorkbook();
        Sheet sheet = excelOut.createSheet("Test1 result");

        for (int i = 0; i< input2D.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o< input2D[0].length; o++) {
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
    }

    public static void toExcelTest (String [][] Array) throws IOException {

        Workbook excelOut = new HSSFWorkbook();
        Sheet sheet = excelOut.createSheet("Test1 result");
        String testResult = "Passed";
        int resChecker = 0;

        for (int i = 0; i<Array.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o<Array[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(Array[i][o]);
                if (resChecker == 0 && (Objects.equals(Array[i][o] , "FAILED") | Objects.toString(Array[i][o] ).equals("404")))  {
                    testResult = "FAILED";
                    resChecker = 1;

                }

            }

        }

        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("(hh_mm_ss a)");
        String outDirect = "./Outputs/Excel/CodesToCheck_Result_" + testResult + "_" + currentDate + "_" + simpleDate.format(dateNow) + ".xls";

        FileOutputStream outFile = new FileOutputStream(outDirect);
        excelOut.write(outFile);

    }

    public static String [][] clone2d(String [][] array) {
        String [][] updated = new String[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {

            for (int j = 0; j < array[0].length; j++) {
                updated [i][j] = array [i][j] + "";
            }

        }
        return updated;

    }

    public static int [][] clone2d(int [][] array) {
        int [][] result = new int[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {

            for (int j = 0; j < array[0].length; j++) {
                result [i][j] = array [i][j] + 0;
            }

        }
        return result;

    }

    public void selector1D() {

        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";

        if (input1D.length>1) {
            System.out.println(ANSI_RED + "Выберите из:" + ANSI_RESET);

            int objectNum = 1;
            for (int i = 0; i < input1D.length; i++) {
                System.out.println(ANSI_GREEN + objectNum + "_" + input1D[i] + ANSI_RESET);
                objectNum++;
            }



            int scanNum = 0;
            while (scanNum > input1D.length | scanNum < 1) {
                System.out.print("Введите номер (1,2,etc.): ");

                Scanner in = new Scanner(System.in);
                scanNum = in.nextInt();
            }

            result1D = input1D[scanNum-1];

        } else {
            result1D = input1D [0];
        }

    }




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