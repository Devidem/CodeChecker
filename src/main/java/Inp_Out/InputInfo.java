package Inp_Out;

import Inp_Out.Handlers.Inp_Xls;

import java.io.IOException;

public class InputInfo {
    public static String[][] fileToArray(String In_File, int sheet) throws IOException{

        if (In_File.endsWith(".xls")){
            Inp_Xls excel = new Inp_Xls();
            String inp_array [][] = excel.fileToArray(In_File, sheet);
            return inp_array;

        }
        else {
            System.out.println("Wrong Input Format");
            String inp_array [][] = new String[0][];
            return inp_array;
        }

    }

//    public static String[][] SQLToArray(String In_File, int sheet) throws IOException{
//        System.out.println("Nothing here");
//        return new String[0][];
//    }

}
