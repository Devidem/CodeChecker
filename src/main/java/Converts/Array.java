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

public class Array implements Selector1D {
    String [] input1D;
    String result1D;
    String [][] input2D;
    String [] result2D;

    public static void toExcel (String [][] Array, String outName, String outPath) throws IOException {

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
        String outDir = outPath + "/" + outName + "_" + currentDate + "_" + Sdate.format(dateNow) + ".xls";

        FileOutputStream TestRes = new FileOutputStream(outDir);
        outEx.write(TestRes);
    }

    public static void toExcel (int [][] Array, String outName, String outPath) throws IOException {

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
        String outDir = outPath + "/" + outName + "_" + currentDate + "_" + Sdate.format(dateNow) + ".xls";

        FileOutputStream TestRes = new FileOutputStream(outDir);
        outEx.write(TestRes);
    }

    public void toExcel (String outName, String outPath) throws IOException {

        Workbook outEx = new HSSFWorkbook();
        Sheet sheet = outEx.createSheet("Test result");

        for (int i = 0; i< input2D.length; i++) {
            Row row = sheet.createRow(i);

            for (int o = 0; o< input2D[0].length; o++) {
                Cell cell = row.createCell(o);
                cell.setCellValue(input2D[i][o]);

            }
        }

        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat Sdate = new SimpleDateFormat("(hh_mm_ss a)");
        String outDir = outPath + "/" + outName + "_" + currentDate + "_" + Sdate.format(dateNow) + ".xls";

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
                if (resChecker == 0 && (Objects.equals(Array[i][o] , "FAILED") | Objects.toString(Array[i][o] ).equals("404")))  {
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
        int [][] updated = new int[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {

            for (int j = 0; j < array[0].length; j++) {
                updated [i][j] = array [i][j] + 0;
            }

        }
        return updated;

    }

    public void selector1D() {

        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";

        if (input1D.length>1) {
            System.out.println(ANSI_RED + "Выберите из:" + ANSI_RESET);

            int fNum = 1;
            for (int i = 0; i < input1D.length; i++) {
                System.out.println(ANSI_GREEN + fNum + "_" + input1D[i] + ANSI_RESET);
                fNum++;
            }

            Scanner in = new Scanner(System.in);

            int num = 0;
            for (int i = 0; i >= 0; i++) {
                System.out.print("Введите номер (1,2,etc.): ");
                num = in.nextInt();
                if (num <= input1D.length & num >= 1) {
                    break;
                }
            }
//            System.out.print("Введите номер (1,2,etc.): ");
//            int num = in.nextInt();
//
            result1D = input1D[num-1];

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
