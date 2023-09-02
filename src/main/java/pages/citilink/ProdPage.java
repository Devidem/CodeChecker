package pages.citilink;

import enums.Locators;
import fabrics.SetDriver;
import org.openqa.selenium.*;

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

        //Устанавливаем дополнительное время ожидания для повторной попытки загрузки страницы
        int checkLoadTime = 3;

        //Xpath проверочного элемента
        String checkObjectXpath = Locators.ProdPageBasket.getXpath();

        //Записываем начальное значение PageLoadTimeout и ImplicitWait
        int startTimeout = SetDriver.getPageOut(prodDriver);
        int startImplicit = SetDriver.getImpOut(prodDriver);

        //Блок try-finally для гарантии возвращения PageLoadTimeout и ImplicitWait после проверки
        try {

            promChecker:
            {
                for (int o = 0; o < promsList[0].length; o++) {

                    //Начиная со второй скидки отключаем implicitlyWait, чтобы не тратить время на ожидание.
                    //Без использования на первой скидке, могут быть проблемы с обнаружением элементов
                    //сразу после загрузки страницы.
                    if (o==2) {
                        prodDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                    }

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

                        //Останавливаем запуск дополнительной проверки начиная со второй промо-акции
                        if (o > 1) {
                            break;
                        }

                        // Блок проверки загрузки страницы. Запускается однократно и только на первой скидке
                        // Закрывает цикл, если нашел проверочный элемент (checkElement), а если нет, то обновляет страницу и ждет появления
                        // Если не дождался, то вписывает "404" в ячейку и завершает проверку для кода товара (не проверяет остальные акции)
                        // Если дождался, то цикл проверки скидки запускается снова
                        if (i == 0) {
                            try {
                                WebElement checkElement = prodDriver.findElement(By.xpath(checkObjectXpath));
                                break;

                            } catch (NoSuchElementException e) {
                                try {
                                    //Обновляем страницу с увеличенным pageLoadTimeout
                                    prodDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(SetDriver.getPageOut(prodDriver) + checkLoadTime));
                                    refresh();

                                    //Провеяем наличие проверочного объекта после рефреша
                                    WebElement checkElement = prodDriver.findElement(By.xpath(checkObjectXpath));
                                    System.out.println("Slow Loading");

                                } catch (TimeoutException | NoSuchElementException ex) {
                                    System.out.println("404");
                                    checkResult[o] = "404";
                                    break promChecker;
                                }
                            }
                        }
                    }
                }
            }
        } finally {
            //Возвращаем изначальные implicitlyWait и pageLoadTimeout
            prodDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(startImplicit));
            prodDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(startTimeout));
        }
        return checkResult;
    }
}




