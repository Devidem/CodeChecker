import Converts.Array;
import Selectors.InputType;
import Selen.Page;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class TestRunner2 {
    public static void main(String[] args) throws IOException {
    }
}


//public class TestRunner2 {
//    public static void main(String[] args) throws IOException {
//        String file = "./Inputs/Files";
//        String fileAbs;
//        File dir = new File(file);
//        String[] arrFiles = dir.list();
//
//        if (arrFiles.length>1){
//            System.out.println("В папке несколько файлов:");
//
//            int fNum = 1;
//            for (int i = 0; i < args.length; i++) {
//                System.out.println(fNum + "_" + arrFiles[i]);
//                fNum++;
//            }
//
//            Scanner in = new Scanner(System.in);
//            System.out.print("Введите номер файла(1,2,etc.): ");
//            int num = in.nextInt();
//            fileAbs = arrFiles[num-1];
//
//            in.close();
//            return fileAbs;
//
//        }
//
//        fileAbs = arrFiles [0];
//        return fileAbs;
//
//
//
//        System.out.println(Arrays.deepToString(arrFiles));
//
//
//
//    }
//}


//public class TestRunner2 {
//    public static void main(String[] args) throws IOException {
//        String array[][] = {{"Ga", "Ga", "Ba"}, {"Ga", "Ga", "Ba"}};
//        Array.toExcel(array, "laba", "C:/");
//
//    }
//}


//public class TestRunner2 {
//    public static void main(String[] args) throws IOException {
//        String array[] = {"Ga", "Ga", "Ba"};
//        String B [] = new String[3];
//        B[0] = array [0] + 0;
//        B[0] = "AA";
//
//        System.out.println(Arrays.deepToString(array));
//        System.out.println(Arrays.deepToString(B));
////        System.out.println(Arrays.deepToString(Codes));
//    }
//}


//public class TestRunner2 {
//    public static void main(String[] args) throws IOException {
//        String array[][] = InputType.selector("file");
//        Array.toExcel(array);
//    }
//}


//public class TestRunner2 {
//    public static void main(String[] args) throws IOException {
//        String [][] Codes = InputType.selector("file");
//        System.out.println(Arrays.deepToString(Codes));
//    }
//}


//public class TestRunner2 {
//    public static void main(String[] args) throws IOException {
//        String [][] Codes = InputType.selector("file");
//        System.out.println(Arrays.deepToString(Codes));
//    }
//}


//public class TestRunner2 {
//    public static void main(String[] args) throws IOException {
//        String [][] Codes = Xls.toFinalArray("f");
//        System.out.println(Arrays.deepToString(Codes));
//
//    }
//}


//public class TestRunner2 {
//    public static void main(String[] args) throws IOException {
//        String browser = "Chrome";
//        WebDriver driver = Browsers.selector(browser);
//        driver.manage().window().maximize();
//
//    }
//}






//public class TestRunner2 {
//    public static void main(String[] args) throws IOException {
//        int S [][] = {{1, 2, 3}, {4, 5, 6}};
//        int R [][] = {{7, 8, 9}, {10, 11, 12}};
//        int F [] = {0,0,0,};
//
//        System.arraycopy(S[0],0, R[0], 0, 2);
//        System.out.println(Arrays.deepToString(R));
////        public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
//
//    }
//}