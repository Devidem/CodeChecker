package pages.citilink;

import locators.Locators;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;


/**
 * Страница товара
 */
public class ProdPage extends CitiPage {
    public ProdPage(WebDriver driverStart, WebDriverWait wait) {
        super(driverStart, wait);
    }

    final WebDriver prodDriver = getDriver();

    /**
     * Проверяет отображение акций на странице товара.
     * @param promsList Двумерный массив включающий в себя 2 строки без указания кода товара -
     *                  с названиями акций и указаниями о необходимости их проверок (* - должна отображаться, в остальных случаях - нет).
     * @return Возвращает одномерный String массив с результатами проверок.
     */
    public String [] checkProms(String [][] promsList) {

        //Массив для результата проверки (строка, которая будет вставлена напротив кода товара)
        String [] checkResult = new String[promsList.length];

        //Xpath элемента проверки и время ожидания прогрузки страницы
        String checkObjectXpath = Locators.ProductAbout.getXpath();
        //Время ожидания дозагрузки страницы
        int checkLoadTime = 5;
        WebDriverWait checkLoadWait = new WebDriverWait(prodDriver, Duration.ofSeconds(checkLoadTime));

        promChecker:
        {
            for (int o = 0; o < promsList.length; o++) {

                //Забираем значение и имя акции + вычисляем для нее xpath
                String promoValue = promsList[1][o];
                String promoName = promsList[0][o];
                String xpathPromo = Locators.VarProductPromoMain.getXpathVariable(promoName);

                for (int i = 0; i<2; i++) {

                    // Проверка акций
                    if (Objects.equals(promoValue, "*")) {
                        try {
                            WebElement promoElement = prodDriver.findElement(By.xpath(xpathPromo));
                            checkResult[o] = "Passed";
                            break;

                        } catch (NoSuchElementException e) {
                            checkResult[o] = "FAILED";

                        }

                    } else {
                        try {
                            WebElement promoElement = prodDriver.findElement(By.xpath(xpathPromo));
                            checkResult[o] = "FAILED";
                            break;

                        } catch (NoSuchElementException e) {
                            checkResult[o] = "Passed";

                        }

                    }

                    // Блок проверки загрузки страницы. Запускается однократно и только на первой скидке
                    // Закрывает цикл, если нашел проверочный элемент (checkElement), а если нет, то ждет появления
                    // Если не дождался, то вписывает "404" в ячейку и завершает проверку для кода товара (не проверяет остальные акции)
                    // Если дождался, то цикл проверки скидки запускается снова
                    if (o == 0 && i==0) {
                        try {
//                            System.out.println("Проверка страницы");
                            WebElement checkElement = prodDriver.findElement(By.xpath(checkObjectXpath));
//                            System.out.println("Нашел");
                            break;

                        } catch (NoSuchElementException e) {
                            try {
//                                System.out.println("Стал ждать");
                                checkLoadWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkObjectXpath)));
                                System.out.println("Slow Loading");

                            } catch (TimeoutException ex) {
                                System.out.println("404");
                                checkResult[o] = "404";
                                break promChecker;

                            }

                        }

                    }

                }

            }

        }
        return checkResult;
    }

}




