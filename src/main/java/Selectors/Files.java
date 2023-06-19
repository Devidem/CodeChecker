package Selectors;

import Converts.Xls;
import Interfaces.Arrayer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Files extends Selectors implements Arrayer {
    String input = "./Inputs/Files"; //Определил конкретное значение, поскольку в проекте предусмотрена папка для входных файлов

    public String [][] toFinalArray() throws IOException {

        if (result.endsWith(".xls")){
            Xls excel = new Xls();
            return excel.toFinalArray(result);

        } else {
            System.out.println("Wrong Input Format");
            return null;
        }

    }
    public void selector() {
        File dir = new File(this.input);
        String[] arrFiles = dir.list();

        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";

        if (arrFiles.length>1) {
            System.out.println(ANSI_RED + "В папке несколько файлов:" + ANSI_RESET);

            int fNum = 1;
            for (int i = 0; i < arrFiles.length; i++) {
                System.out.println(ANSI_GREEN + fNum + "_" + arrFiles[i] + ANSI_RESET);
                fNum++;
            }

            Scanner in = new Scanner(System.in);
            System.out.print("Введите номер файла(1,2,etc.): ");
            int num = in.nextInt();
            result = input + "/" + arrFiles[num-1];

        } else {
            result = input + "/" + arrFiles [0];
        }
    }

}
