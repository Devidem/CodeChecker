package org.example;

import Excel.ReadEx;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.lang.String;
import java.util.concurrent.TimeUnit;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException{
        ReadEx Nor = new ReadEx();


        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";

        FileInputStream Tes = new FileInputStream("C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls");
        Workbook R = new HSSFWorkbook(Tes);
//        String Gg = R.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
//        System.out.println(Gg);

        for (int i = 0; i < 6; i++)
        {
            String Gg = R.getSheetAt(0).getRow(i).getCell(0).getStringCellValue();
            if (Gg.equals("")) {
                i = 6;
                System.out.println(Gg);
            }
            else {
                String codt = Gg;
                String site = "https://www.citilink.ru/";
                System.setProperty("webdriver.chrome.driver", "F:\\SelenDrives\\chromedriver.exe");
                WebDriver driver = new ChromeDriver();

                driver.get(site);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                //driver.manage().timeouts().elementToBeClickable(5, TimeUnit.SECONDS);

                WebElement F = driver.findElement(By.xpath("//input[@type=\"search\"]"));
                F.sendKeys(codt);

                WebDriverWait wait;
                wait = new WebDriverWait(driver, Duration.ofSeconds(1));
                //wait.until(ExpectedConditions.elementToBeClickable
                //        (By.xpath("//*[contains(@href,'" + codt + "')]//*[@data-meta-name=\"InstantSearchMainResult\"]")));
                wait.until(ExpectedConditions.invisibilityOfElementLocated
                        (By.xpath("//*[contains(text(),'просмотренные')]")));


                WebElement S = driver.findElement(By.xpath("//*[contains(@href,'" + codt + "')]//*[@data-meta-name=\"InstantSearchMainResult\"]")); //"//*[contains(@href,'1777568')]//*[@data-meta-name=\"InstantSearchMainResult\"]"
                S.click();
                try {
                    WebElement S006 = driver.findElement(By.xpath("//*[@data-meta-name=\"ProductHeaderContentLayout\"]//*[contains(text(),'Рассрочка 0-0-6')]"));
                    System.out.println(ANSI_GREEN + "Рассрочка 0-0-6" + ANSI_RESET);
                    driver.close();
                }
                catch (Exception e)
                {
                    System.out.println(ANSI_RED + "Рассрочка 0-0-6" + ANSI_RESET);
                    driver.close();
                }
//                WebElement S006 = driver.findElement(By.xpath("//*[@data-meta-name=\"ProductHeaderContentLayout\"]//*[contains(text(),'Рассрочка 0-0-6')]")); //Выбран Content Layout без выбора столба, потому что они меняются в зависимости от размера окна. Текст Рассрочка выбран, поскольку только здесь он используется вместе с 0-0-6
//                if (S006.isDisplayed()) {
//                    System.out.println(ANSI_GREEN + "Рассрочка 0-0-6" + ANSI_RESET);
//                } else
//                {
//                    System.out.println(ANSI_RED + "Рассрочка 0-0-6" + ANSI_RESET);
//                    ;
//                }
            }

        }
    }
}


//        String CN = R.getSheet(0).getRow(2).getCell(0).getStringCellValue();
//
//
//
//
//
//
//
//
//
//
//
//        String[] Codes = new String[10];
//        Codes[0] = "1777568";
//
//        String codt = Codes[0];
//        String site = "https://www.citilink.ru/";
//        System.setProperty("webdriver.chrome.driver","F:\\SelenDrives\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//
//        driver.get(site);
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//        //driver.manage().timeouts().elementToBeClickable(5, TimeUnit.SECONDS);
//
//        WebElement F = driver.findElement(By.xpath("//input[@type=\"search\"]"));
//        F.sendKeys(codt);
//
//        WebDriverWait wait;
//        wait = new WebDriverWait(driver, Duration.ofSeconds(1));
//        //wait.until(ExpectedConditions.elementToBeClickable
//        //        (By.xpath("//*[contains(@href,'" + codt + "')]//*[@data-meta-name=\"InstantSearchMainResult\"]")));
//        wait.until(ExpectedConditions.invisibilityOfElementLocated
//                (By.xpath("//*[contains(text(),'просмотренные')]")));
//
//
//
//        WebElement S = driver.findElement(By.xpath("//*[contains(@href,'" + codt + "')]//*[@data-meta-name=\"InstantSearchMainResult\"]")); //"//*[contains(@href,'1777568')]//*[@data-meta-name=\"InstantSearchMainResult\"]"
//        S.click();
//        WebElement S006 = driver.findElement(By.xpath("//*[@data-meta-name=\"ProductHeaderContentLayout\"]//*[contains(text(),'Рассрочка 0-0-6')]")); //Выбран Content Layout без выбора столба, потому что они меняются в зависимости от размера окна. Текст Рассрочка выбран, поскольку только здесь он используется вместе с 0-0-6
//        if (S006.isDisplayed())
//        {
//            System.out.println(ANSI_GREEN + "Рассрочка 0-0-6" + ANSI_RESET);
//        }
//        else
//        {
//            System.out.println(ANSI_RED + "Рассрочка 0-0-6" + ANSI_RESET);;
//        }
//        // WebElement F = driver.findElement(By.xpath(""));
//    }
//}