package Inp_Out.Handlers;

import Experiments.ObjectsEx;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class Inp_Xls {
    public String[][] fileToArray (String in_file, int sheet) throws IOException {
        FileInputStream direct = new FileInputStream(in_file);
        Workbook proms = new HSSFWorkbook(direct);

        int rows = calc_R(in_file, sheet);
        int cells = calc_C(in_file, sheet);
//        System.out.println(rows + "" + cells);
        String [][] farra = new String[rows][cells];

        for (int i = 0; i < rows; i++) {
            for (int o = 0; o < cells; o++) {
                try {
                    farra [i][o] = ObjectsEx.toString(proms.getSheetAt(sheet).getRow(i).getCell(o));
                }
                catch (Exception NullPointerException) {
                    farra [i][o] = "";
                }

            }
        }
                return farra;
    }


    public int calc_R(String in_file, int sheet) throws IOException{

        FileInputStream Direct = new FileInputStream(in_file);
        Workbook toArray = new HSSFWorkbook(Direct);

        int StarRow = 2;
        int CycleRow = StarRow;
        int Step = 1;
        int PromNumbs = 0;

        for (int i = 0;  Step!=0 ; i++) {
            try {
                if (!Objects.equals(ObjectsEx.toString(toArray.getSheetAt(sheet).getRow(CycleRow).getCell(0)), "")) {
                    Step = Step * 2;
                    CycleRow = CycleRow + Step;

                } else if (Objects.equals(ObjectsEx.toString(toArray.getSheetAt(sheet).getRow(CycleRow).getCell(0)), "") & Step!=1) {
                    Step = Step / 2;
                    CycleRow = CycleRow - Step;
                } else if (Objects.equals(ObjectsEx.toString(toArray.getSheetAt(sheet).getRow(CycleRow).getCell(0)), "") & Step==1) {
                    PromNumbs = CycleRow - StarRow;
                    Step = Step - 1;
                }
                else {
                    System.out.println("Inp_Out.Handlers.calc_R is Broken");
                }
            }
            catch (Exception NullPointerException) {
                if (Step!=1) {
                    Step = Step / 2;
                    CycleRow = CycleRow - Step;
                } else if (Step==1) {
                    PromNumbs = CycleRow - StarRow;
                    Step = Step - 1;
                }
                else {
                    System.out.println("Inp_Out.Handlers.calc_R is Broken");
                }
            }
//            if (!Objects.equals(ObjectsEx.toString(toArray.getSheetAt(sheet).getRow(CycleRow).getCell(0)), "")) {
//                Step = Step * 2;
//                CycleRow = CycleRow + Step;
//
//            } else if (Objects.equals(ObjectsEx.toString(toArray.getSheetAt(sheet).getRow(CycleRow).getCell(0)), "") & Step!=1) {
//                Step = Step / 2;
//                CycleRow = CycleRow - Step;
//            } else if (Objects.equals(ObjectsEx.toString(toArray.getSheetAt(sheet).getRow(CycleRow).getCell(0)), "") & Step==1) {
//                PromNumbs = CycleRow - StarRow;
//                Step = Step - 1;
//            }
//            else {
//                System.out.println("Inp_Out.Handlers.calc_R is Broken");
//            }

        }
        return PromNumbs+StarRow;
    }


    public int calc_C(String dir, int sheet) throws IOException{

        FileInputStream Direct = new FileInputStream(dir); //C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls
        Workbook toArray = new HSSFWorkbook(Direct);

        int CycleCell = 0;
        int Step = 1;
        int PromNumbs = 0;


        for (int i = 0;  Step!=0 ; i++) {
            if (!Objects.equals(ObjectsEx.toString(toArray.getSheetAt(sheet).getRow(0).getCell(CycleCell)), "")) {
                Step = Step * 2;
                CycleCell = CycleCell + Step;
//                System.out.println("Beep");

            } else if (Objects.equals(ObjectsEx.toString(toArray.getSheetAt(sheet).getRow(0).getCell(CycleCell)), "") & Step!=1) {
                Step = Step / 2;
                CycleCell = CycleCell - Step;

            } else if (Objects.equals(ObjectsEx.toString(toArray.getSheetAt(sheet).getRow(0).getCell(CycleCell)), "") & Step==1) {
                PromNumbs = CycleCell;
                Step = Step - 1;

            }
            else {
                System.out.println("Inp_Out.Handlers.calc_C is Broken");
            }

        }
        return PromNumbs;
    }

}