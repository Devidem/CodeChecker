import converters.ExSql;
import exceptions.myExceptions.MyFileIOException;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

public class TestRunner2 {
    public static void main(String[] args) throws IOException, SQLException, MyFileIOException {


//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
//                "admin");
//
//        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//                ResultSet.CONCUR_UPDATABLE);
//
//
//        ResultSet resultSet = statement.executeQuery("SELECT * from prodcode_promo");

        String [][] info = ExSql.toFinalArray();
        System.out.println(Arrays.deepToString(info));

//        while (resultSet.next()) {
//            for (int i = 1; true; i++) {
//                try {
//                    System.out.println(resultSet.getString(i));
//                } catch (SQLException e) {
//                    break;
//                }
//            }
//        }
//        resultSet.first();
//        System.out.println(resultSet.getString(1));


//        statement.close();


    }

//public class TestRunner2 {
//    public static void main(String[] args) throws IOException {
//
//
//
//
//        File dir = new File("C:\\Users\\SHH\\IdeaProjects\\CodeChecker\\Inputs");
//        String[] arrFiles = dir.list();
//        System.out.println(Arrays.deepToString(arrFiles));
//
//
////        System.out.println(xls.calc_C("C:\\Users\\SHH\\IdeaProjects\\CodeChecker\\Inputs\\Files\\CodesToCheck.xls", 0));
//
////        FileInputStream direct = new FileInputStream("C:\\Users\\SHH\\IdeaProjects\\CodeChecker\\Inputs\\Files\\CodesToCheck.xls");
////        Workbook toArray = new HSSFWorkbook(direct);
////
////        System.out.println(Objects.toString(toArray.getSheetAt(0).getRow(10).getCell(150)));
//
//
//
//
//    }

}


//public class TestRunner2 {
//    public static void main(String[] args) {
//
//        String filePath = "C:\\Users\\SHH\\IdeaProjects\\CodeChecker\\Inputs\\Files/CodesToCheck.txt";
//        FileInputStream direct = null;
//        try {
//            direct = new FileInputStream(filePath);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        WorkbookXls proms = null;
//
//        try {
//            proms = new HSSFWorkbook(direct);
//        } catch (IOException ex) {
//            try {
//                proms = ExceptionCatcher.WorkbookXls(ex);
//            } catch (IOException e) {
//                System.err.println(e.getMessage());
//            }
//
////            System.out.println(ex.getMessage());
////            System.out.println("Введите полный адрес .xls файла: ");
////
////            Scanner in = new Scanner(System.in);
////            String newFilePath = in.nextLine();
////            direct = new FileInputStream(newFilePath);
////            proms = new HSSFWorkbook(direct);
//        }
//    }
//}


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
