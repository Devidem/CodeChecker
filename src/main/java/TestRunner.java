import Converts.Array;
import Selectors.Browsers;
import Selectors.InputType;
import Selectors.Sites;
import Selen.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.


public class TestRunner {
    public static void main(String[] args) throws IOException {
        String browser_name = "chrome";
        String site_name = "citilink";
        String input_type = "file";

        String [][] Codes = InputType.selector(input_type);
        site_name = Sites.selector(site_name);
        WebDriver driver = Browsers.selector(browser_name);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        driver.get(site_name);

        WebDriverWait wait;
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));


        int startrow = 2;
        int startcell = 1;
        int arrow = 0;
        int arcel = 0;
        String[][] Result = Codes;
        for (int i =0; i< Codes.length-startrow; i++) {
            arrow = i+startrow;

            String prodcod = Codes [arrow][0];

            Page page = new Page();
            page.toProdPage(driver, wait, prodcod);
//
//            WebElement Search = driver.findElement(By.xpath("//input[@type=\"search\"]"));
//            Search.sendKeys(prodcod);
//
//            wait.until(ExpectedConditions.invisibilityOfElementLocated
//                    (By.xpath("//*[contains(text(),'просмотренные')]")));
//
//            WebElement prodlink = driver.findElement(By.xpath("//*[contains(@href,'" + prodcod + "')]//*[@data-meta-name=\"InstantSearchMainResult\"]"));
//            prodlink.click();

//            String [] codeProms = Result [arrow];
//            System.out.println("1");
            String [][] codeProms = new String[2][Codes[0].length-startcell];
//            System.out.println("2");
            System.arraycopy(Result [0], 1, codeProms[0], 0, codeProms[0].length);
//            System.out.println("3");
            System.arraycopy(Result [arrow], 1, codeProms[1], 0, codeProms[0].length);
//            System.out.println("4");

            System.arraycopy(page.checkProms(driver, codeProms), 0, Result[arrow], 1, codeProms[0].length);
//            System.out.println("5");

            //        System.arraycopy(S[0][0],0, R[0][0], 0, 2);
//        System.out.println(Arrays.deepToString(R));
////        public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)


//            for (int o =0; o< Codes[0].length-startcell; o++) {
//                arcel = o + startcell;
//
//                String Star = Codes [arrow][arcel];
//                String PriceName = Codes[0][arcel];
//                String Xpath = "//*[@data-meta-name=\"ProductHeaderContentLayout\"]//*[contains(text(),'" + PriceName + "')]";
//
//                if (Objects.equals(Star, "*")) {
//                    try {
//                        WebElement promFinder = driver.findElement(By.xpath(Xpath));
//                        Result [arrow][arcel] = "Passed";
//                    } catch (Exception e) {
//                        Result [arrow][arcel] = "Failed";
//                    }
//
//                } else {
//                    try {
//                        WebElement promFinder = driver.findElement(By.xpath(Xpath));
//                        Result [arrow][arcel] = "Failed";
//                    } catch (Exception e) {
//                        Result [arrow][arcel] = "Passed";
//                    }
//                }
//            }

        }
        System.out.println(Arrays.deepToString(Result));
        driver.close();

//        LocalDate currentDate = LocalDate.now();
//        Date dateNow = new Date();
//        SimpleDateFormat Sdate = new SimpleDateFormat("(hh_mm_ss a)");
//        String outDir = "./Outputs/Excel/CodesToCheck_Result_" + currentDate + "_" + Sdate.format(dateNow) + ".xls";
////        FileOutputStream out = new FileOutputStream(outDir);
        Array.toExcelTest(Result);




    }
}




