package Selectors;

import Converts.Array;
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

        Array array = new Array();
        array.setInput1D(arrFiles);
        array.selector1D();
        result = input + "/" + array.getResult1D();

    }

}
