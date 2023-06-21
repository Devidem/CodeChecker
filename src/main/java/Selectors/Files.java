package Selectors;

import Converts.ArrayEx;
import Converts.Xls;
import Interfaces.Arrayer;

import java.io.File;
import java.io.IOException;

public class Files extends Selectors implements Arrayer {
    String input = "./Inputs/Files"; //Определил конкретное значение, поскольку в проекте предусмотрена папка для входных файлов

    public String [][] toFinalArray() throws IOException {

        if (result.endsWith(".xls")){
            Xls excel = new Xls();
            return excel.toFinalArray(result);

        } else {
            System.out.println("Wrong File");
            return null;
        }

    }
    public void selector() {
        File dir = new File(input);
        String[] arrFiles = dir.list();

        ArrayEx arrayEx = new ArrayEx();
        arrayEx.setInput1D(arrFiles);
        arrayEx.selector1D();
        result = input + "/" + arrayEx.getResult1D();

    }

}
