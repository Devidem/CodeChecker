package Selen;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
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


            String checkXpath = "//*[@class = \"app-catalog-qpfekx e1kfx4t80\"]";
            boolean checkLoad = true;
            int cycleNum = 0;
            while (checkLoad & o==0 & cycleNum<10) {
                cycleNum++;

                if (Objects.equals(Star, "*")) {
                    try {
                        WebElement promFinder = driver.findElement(By.xpath(Xpath));
                        Result [o] = "Passed";

                        checkLoad = false;

                    } catch (NoSuchElementException e) {
                        Result [o] = "FAILED";

                        try {
                            WebElement checkElement  = driver.findElement(By.xpath(checkXpath));
                            checkLoad = false;

                        } catch (NoSuchElementException e1) {
                            checkLoad = true;

                        }

                    }

                } else {
                    try {
                        WebElement promFinder = driver.findElement(By.xpath(Xpath));
                        Result [o] = "FAILED";

                        checkLoad = false;

                    } catch (NoSuchElementException e) {
                        Result [o] = "Passed";

                        try {
                            WebElement checkElement  = driver.findElement(By.xpath(checkXpath));
                            checkLoad = false;

                        } catch (NoSuchElementException e2) {
                            checkLoad = true;

                        }
                    }
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




