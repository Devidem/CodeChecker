package Converts;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Array {
    public static void toExcel (String [][] Array, String filename, String path) throws IOException {

        Workbook outEx = new HSSFWorkbook();
        Sheet sheet = outEx.createSheet("Test result");

        for (int i = 0; i<Array.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o<Array[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(Array[i][o]);

            }
        }

        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat Sdate = new SimpleDateFormat("(hh_mm_ss a)");
        String outDir = path + "/" + filename + "_" + currentDate + "_" + Sdate.format(dateNow) + ".xls";

        FileOutputStream TestRes = new FileOutputStream(outDir);
        outEx.write(TestRes);
    }

    public static void toExcel (int [][] Array, String filename, String path) throws IOException {

        Workbook outEx = new HSSFWorkbook();
        Sheet sheet = outEx.createSheet("Test result");

        for (int i = 0; i<Array.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o<Array[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(Array[i][o]);

            }
        }

        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat Sdate = new SimpleDateFormat("(hh_mm_ss a)");
        String outDir = path + "/" + filename + "_" + currentDate + "_" + Sdate.format(dateNow) + ".xls";

        FileOutputStream TestRes = new FileOutputStream(outDir);
        outEx.write(TestRes);
    }

    public static void toExcelTest (String [][] Array) throws IOException {

        Workbook outEx = new HSSFWorkbook();
        Sheet sheet = outEx.createSheet("Test result");
        String testResult = "Passed";
        int resChecker = 0;

        for (int i = 0; i<Array.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o<Array[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(Array[i][o]);
                if (resChecker == 0 && Objects.equals(Array[i][o] , "FAILED")) {
                    testResult = "FAILED";
                    resChecker = 1;


                }

            }
        }



        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat Sdate = new SimpleDateFormat("(hh_mm_ss a)");
        String outDir = "./Outputs/Excel/CodesToCheck_Result_" + testResult + "_" + currentDate + "_" + Sdate.format(dateNow) + ".xls";

        FileOutputStream TestRes = new FileOutputStream(outDir);
        outEx.write(TestRes);
    }

    public static String [][] clone2d(String [][] array) throws IOException {
        String [][] updated = new String[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {

            for (int j = 0; j < array[0].length; j++) {
                updated [i][j] = array [i][j] + "";
            }

        }
        return updated;

    }
    public static int [][] clone2d(int [][] array) throws IOException {
        int [][] updated = new int[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {

            for (int j = 0; j < array[0].length; j++) {
                updated [i][j] = array [i][j] + 0;
            }

        }
        return updated;

    }

    public static String picker (String [] array ) throws IOException {

        String result;

        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";

        if (array.length>1) {
            System.out.println(ANSI_RED + "Найдено несколько вариантов:" + ANSI_RESET);

            int fNum = 1;
            for (int i = 0; i < array.length; i++) {
                System.out.println(ANSI_GREEN + fNum + "_" + array[i] + ANSI_RESET);
                fNum++;
            }

            Scanner in = new Scanner(System.in);
            System.out.print("Введите номер (1,2,etc.): ");
            int num = in.nextInt();

            result = array[num-1];

            return result;

        }

        result = array [0];
        System.out.println(array[0] + " наш единственный вариант!");
        return result;

    }

}
