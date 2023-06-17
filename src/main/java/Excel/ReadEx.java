package Excel;
import Excel.ReadEx;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class ReadEx {
    public void numProms(String dir) throws IOException {
        FileInputStream Direct = new FileInputStream(dir); //C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls
        Workbook Proms = new HSSFWorkbook(Direct);

//        ReadEx REX = new ReadEx();
//        int rows = REX.calcRows(dir);
//        for (int i = 0; i < rows; i++) {
//
//
//        }
    }


    public String[] promArray (String dir) throws IOException {
        FileInputStream Direct = new FileInputStream(dir); //C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls
        Workbook Proms = new HSSFWorkbook(Direct);

        ReadEx Nor = new ReadEx();
        int rows = Nor.calcRows(dir, 1);


        String[] promList = new String[rows];
        int arrNum = 0;

        for (int i = 0; i < rows; i++) {
            String Prom;
            String Check;

            int forRow = i + 2;
            int promIndex = forRow - 1;
            Prom = Proms.getSheetAt(1).getRow(forRow).getCell(0).getStringCellValue();
            Check = Proms.getSheetAt(1).getRow(forRow).getCell(1).getStringCellValue();
            //System.out.println(Prom);
            //System.out.println(Check);

            if (Objects.equals(Check, "*")) {
                promList [arrNum] = String.valueOf(promIndex);
                arrNum++;
                //System.out.println("waw");
            }

        }


        //String [][] CheckingArray = new String [rows][cells];

        return promList;
    }
    public void write(String dir) throws IOException{
        FileInputStream Direct = new FileInputStream(dir); //C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls
        Workbook toArray = new HSSFWorkbook(Direct);
        String Gg = toArray.getSheetAt(0).getRow(2).getCell(0).getStringCellValue();
        System.out.println(Gg);

    }
    public int calcRows(String dir, int sheet) throws IOException{
         // Целью является создание массива с указаниями для проверкиъ

        FileInputStream Direct = new FileInputStream(dir); //C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls
        Workbook toArray = new HSSFWorkbook(Direct);
        //Узнаем количество проверяемых акций
        //Для начала выясняем их общее количество


        int StartCell = 2;              //Номер ячейки, с которой начинаются записываться скидки
        int Step = 2;                   //Шаг используемый для алгоритма поиска последнего значения
        int PromNumbs = 0;              //Счетчик акций

//        for (int i = 0; i < 100; i++) {
//
//            String F = toArray.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
//        }

        for (int i = 0;  Step!=0 ; i++) {
//            System.out.println("WTF");
//            System.out.println(StartCell);
//            System.out.println(Step);
//            System.out.println(toArray.getSheetAt(1).getRow(StartCell).getCell(0).getStringCellValue());
            if (toArray.getSheetAt(sheet).getRow(StartCell).getCell(0).getStringCellValue() != "" ) {
                StartCell = StartCell + Step;

            } else if (toArray.getSheetAt(sheet).getRow(StartCell).getCell(0).getStringCellValue() == "" & Step!=1) {
                Step = Step / 2;
                StartCell = StartCell - Step;
            } else if (toArray.getSheetAt(sheet).getRow(StartCell).getCell(0).getStringCellValue() == "" & Step==1) {
                PromNumbs = StartCell - 2;
                Step = Step - 1;
                //System.out.println(PromNumbs);
            }
            else {
                System.out.println("WTF");
            }

        }
        return PromNumbs;






//        String Gg = toArray.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
//        System.out.println(Gg);

    }
}