//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//
//        Xls Xls = new Xls();
//        String [][] Codes = Xls.fileToArray("./Inputs/Excel/CodesToCheck.xls", 0);
//        String [][] Proms = Xls.fileToArray("./Inputs/Excel/CodesToCheck.xls", 1);
//        String[][] n = Xls.fileToArray("./Inputs/Excel/CodesToCheck.xls", 1);
//
//        int codestartrow = 3-1; //Номер строки, с которой начинается перечисление кодов в Codes
//        int promstartcell = 1;  //Номер ячейки, с которой начинается перечисление акций в Codes
//        int deleted = 0; // Счетчик удаленных акций
//        String promsymbol = "*";
//
////        System.out.println("1");
//
//        for (int i = 0; i< (Proms.length-2); i++) {
////            System.out.println("П");
//            String starcheck = Proms [codestartrow+i] [1];
//
//            if (!Objects.equals(starcheck, promsymbol)) {
////                System.out.println("2");
//                String promname = Proms [codestartrow+i] [0];
//                for (int a = promstartcell; a <(Codes[0].length); a++) {
////                    System.out.println("C");
//                    if (Objects.equals(Codes[0][a], promname)) {
////                        System.out.println("3");
//                        Codes [0][a] = "";
//                        deleted++;
//                        break;
//                    }
//                }
//            }
//
//        }
//
////        System.out.println(deleted);
//
//        String [][] Final = new String[Codes.length][Codes[0].length-deleted];
//        int o_final = 0; // Свой счетчик столбцов у массива Final для цикла ниже
//
//        for (int o=0; o<Codes[0].length; o++ ) {
//
//            if (!Objects.equals(Codes[0][o], "")) {
//
//                for (int p=0; p< Codes.length; p++ ) {
//                    Final [p][o_final] = Codes [p][o];
//                }
//
//                o_final++;
//            }
//
//        }
//        System.out.println(Arrays.deepToString(Codes));
//        System.out.println(Arrays.deepToString(Proms));
//        System.out.println(Arrays.deepToString(Final));
//    }
//}


//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        Browser r = new Browser();
//        WebDriver driver = r.go("chrome");
//        driver.get("https://www.citilink.ru/");
//    }
//}


//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        String browser = "ChromeBoobaRoom";
//        browser = browser.toLowerCase();
//        System.out.println(browser);
//        if (browser.contains("chrome")) {
//            System.setProperty("webdriver.chrome.driver", "F:\\SelenDrives\\chromedriver.exe");
//        } else {
//            System.out.println("Unknown Browser");
//        }
//    }
//}


//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        Boolean F = null;
//        String n = Objects.toString(F);
//
//        System.out.println(n);
//    }
//}


//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        InputInfo F = new InputInfo();
//        String[][] n = F.fileToArray("C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls", 1);
//
//        System.out.println(Arrays.deepToString(n));
//    }
//}


//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        Inp_Xls F = new Inp_Xls();
//        String[][] n = F.xls_to_array("C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls", 1);
//
//        System.out.println(Arrays.deepToString(n));
//    }
//}


//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        Inp_Xls F = new Inp_Xls();
//        int n = F.calc_C("C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls", 0);
//        System.out.println(n);
//    }
//}

//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        Inp_Xls F = new Inp_Xls();
//        int n = F.calc_R("C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls", 1);
//        System.out.println(n);
//    }
//}


//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        String Dir = "C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls";
//
//        ReadEx ReX = new ReadEx();
//        String [] SPrList = ReX.promArray(Dir);
//        for (String b : SPrList) {
//
//            System.out.println(b);
//        }
//        System.out.println("--------");
//        String PrRow = SPrList [3];
//        System.out.println(PrRow);
//        if (Objects.isNull(PrRow)) {
//            System.out.println("null");
//        }
//        else {
//            System.out.println("WTF");
//        }


//        int PromNumbs = 0;
//        for (int pn=0; pn==PromNumbs; pn++){
//            String PrRow = SPrList [PromNumbs];
//            System.out.println(PrRow);
//            if (PrRow=="null"){
//                pn = 0;
//            }
//            else {
//                PromNumbs++;
//                System.out.println(PromNumbs);
//            }
//        }
//    }
//    }


//public class TestRunner {
//    public static void main(String[] args) throws IOException {
////        ReadEx REX = new ReadEx();
////        String dir = "C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls";
////        String[] TestList = REX.promArray(dir);
////
////        for (String i : TestList) {
////
////            System.out.println(i);
////        }
//        LocalDate currentDate = LocalDate.now();
//        Date dateNow = new Date();
//        SimpleDateFormat Sdate = new SimpleDateFormat("(hh_mm_ss a)");
//        String ExName = "C:/Users/SHH/IdeaProjects/PreFinalExcel/TestResult_" + currentDate + "_" + Sdate.format(dateNow) + ".xls";
//        System.out.println(ExName);
//    }
//}





//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        FileOutputStream TestRes = new FileOutputStream("C:/Users/SHH/IdeaProjects/PreFinalExcel/TestResult.xlsx");
//        XSSFWorkbook Result = new XSSFWorkbook();
//        XSSFSheet sheet = Result.createSheet("TestResult");
//
//        int PriceNum = 3;
//
//        for (int i = 0; i < PriceNum; i++) {
//            int StartRow = 2;
//            int StartCell = 1;
//            String Cod = "Cod tovara";
//
//            XSSFRow A = sheet.createRow(StartRow + i);
//            XSSFCell B = A.createCell(0);
//            B.setCellValue("Number");
//
//            int PromNumbs = 2;
//            int[] PrList = new int[]{1, 2, 3};
//
//            //XSSFRow F = sheet.createRow(StartRow + i);
//            XSSFCell G = A.createCell(PrList[0]);
//            G.setCellValue("Number");
//
////            for (int i_1 = 0; i_1<PromNumbs; i_1++) {
////                Row row = sheet.createRow(StartRow+i);
////                Cell cell = row.createCell(PrList[i_1]);
////                cell.setCellValue("Number");
////            }
//
//        }
//
//        Result.write(TestRes);
//        TestRes.close();
//    }
//}

