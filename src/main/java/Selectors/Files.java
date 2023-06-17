package Selectors;

import Converts.ConvertCheckList;
import Converts.Xls;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Files {
    public static String[][] selector () throws IOException {

        String filePath = Files.picker();

        if (filePath.endsWith(".xls")){
            ConvertCheckList excel = new Xls();
            String inp_array [][] = excel.toFinalArray(filePath);
            return inp_array;

        }
        else {
            System.out.println("Wrong Input Format");
            return null;
        }

    }

    public static String picker () throws IOException {
        String file = "./Inputs/Files";
        String fileAbs;
        File dir = new File(file);
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
            fileAbs = "./Inputs/Files/" + arrFiles[num-1];

            in.close();
            return fileAbs;

        }

        fileAbs = arrFiles [0];
        return fileAbs;

    }
}
