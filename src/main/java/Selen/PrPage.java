package Selen;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.Objects;

public class PrPage extends Pages{

    String prodCod;

    public void click (WebElement element) {
        try {
            element.click();
        } catch (TimeoutException ignored) {
        }
    }
    public void toProdPage() {

        WebElement Search = driver.findElement(By.xpath("//input[@type=\"search\"]"));
        Search.sendKeys(prodCod);

        wait.until(ExpectedConditions.invisibilityOfElementLocated
                (By.xpath("//*[contains(text(),'просмотренные')]")));

        WebElement prodlink = driver.findElement(By.xpath("//*[contains(@href,'" + prodCod + "')]//*[@data-meta-name=\"InstantSearchMainResult\"]"));
        SelEx.click(prodlink);

    }

    public String [] checkProms(String [][] codeProms) {
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
                } catch (NoSuchElementException e) {
                    Result [o] = "FAILED";
                }

            } else {
                try {
                    WebElement promFinder = driver.findElement(By.xpath(Xpath));
                    Result [o] = "FAILED";
                } catch (NoSuchElementException e) {
                    Result [o] = "Passed";
                }
            }
        }
        return Result;
    }




    //Геттеры и сеттеры
    public String getProdCod() {
        return prodCod;
    }

    public void setProdCod(String prodCod) {
        this.prodCod = prodCod;
    }

}