//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        FileOutputStream TestRes = new FileOutputStream("C:/Users/SHH/IdeaProjects/PreFinalExcel/TestResult.xls");
//        Workbook Result = new HSSFWorkbook();
//        Sheet sheet = Result.createSheet("TestResult");
//
//        int PriceNum = 3;
//
//        for (int i = 0; i < PriceNum; i++) {
//            int StartRow = 2;
//            int StartCell = 1;
//            String Cod = "Cod tovara";
//
//            Row A = sheet.createRow(StartRow+i);
//            Cell B = A.createCell(0);
//            B.setCellValue(Cod);
//
//            int PromNumbs = 2;
//            int [] PrList = new int [] { 1, 2, 3 };
//
//            Row F = sheet.createRow(StartRow+i);
//            Cell G = F.createCell(PrList[1]);
//            G.setCellValue("Number");
//
////            for (int i_1 = 0; i_1<PromNumbs; i_1++) {
////                Row row = sheet.createRow(StartRow+i);
////                Cell cell = row.createCell(PrList[i_1]);
////                cell.setCellValue("Number");
////            }
//
//        }
//
//                Result.write(TestRes);
//        TestRes.close();
//    }
//    }



//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        String dir = "C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls";
//        FileInputStream Direct = new FileInputStream(dir); //C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls
//        Workbook Proms = new HSSFWorkbook(Direct);
//
//        ReadEx Nor = new ReadEx();
//        int rows = Nor.calcRows(dir, 1);
//
//        String[] promList = new String[rows];
//        int arrNum = 0;
//
//        for (int i = 0; i < rows; i++) {
//            String Prom;
//            String Check;
//
//            int forRow = i + 2;
//            Prom = Proms.getSheetAt(1).getRow(forRow).getCell(0).getStringCellValue();
//            Check = Proms.getSheetAt(1).getRow(forRow).getCell(1).getStringCellValue();
//            //System.out.println(Prom);
//            //System.out.println(Check);
//
//            if (Objects.equals(Check, "*")) {
//                promList [arrNum] = Prom;
//                arrNum++;
//                //System.out.println("waw");
//            }
//
//        }
//        for (String i : promList) {
//
//            System.out.println(i);
//        }
//        System.out.println(promList[5]);
//    }
//}







//public class TestRunner {
//    public static void main(String[] args) throws IOException {
//        // Целью является создание массива с указаниями для проверки
//        String dir = "C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls";
//
//        FileInputStream Direct = new FileInputStream(dir); //C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls
//        Workbook toArray = new HSSFWorkbook(Direct);
//        //Узнаем количество проверяемых акций
//        //Для начала выясняем их общее количество
//
//
//        int StartCell = 2;              //Номер ячейки, с которой начинаются записываться скидки
//        int Step = 2;                   //Шаг используемый для алгоритма поиска последнего значения
//        int PromNumbs = 0;              //Счетчик акций
//
////        for (int i = 0; i < 100; i++) {
////
////            String F = toArray.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
////        }
//
//        for (int i = 0; i < 100 & Step!=0 ; i++) {
////            System.out.println("WTF");
////            System.out.println(StartCell);
////            System.out.println(Step);
////            System.out.println(toArray.getSheetAt(1).getRow(StartCell).getCell(0).getStringCellValue());
//            if (toArray.getSheetAt(1).getRow(StartCell).getCell(0).getStringCellValue() != "" ) {
//                StartCell = StartCell + Step;
//
//            } else if (toArray.getSheetAt(1).getRow(StartCell).getCell(0).getStringCellValue() == "" & Step!=1) {
//                Step = Step / 2;
//                StartCell = StartCell - Step;
//            } else if (toArray.getSheetAt(1).getRow(StartCell).getCell(0).getStringCellValue() == "" & Step==1) {
//                PromNumbs = StartCell - 2;
//                Step = Step - 1;
//                System.out.println(PromNumbs);
//
//            }
//            else {
//                System.out.println("WTF");
//            }
//
//        }






//        String Gg = toArray.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
//        System.out.println(Gg);

//    }
//}