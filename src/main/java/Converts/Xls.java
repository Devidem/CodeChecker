package Converts;

import Experiments.ObjectsEx;

import Inp_Out.Interfaces.FileArrayer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class Xls extends ConvertCheckList implements FileArrayer {
    public  String[][] toFinalArray(String filePath) throws IOException {

        String [][] Codes = fileToArray(filePath, 0);
        String [][] Proms = fileToArray(filePath, 1);

        int codestartrow = 3-1; //Номер строки, с которой начинается перечисление кодов в Codes
        int promstartcell = 1;  //Номер ячейки, с которой начинается перечисление акций в Codes
        int deleted = 0; // Счетчик удаленных акций
        String promsymbol = "*";

        for (int i = 0; i< (Proms.length-2); i++) { //Цикл убирает текст из первого ряда в скидках, которые не нужно проверять + считает количество удаленных ячеек
            String starcheck = Proms [codestartrow+i] [1];

            if (!Objects.equals(starcheck, promsymbol)) {
                String promname = Proms [codestartrow+i] [0];
                for (int a = promstartcell; a <(Codes[0].length); a++) {
                    if (Objects.equals(Codes[0][a], promname)) {
                        Codes [0][a] = "";
                        deleted++;
                        break;
                    }
                }
            }
        }

        String [][] Final = new String[Codes.length][Codes[0].length-deleted]; //Делаю массив без лишних ячеек (с учетом удаленных ячеек/столбцов)

        int o_final = 0; // Свой счетчик столбцов у массива Final для цикла ниже
        for (int o=0; o<Codes[0].length; o++ ) { // Цикл для переноса данных из отредактированного массива - забираются только те, где что-то написано в 1-й(0-й) ячейке столбца

            if (!Objects.equals(Codes[0][o], "")) {

                for (int p=0; p< Codes.length; p++ ) {
                    Final [p][o_final] = Codes [p][o];
                }

                o_final++;
            }

        }
        return Final;
    }

    public String[][] fileToArray (String filePath, int sheet) throws IOException {
        FileInputStream direct = new FileInputStream(filePath);
        Workbook proms = new HSSFWorkbook(direct);

        int rows = calc_R(filePath, sheet);
        int cells = calc_C(filePath, sheet);
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
