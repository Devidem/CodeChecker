package Pages;

import Selen.SelEx;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class ProdPage extends Pages {

    String prodCod;

    public void toProdPage() {

        WebElement Search = driver.findElement(By.xpath("//input[@type=\"search\"]"));
        Search.sendKeys(prodCod);

        wait.until(ExpectedConditions.invisibilityOfElementLocated
                (By.xpath("//*[contains(text(),'просмотренные')]")));

        WebElement prodlink = driver.findElement(By.xpath("//*[contains(@href,'" + prodCod + "')]//*[@data-meta-name=\"InstantSearchMainResult\"]"));

        click(prodlink);

    }

    //Проверяет список акций
    public String [] checkProms(String [][] codeProms) {
        String [] Result = new String[codeProms.length];

        String checkXpath = "//*[@data-meta-value = \"about\"]";
        WebDriverWait checkWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        promChecker:
        {
            for (int o = 0; o < codeProms.length; o++) {

                String Star = codeProms[1][o];
                String PriceName = codeProms[0][o];
                String Xpath = "//*[@data-meta-name=\"ProductHeaderContentLayout\"]//*[contains(text(),'" + PriceName + "')]";


                for (int i = 0; i<2; i++) {
                    // Блок проверки загрузки, который запускается при первом повторе. Закрывает цикл, если нашел проверочный элемент (checkElement), а если нет, то ждет появления
                    // Если не дождался, то вписывает "404" в ячейку и завершает проверку для кода товара (не проверяет остальные акции)
                    if (o == 0 && i == 1) {
                        try {
                            WebElement checkElement = driver.findElement(By.xpath(checkXpath));
                            break;

                        } catch (NoSuchElementException e) {
                            try {
                                checkWait.until(ExpectedConditions.elementToBeClickable(By.xpath(checkXpath)));
                                System.out.println("Slow Loading");

                            } catch (TimeoutException ex) {
                                System.out.println("404");
                                Result[o] = "404";
                                break promChecker;

                            }

                        }

                    }

                    // Проверка акций
                    if (Objects.equals(Star, "*")) {
                        try {
                            WebElement promFinder = driver.findElement(By.xpath(Xpath));
                            Result[o] = "Passed";
                            break;

                        } catch (NoSuchElementException e) {
                            Result[o] = "FAILED";

                        }

                    } else {
                        try {
                            WebElement promFinder = driver.findElement(By.xpath(Xpath));
                            Result[o] = "FAILED";
                            break;

                        } catch (NoSuchElementException e) {
                            Result[o] = "Passed";

                        }

                    }

                    //Отключение проверки загрузки начиная со второй скидки
                    if (o > 0) { break; }
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




