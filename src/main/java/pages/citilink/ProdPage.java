package pages.citilink;

import enums.Locators;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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

        //Устанавливаем дополнительное время ожидания для повторной попытки загрузки страницы
        //и создаем явное ожидание для поиска проверочного элемента после обновления страницы
        int checkLoadTime = 3;
        WebDriverWait checkLoadWait = new WebDriverWait(prodDriver, Duration.ofSeconds(0));

        //Записываем начальное значение getPageLoadTimeout
        int startTimeout = (int) prodDriver.manage().timeouts().getPageLoadTimeout().getSeconds();

        //Запоминаем установленное значение ImplicitWait и меняем его на 0, чтобы не тратить лишнее время при проверках
        int impWait = (int) prodDriver.manage().timeouts().getImplicitWaitTimeout().getSeconds();
        prodDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        //Блок try-finally для гарантии возвращения ненулевого значения ImplicitWait
        try {

            promChecker:
            {
                for (int o = 0; o < promsList[0].length; o++) {

                    //Забираем значение и имя акции + вычисляем для нее xpath
                    String promoName = promsList[0][o];
                    String promoValue = promsList[1][o];
                    String xpathPromo = Locators.VarProductPromoMain.getXpathVariable(promoName);

                    for (int i = 0; i < 2; i++) {

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
                        if (o == 0 && i == 0) {
                            try {
                                WebElement checkElement = prodDriver.findElement(By.xpath(checkObjectXpath));
                                break;

                            } catch (NoSuchElementException e) {

                                try {
                                    //Обновляем страницу с увеличенным pageLoadTimeout
                                    prodDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(startTimeout + checkLoadTime));
                                    refresh();

                                    //Провеяем наличие проверочного объекта после рефреша
                                    checkLoadWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkObjectXpath)));
                                    System.out.println("Slow Loading");

                                } catch (TimeoutException | NoSuchElementException ex) {
                                    System.out.println("404");
                                    checkResult[o] = "404";
                                    break promChecker;

                                } finally {
                                    //Возвращаем изначальный pageLoadTimeout
                                    prodDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(startTimeout));
                                }
                            }
                        }
                    }
                }
            }
        } finally {
            //возвращаем implicitlyWait, который был до проверки
            prodDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(impWait));
        }
        return checkResult;
    }
}




