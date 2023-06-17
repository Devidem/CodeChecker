package Selen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Objects;

public class Page {
    public void toProdPage(WebDriver driver, WebDriverWait wait, String prodcod) throws IOException {
        WebElement Search = driver.findElement(By.xpath("//input[@type=\"search\"]"));
        Search.sendKeys(prodcod);

        wait.until(ExpectedConditions.invisibilityOfElementLocated
                (By.xpath("//*[contains(text(),'просмотренные')]")));

        WebElement prodlink = driver.findElement(By.xpath("//*[contains(@href,'" + prodcod + "')]//*[@data-meta-name=\"InstantSearchMainResult\"]"));
        prodlink.click();



    }
    public String [] checkProms (WebDriver driver, String [][] codeProms) throws IOException {
        String Result [] = new String[codeProms.length];

        for (int o =0; o< codeProms.length; o++) {
            o = o;

            String Star = codeProms [1][o];
            String PriceName = codeProms[0][o];
            String Xpath = "//*[@data-meta-name=\"ProductHeaderContentLayout\"]//*[contains(text(),'" + PriceName + "')]";

            if (Objects.equals(Star, "*")) {
                try {
                    WebElement promFinder = driver.findElement(By.xpath(Xpath));
                    Result [o] = "Passed";
                } catch (Exception e) {
                    Result [o] = "FAILED";
                }

            } else {
                try {
                    WebElement promFinder = driver.findElement(By.xpath(Xpath));
                    Result [o] = "FAILED";
                } catch (Exception e) {
                    Result [o] = "Passed";
                }
            }
        }
        return Result;
    }

}

