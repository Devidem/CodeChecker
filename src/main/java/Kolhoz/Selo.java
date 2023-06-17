package Kolhoz;

import Excel.CreateEx;
import Excel.ReadEx;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.net.UrlChecker;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Selo {
    public static void main(String[] args) throws IOException, TimeoutException {
        String Dir = "C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls";
        FileInputStream fileXLS = new FileInputStream(Dir);
        Workbook XLS = new HSSFWorkbook(fileXLS);

        ReadEx ReX = new ReadEx();
        int PriceNum = ReX.calcRows(Dir, 0);
        String [] SPrList = ReX.promArray(Dir);

//        CreateEx CeX = new CreateEx();
//        CeX.crFile();
        Workbook Result = new HSSFWorkbook();
        Sheet sheet = Result.createSheet("TestResult");
        Row FstRow = sheet.createRow(0);




        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";

        String site = "https://www.citilink.ru/";
//        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
        System.setProperty("webdriver.chrome.driver", "F:\\SelenDrives\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();



        driver.get(site);
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        for (int i = 0; i < PriceNum; i++) {
            int StartRow = 2;
            int StartCell = 1;
            String Cod = XLS.getSheetAt(0).getRow(StartRow+i).getCell(StartCell-1).getStringCellValue();
            System.out.println (Cod);

            Row rowCod = sheet.createRow(StartRow+i);
            Cell cellCoD = rowCod.createCell(0);
            cellCoD.setCellValue(Cod);

//            String site = "https://www.citilink.ru/";
//            System.setProperty("webdriver.chrome.driver", "F:\\SelenDrives\\chromedriver.exe");
//            WebDriver driver = new ChromeDriver();
//
//            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//
//            driver.get(site);
//            driver.manage().window().maximize();

            WebElement F = driver.findElement(By.xpath("//input[@type=\"search\"]"));
            F.sendKeys(Cod);

            WebDriverWait wait;
            wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.invisibilityOfElementLocated
                    (By.xpath("//*[contains(text(),'просмотренные')]")));

            System.out.println ("BeforeClick");
            WebElement S = driver.findElement(By.xpath("//*[contains(@href,'" + Cod + "')]//*[@data-meta-name=\"InstantSearchMainResult\"]")); //"//*[contains(@href,'1777568')]//*[@data-meta-name=\"InstantSearchMainResult\"]"
            System.out.println ("Founded");
            S.click();
            System.out.println ("Click");






//            for (String b : SPrList) {
//
//                System.out.println(b);
//            }

            int PromNumbs = 0;
            for (int pn=0; pn==PromNumbs; pn++){
                System.out.println ("PR");
                String PrRow = SPrList [PromNumbs];
//                System.out.println(PrRow);
                if (Objects.isNull(PrRow)){
                    pn = 0;
                }
                else {
                    PromNumbs++;
//                    System.out.println(PromNumbs);
                }
            }


//            int PromNumbs = 0;
//            for (int pn=0; pn<3; pn++){
//                String PrRow = SPrList [PromNumbs];
//                if (Objects.equals(PrRow, "null")){
//                    //pn = 0;
//                }
//                else {
//                    PromNumbs++;
//                }
//            }
//            System.out.println(PromNumbs);

            int PrList [] = new int[PromNumbs];
            for (int s_i = 0; s_i<PromNumbs; s_i++){
                PrList [s_i] = Integer.parseInt(SPrList [s_i]);
            }

//            for (int u : PrList) {
//
//                System.out.println(u);
//            }

//            int PromNumbs = 3;
//            int PrList [] = {1, 2, 4};






            for (int i_1 = 0; i_1<PromNumbs; i_1++) {
                System.out.println ("FR");
                String Star = XLS.getSheetAt(0).getRow(StartRow+i).getCell(PrList[i_1]).getStringCellValue();
                if (Objects.equals(Star, "*")) {
                    String PriceName = XLS.getSheetAt(0).getRow(0).getCell(PrList[i_1]).getStringCellValue();
                    String Xpath = "//*[@data-meta-name=\"ProductHeaderContentLayout\"]//*[contains(text(),'" + PriceName + "')]";

                    try {
                        WebElement Checker = driver.findElement(By.xpath(Xpath));
                        //Row row = sheet.createRow(StartRow+i);
                        Cell cell = rowCod.createCell(PrList[i_1]);
                        cell.setCellValue("Success");

                    } catch (Exception e) {
                       // Row row = sheet.createRow(StartRow+i);
                        Cell cell = rowCod.createCell(PrList[i_1]);
                        cell.setCellValue("Failed");

                    }
                } else {

                    String PriceName = XLS.getSheetAt(0).getRow(0).getCell(PrList[i_1]).getStringCellValue();
                    String Xpath = "//*[@data-meta-name=\"ProductHeaderContentLayout\"]//*[contains(text(),'" + PriceName + "')]";

                    try {
                        WebElement Checker = driver.findElement(By.xpath(Xpath));
                       // Row row = sheet.createRow(StartRow+i);
                        Cell cell = rowCod.createCell(PrList[i_1]);
                        cell.setCellValue("Failed");

                    } catch (Exception e) {
                       // Row row = sheet.createRow(StartRow+i);
                        Cell cell = rowCod.createCell(PrList[i_1]);
                        cell.setCellValue("Success");

                    }
                }
                if (i == (PriceNum - 1)) {
                    String NameProm = XLS.getSheetAt(0).getRow(0).getCell(PrList[i_1]).getStringCellValue();
                    Cell cell = FstRow.createCell(PrList[i_1]);
                    cell.setCellValue(NameProm);

                }

            }


//            try {
//
//
//                WebElement Checker = driver.findElement(By.xpath("//*[@data-meta-name=\"ProductHeaderContentLayout\"]//*[contains(text(),'Рассрочка 0-0-6')]"));
//                System.out.println(ANSI_GREEN + "Рассрочка 0-0-6" + ANSI_RESET);
//                driver.close();
//            }
//            catch (Exception e)

//            driver.close();
//            driver.quit();
            System.out.println (Cod);
        }

        driver.close();
        driver.quit();

        LocalDate currentDate = LocalDate.now();
        Date dateNow = new Date();
        SimpleDateFormat Sdate = new SimpleDateFormat("(hh_mm_ss a)");
        String ExName = "C:/Users/SHH/IdeaProjects/PreFinalExcel/TestResult_" + currentDate + "_" + Sdate.format(dateNow) + ".xls";
        FileOutputStream TestRes = new FileOutputStream(ExName);
        Result.write(TestRes);


    }
}
