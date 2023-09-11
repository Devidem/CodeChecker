package tests.citilink.testngAllure;

import converters.ExArray;
import enums.Locators;
import experiments.FileManager;
import interfaces.old.RetryableOld;
import interfaces.old.ScreenshootableOld;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.qameta.allure.testng.TestInstanceParameter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.citilink.old.ProdPageOld;
import tests.citilink.testngAllure.supprotClasses.promChecking.MyListenerPromCheckingOld;
import tests.citilink.testngAllure.supprotClasses.promChecking.RetryAnalyzerPromCheckingOld;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

@Listeners(MyListenerPromCheckingOld.class)
public class PromCheckingSingleFactory implements ScreenshootableOld, RetryableOld {
    String[][] singleCheckList;
    WebDriver driver;

    public PromCheckingSingleFactory(String[][] singleCheckList, WebDriver driver, int queueNum) {
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

    // Используем код товара как @TestInstanceParameter, благодаря чему тесты, созданные через @Factory,
    // будут отображаться отдельно в Allure
    @TestInstanceParameter("Product")
    String prodCode;

    //Объявляем чек-лист для записи результатов проверки и переменная для финального ассерта - заполняется при фейлах
    //Сделано так для того, чтобы не останавливать тест после одного фейла и передать все проверки акций в отчет
    String[][] resultCheckList;
    String result;

    // Копирование categories.json в allure-results
    @BeforeSuite(groups = "factory")
    public void allureSettings() {
        FileManager.copyCategories();
    }

    // Переход на страницу товара
    @BeforeMethod(groups = "factory")
    @Step("Открытие страницы товара")
    public void openProdPage() {
        ProdPageOld prodPage = new ProdPageOld(driver);
        prodCode = singleCheckList[1][0];
        prodPage.enterSearch(prodCode);
        prodPage.clickSearchResult(prodCode);
    }

    //Проверка акций у товара
    @Step("Проверка промо-акций")
    @Description("Проверка отображения промо-акций на странице товара")
    @Owner("Dmitriy Kazantsev")
    @Test(groups = "factory", retryAnalyzer = RetryAnalyzerPromCheckingOld.class, description = "ProductPromoCheck")
    public void promCheck() {

        resultCheckList = ExArray.clone2d(singleCheckList);

        //Xpath элемента проверки и время ожидания прогрузки страницы
        String checkObjectXpath = Locators.ProductAbout.getXpath();

        //Устанавливаем дополнительное время ожидания для повторной попытки загрузки страницы
        //и создаем явное ожидание для поиска проверочного элемента после обновления страницы
        int checkLoadTime = 3;
        WebDriverWait checkLoadWait = new WebDriverWait(driver, Duration.ofSeconds(0));

        //Записываем начальное значение getPageLoadTimeout
        int startTimeout = (int) driver.manage().timeouts().getPageLoadTimeout().getSeconds();

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
                // Закрывает цикл, если нашел проверочный элемент (checkElement), а если нет, то обновляет страницу и ждет появления
                // Если не дождался, то вписывает "404" в ячейку и завершает проверку для кода товара (не проверяет остальные акции)
                // Если дождался, то цикл проверки скидки запускается снова
                if (o == 1 && i == 0) {
                    try {
                        WebElement checkElement = driver.findElement(By.xpath(checkObjectXpath));
                        break;

                    } catch (NoSuchElementException e) {

                        try {
                            //Обновляем страницу с увеличенным pageLoadTimeout
                            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(startTimeout + checkLoadTime));
                            ProdPageOld prodPage = new ProdPageOld(driver);
                            prodPage.refresh();

                            //Провеяем наличие проверочного объекта после рефреша
                            checkLoadWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkObjectXpath)));
                            System.out.println("Slow Loading");

                        } catch (TimeoutException | NoSuchElementException ex) {
                            System.out.println("Page 404");
                            resultCheckList[1][o] = "Page 404";
                            result = "Page 404";
                            throw new RuntimeException("Page 404");

                        } finally {
                            //Возвращаем изначальный pageLoadTimeout
                            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(startTimeout));
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

    //Закрытие браузера
    @AfterSuite(groups = "factory")
    public void closeDriver() {
        new ProdPageOld(driver).close();
    }


    //Геттеры
    public String getRetryVar() {
        return result;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getScreenVariable() {
        return prodCode;
    }
}
