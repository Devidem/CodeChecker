package tests.citilink.testngAllure;

import converters.ArrayEx;
import enums.ConstInt;
import enums.ConstString;
import enums.Locators;
import exceptions.myExceptions.MyFileIOException;
import experiments.FileManager;
import fabrics.SetDriver;
import interfaces.Retryable;
import interfaces.Screenshootable;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.NoPage;
import pages.citilink.ProdPage;
import selectors.Browsers;
import selectors.InputType;
import tests.citilink.testngAllure.supprotClasses.promChecking.MyListenerPromChecking;
import tests.citilink.testngAllure.supprotClasses.promChecking.RetryAnalyzerPromChecking;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

@Listeners(MyListenerPromChecking.class)
public class PromCheckingProvider implements Screenshootable, Retryable {
    WebDriver driver;
    String [][] fullCheckList;
    String prodCode;

    //Объявляем чек-лист для записи результатов проверки и переменная для финального ассерта - заполняется при фейлах
    //Сделано так для того, чтобы не останавливать тест после одного фейла и передать все проверки акций в отчет
    String[][] resultCheckList;
    String result;

    @BeforeSuite(groups = "provider")
    @Parameters ({"inputType", "browserName"})
    public void testPrepare(String inputType, String browserName) throws MyFileIOException {

        //Получение чеклиста для дальнейшей проверки
        InputType inpType = new InputType(inputType);
        fullCheckList = inpType.toFinalArray();

        // Копирование categories.json в allure-results
        FileManager.copyCategories();

        //Адрес сайта
        String siteLink = ConstString.CitilinkAdress.getValue();

        //Выбор браузера и его запуск + настройка
        Browsers browsers = new Browsers(browserName);
        driver = browsers.start();
        SetDriver.standard(driver);

        //Переход на сайт
        NoPage noPage= new NoPage(driver);
        noPage.get(siteLink);
    }

    //Проверка акций у товара
    @Step("Проверка промо-акций")
    @Description("Проверка отображения промо-акций на странице товара")
    @Owner("Dmitriy Kazantsev")
    @Test(groups = "provider", dataProvider = "PromVider", retryAnalyzer = RetryAnalyzerPromChecking.class, description = "ProductPromoCheck", threadPoolSize = 1)
    public void promCheck(String[][] singleCheckList) {

        //Обнуляем проверочную переменную
        result = null;

        // Переход на страницу товара
        ProdPage prodPage = new ProdPage(driver);
        prodCode = singleCheckList[0][0];
        prodPage.enterSearch(prodCode);
        prodPage.clickSearchResult(prodCode);

        // Создаем чеклист для записи итогов проверки
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
            String promoName = fullCheckList[0][o];
            String promoValue = singleCheckList[0][o];
            String xpathPromo = Locators.VarProductPromoMain.getXpathVariable(promoName);

            // Цикл с алгоритмом проверки отдельной акции
            for (int i = 0; i < 2; i++) {

                // Проверка акций
                if (Objects.equals(promoValue, "*")) {
                    try {
                        WebElement promoElement = driver.findElement(By.xpath(xpathPromo));
                        resultCheckList[0][o] = "Passed";
                        break;

                    } catch (NoSuchElementException e) {
                        resultCheckList[0][o] = "Failed";
                        result = "Failed";
                    }

                } else {
                    try {
                        WebElement promoElement = driver.findElement(By.xpath(xpathPromo));
                        resultCheckList[0][o] = "Failed";
                        result = "Failed";
                        break;

                    } catch (NoSuchElementException e) {
                        resultCheckList[0][o] = "Passed";
                    }
                }

                // Блок проверки загрузки страницы. Запускается однократно и только на первой скидке
                // Закрывает цикл, если нашел проверочный элемент (checkElement), а если нет, то ждет появления
                // Если не дождался, то вписывает "404" в ячейку и завершает проверку для кода товара (не проверяет остальные акции)
                // Если дождался, то цикл проверки скидки запускается снова
                if (o == 1 && i == 0) {
                    try {
                        WebElement checkElement = driver.findElement(By.xpath(checkObjectXpath));
                        break;

                    } catch (NoSuchElementException e) {
                        try {
                            checkLoadWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkObjectXpath)));
                            System.out.println("Slow Loading");

                        } catch (TimeoutException ex) {
                            System.out.println("Page 404");
                            resultCheckList[0][o] = "Page 404";
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
    @Test(groups = "provider")
    @DataProvider (name = "PromVider")
    public Object[][] dataMethod()  {

        //Ряд с которого начинают перечисляться коды товаров
        int startRow = ConstInt.startRow.getValue();

        Object [][] dataObject = new Object[fullCheckList.length-startRow][1];

        //Создается чек-лист для одного товара
        for (int i = startRow; i < fullCheckList.length; i++) {

            String [][] singleCheckList = new String[1][fullCheckList[0].length];

            System.arraycopy(fullCheckList[i], 0, singleCheckList[0], 0, singleCheckList[0].length);

            dataObject [i-startRow] = singleCheckList;

        }
        return dataObject;
    }

    //Закрытие браузера
    @AfterSuite(groups = "provider")
    public void closeDriver() {
        new ProdPage(driver).close();
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
