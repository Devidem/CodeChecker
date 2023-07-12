package tests.ng;

import converters.ArrayEx;
import locators.Locators;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.citilink.ProdPage;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

public class PromChecking {
    String[][] singleCheckList;
    WebDriver driver;

    public PromChecking(String[][] singleCheckList, WebDriver driver, int queueNum) {
        this.singleCheckList = singleCheckList;
        this.driver = driver;
        this.queueNum = queueNum;
    }

    // Обеспечиваем вызов тестов по порядку - изначально в @Factory порядок вызова "случайный" и зависит от значения метода toString() класса
    int queueNum;
    @Override
    public String toString() {
        return "[" + getClass().getName() + " " + queueNum + "]";
    }

    //Объявляем чек-лист для записи результатов проверки и переменная для финального ассерта - заполняется при фейлах
    //Сделано так для того, чтобы не останавливать тест после одного фейла и передать все проверки акций в отчет
    String[][] resultCheckList;
    String result;


    // Переход на страницу товара
    @BeforeMethod(groups = "factory")
    public void openProdPage() {
        ProdPage prodPage = new ProdPage(driver);
        String prodCode = singleCheckList[1][0];
        prodPage.enterSearch(prodCode);
        prodPage.clickSearchResult(prodCode);
    }

    //Проверка акций у товара
    @Test(groups = "factory", retryAnalyzer = RetryAnalyzer.class)
    public void promCheck() {

        resultCheckList = ArrayEx.clone2d(singleCheckList);

        //Xpath элемента проверки и время ожидания прогрузки страницы
        String checkObjectXpath = Locators.ProductAbout.getXpath();

        //Время ожидания дозагрузки страницы
        int checkLoadTime = 5;
        WebDriverWait checkLoadWait = new WebDriverWait(driver, Duration.ofSeconds(checkLoadTime));

        //Запоминаем установленное значение ImplicitWait
        int impWait = (int) driver.manage().timeouts().getImplicitWaitTimeout().getSeconds();

        //Проверка списка акций у товара
        for (int o = 1; o < singleCheckList[0].length; o++) {

            //Забираем значение и имя акции + вычисляем для нее xpath
            String promoName = singleCheckList[0][o];
            String promoValue = singleCheckList[1][o];
            String xpathPromo = Locators.VarProductPromoMain.getXpathVariable(promoName);

            // Цикл с алгоритмом проверки отдельной акции
            for (int i = 0; i < 2; i++) {

                // Проверка акций
                if (Objects.equals(promoValue, "*")) {
                    try {
                        WebElement promoElement = driver.findElement(By.xpath(xpathPromo));
                        resultCheckList[1][o] = "Passed";
                        break;

                    } catch (NoSuchElementException e) {
                        resultCheckList[1][o] = "Failed";
                        result = "Failed";
                    }

                } else {
                    try {
                        WebElement promoElement = driver.findElement(By.xpath(xpathPromo));
                        resultCheckList[1][o] = "Failed";
                        result = "Failed";
                        break;

                    } catch (NoSuchElementException e) {
                        resultCheckList[1][o] = "Passed";

                    }

                }

                // Блок проверки загрузки страницы. Запускается однократно и только на первой скидке
                // Закрывает цикл, если нашел проверочный элемент (checkElement), а если нет, то ждет появления
                // Если не дождался, то вписывает "404" в ячейку и завершает проверку для кода товара (не проверяет остальные акции)
                // Если дождался, то цикл проверки скидки запускается снова
                if (o == 1 && i == 0) {
                    try {
//                            System.out.println("Проверка страницы");
                        WebElement checkElement = driver.findElement(By.xpath(checkObjectXpath));
//                            System.out.println("Нашел");
                        break;

                    } catch (NoSuchElementException e) {
                        try {
//                                System.out.println("Стал ждать");
                            checkLoadWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkObjectXpath)));
                            System.out.println("Slow Loading");

                        } catch (TimeoutException ex) {
                            System.out.println("Page 404");
                            resultCheckList[1][o] = "Page 404";
                            result = "Page 404";
                            throw new RuntimeException("Page 404");

                        }

                    }

                }

                // Регулируем ожидания implicitlyWait, чтобы не тратить время на ожидания после 1 шага и
                // возвращаем прежнее значение после окончания проверки
                if (o == 1) {
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                } else if (o == singleCheckList.length - 2) {
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(impWait));
                }

            }

        }

        System.out.println(Arrays.deepToString(resultCheckList));
        Assert.assertNull(result);
    }

    //Закрытие браузера после всех проверок
    @AfterSuite(groups = "factory")
    public void closeDriver() {
        driver.close();
    }
    //Геттер для RetryAnalizer
    public String getResult() {
        return result;
    }
}
