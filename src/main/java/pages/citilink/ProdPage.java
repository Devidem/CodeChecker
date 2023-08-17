package pages.citilink;

import enums.Locators;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;


/**
 * Страница товара
 */
public class ProdPage extends CitiPage {
    public ProdPage(WebDriver driverStart) {
        super(driverStart);
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
        String [] checkResult = new String[promsList[0].length];

        //Xpath элемента проверки и время ожидания прогрузки страницы
        String checkObjectXpath = Locators.ProductAbout.getXpath();
        //Время ожидания дозагрузки страницы
        int checkLoadTime = 5;
        WebDriverWait checkLoadWait = new WebDriverWait(prodDriver, Duration.ofSeconds(checkLoadTime));

        //Запоминаем установленное значение ImplicitWait
        int impWait = (int) prodDriver.manage().timeouts().getImplicitWaitTimeout().getSeconds();

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
                    // Закрывает цикл, если нашел проверочный элемент (checkElement), а если нет, то обновляет страницу и ждет появления
                    // Если не дождался, то вписывает "404" в ячейку и завершает проверку для кода товара (не проверяет остальные акции)
                    // Если дождался, то цикл проверки скидки запускается снова
                    if (o == 0 && i==0) {
                        try {
                            WebElement checkElement = prodDriver.findElement(By.xpath(checkObjectXpath));
                            break;

                        } catch (NoSuchElementException e) {
                            try {
                                prodDriver.navigate().refresh();
                                checkLoadWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkObjectXpath)));
                                System.out.println("Slow Loading");

                            } catch (TimeoutException ex) {
                                System.out.println("404");
                                checkResult[o] = "404";
                                break promChecker;

                            }

                        }

                    }

                    // Регулируем ожидания implicitlyWait, чтобы не тратить время на ожидания после 1 шага и
                    // возвращаем прежнее значение после окончания проверки
                    if (o==0) {
                        prodDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                    } else if (o == promsList.length - 1) {
                        prodDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(impWait));
                    }
                }
            }
        }
        return checkResult;
    }
}




