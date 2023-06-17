package Excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class CreateEx {
    public String[][] createArray (String dir) throws IOException {
        ReadEx Nor = new ReadEx();
        int rows = Nor.calcRows("C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls", 0);
        int cells = Nor.calcRows("C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls", 1);
        String [][] CheckingArray = new String [rows][cells];

        return CheckingArray;
    }
     public void crFile() throws FileNotFoundException {
        FileOutputStream TestRes = new FileOutputStream("C:/Users/SHH/IdeaProjects/PreFinalExcel/TestResult.xls");

        Workbook Check = new HSSFWorkbook();

        //LocalDate currentDate = LocalDate.now();

    }

}
