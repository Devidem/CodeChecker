package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

//Класс с методами для странички товара
public class ProdPage extends Pages {

    //Проверяет отображение акций товара
    public String [] checkProms(String [][] promsList) {
        String [] Result = new String[promsList.length];

        String checkXpath = "//*[@data-meta-value = \"about\"]";
        WebDriverWait checkWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        promChecker:
        {
            for (int o = 0; o < promsList.length; o++) {

                String Star = promsList[1][o];
                String PriceName = promsList[0][o];
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

}




