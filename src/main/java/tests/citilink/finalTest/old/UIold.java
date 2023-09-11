package tests.citilink.finalTest.old;

import buffers.BufferDriver;
import buffers.BufferSuiteVar;
import buffers.PromCheckApiUiBuffer;
import converters.ExArray;
import enums.ConstInt;
import enums.Locators;
import exceptions.myExceptions.MyFileIOException;
import exceptions.myExceptions.MyInputParamException;
import experiments.ApiRequests;
import experiments.FanticProdCode;
import fabrics.old.SetDriverOld;
import interfaces.Retryable;
import interfaces.Screenshootable;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.citilink.old.ProdPageOld;
import selectors.InputType;
import tests.citilink.finalTest.supportClasses.MyListenerPromChecking;
import tests.citilink.finalTest.supportClasses.MyRetryAnalyzerPromChecking;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Listeners(MyListenerPromChecking.class)
public class UIold implements Screenshootable, Retryable {

    //Мапа для удобного получения соответствующего драйвера в Listeners
    private final HashMap <String, WebDriver> codeDriver = new HashMap<>();

    //Мапа для хранения переменной result для финального Ассерта
    private final HashMap <String, String> codeResult = new HashMap<>();

    //Мапа хранящая переменную true/false, в которой мы определяем необходимость обработки RetryAnalyzer'ом
    private final HashMap <String, Boolean> retryValue = new HashMap<>();


    //Проверка акций у товара
    @Step("Проверка промо-акций через UI")
    @Description("Проверка отображения промо-акций на странице товара")
    @Owner("Dmitriy Kazantsev")
    @Test(groups = "UI", dataProvider = "UI_Vider", retryAnalyzer = MyRetryAnalyzerPromChecking.class,
            alwaysRun = true, priority = 2)
    public void promCheckUI(FanticProdCode product) {

        //Разворачивается фантик и записываем код товара
        String[][] singleCheckList = product.getSingleCheckList();
        String prodCode = singleCheckList[1][0];

        //Создаем чек-лист для записи итогов проверки
        String[][] resultCheckList = ExArray.clone2d(singleCheckList);

        //Добавляем переменную запуска RetryAnalyzer (true - запускать обработку, false - не запускать)
        //Изначально true, но меняется на false, если причиной фейла является неправильное отображение элементов
        retryValue.put(prodCode, true);

        //Добавляем финальную проверочную переменную
        codeResult.put(prodCode, null);

        //Устанавливаем добавочное время ожидания для повторной попытки загрузки страницы
        int checkLoadTime = 3;
        //Xpath проверочного элемента для повторной попытки загрузки страницы
        String checkObjectXpath = Locators.ProdPageBasket.getXpath();

        //Забираем драйвер из буфера
        WebDriver driver;
        try {
            //В соответствии с входным параметром из Suite
            driver = BufferDriver.getDriver(BufferSuiteVar.get("browserName"));
        } catch (MyInputParamException e) {
            //Указываем Chrome как браузер по умолчанию на случай некорректного указания параметра в Suite
            //(сделано для примера обработки собственных исключений)
            try {
                System.out.println(e.getMessage());
                System.out.println("Выбран по умолчанию Chrome");
                driver = BufferDriver.getDriver("chrome");
            } catch (MyInputParamException ex) {
                //Здесь мы не должны оказываться
                throw new RuntimeException(ex);
            }
        }

        //Записываем начальное значение PageLoadTimeout и ImplicitWait
        int startTimeout = SetDriverOld.getPageOut(driver);
        int startImplicit = SetDriverOld.getImpOut(driver);

        //Запоминаем драйвер в мапе
        codeDriver.put(prodCode, driver);

        //Блок try-finally для гарантии возвращения WebDriver в буфер в изначальном состоянии
        try{

            //Настраиваем драйвер
            SetDriverOld.standardManual(driver, 2, 3);

            //Используем Api для получения ссылки страницы товара
            String productLink = ApiRequests.getProdLink(prodCode);

            // Переходим на страницу товара
            ProdPageOld prodPage = new ProdPageOld(driver);
            prodPage.get(productLink);


            //Проверяем список акций у товара
            for (int o = 1; o < singleCheckList[0].length; o++) {

                //Начиная со второй скидки отключаем implicitlyWait, чтобы не тратить время на ожидание.
                //Без использования на первой скидке, могут быть проблемы с обнаружением элементов
                //сразу после загрузки страницы.
                if (o==2) {
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                }

                //Забираем значение и имя акции + вычисляем для нее xpath
                String promoName = singleCheckList[0][o];
                String promoValue = singleCheckList[1][o];
                String xpathPromo = Locators.VarProductPromoMain.getXpathVariable(promoName);

                //Цикл с алгоритмом проверки отдельной акции
                for (int i = 0; i < 2; i++) {

                    //Проверка акций
                    if (Objects.equals(promoValue, "*")) {
                        try {
                            WebElement promoElement = driver.findElement(By.xpath(xpathPromo));
                            resultCheckList[1][o] = "Passed";
                            break;

                        } catch (NoSuchElementException e) {
                            resultCheckList[1][o] = "Failed";
                            codeResult.put(prodCode, "Failed");
                        }

                    } else {
                        try {
                            WebElement promoElement = driver.findElement(By.xpath(xpathPromo));
                            resultCheckList[1][o] = "Failed";
                            codeResult.put(prodCode, "Failed");
                            break;

                        } catch (NoSuchElementException e) {
                            resultCheckList[1][o] = "Passed";
                        }
                    }

                    //Останавливаем запуск дополнительной проверки начиная со второй промо-акции
                    if (o > 1) {
                        break;
                    }

                    // Блок проверки загрузки страницы. Запускается однократно и только на первой скидке.
                    // Закрывает цикл, если нашел проверочный элемент (checkElement), а если нет, то обновляет страницу
                    // с увеличенным таймаутом и ищет его снова
                    // Если не нашел, то вписывает "Page404" в ячейку и завершает проверку для кода товара (не проверяет остальные акции)
                    // Если нашел, то цикл проверки скидки запускается снова
                    if (i == 0) {
                        try {
                            WebElement checkElement = driver.findElement(By.xpath(checkObjectXpath));
                            break;

                        } catch (NoSuchElementException e) {

                            try {
                                System.out.println("Start Refresh");
                                //Обновляем страницу с увеличенным pageLoadTimeout
                                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(SetDriverOld.getPageOut(driver) + checkLoadTime));
                                prodPage.refresh();

                                //Проверяем наличие проверочного объекта после рефреша
                                WebElement checkElement = driver.findElement(By.xpath(checkObjectXpath));

                                //Обновляем значение проверочной переменной если смогли нормально загрузить страницу
                                System.out.println("Slow Loading");
                                codeResult.put(prodCode, null);

                            } catch (NoSuchElementException | TimeoutException ex) {
                                System.out.println("Page 404");
                                resultCheckList[1][o] = "Page 404";
                                codeResult.put(prodCode, "Page 404");
                                throw new RuntimeException("Page 404");
                            }
                        }
                    }
                }
            }

            //Выводим входные данные и результат проверки в консоль
            System.out.println(Arrays.deepToString(singleCheckList));
            System.out.println(Arrays.deepToString(resultCheckList));

            //Заполняем переменную для RetryAnalyzer перед проведением финального Ассерта
            retryValue.put(prodCode, !Objects.equals(codeResult.get(prodCode), "Failed"));
            //Прикладываем подробные входные данные и результат проверки к тесту, а затем делаем финальный Assert
            Allure.addAttachment("Input", (Arrays.deepToString(singleCheckList[0])+ "\n" + Arrays.deepToString(singleCheckList[1])));
            Allure.addAttachment("Result", (Arrays.deepToString(resultCheckList[0])+ "\n" + Arrays.deepToString(resultCheckList[1])));
            Assert.assertNull(codeResult.get(prodCode));

        } finally {
            //Возвращаем драйвер в изначальном состоянии
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(startTimeout));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(startImplicit));
            BufferDriver.returnDriver(driver);
        }
    }

    @DataProvider (name = "UI_Vider", parallel = true)
    public Object[][] dataMethodUI() throws MyFileIOException, IOException, MyInputParamException {

        //Выбираем способ формирования данных в зависимости от того, запускались ли Api тесты и передавали ли они
        //какие-то данные
        if (PromCheckApiUiBuffer.getBufferCheckList() != null && PromCheckApiUiBuffer.getBufferCheckList().size() != 0) {

            //Забираем готовые чек-листы для каждого товара, после прохождения Api тестов
            List<String[][]> afterApiCheckList = PromCheckApiUiBuffer.getBufferCheckList();

            //Задаем размер для массива провайдера
            Object [][] dataObject = new Object[afterApiCheckList.size()][1];

            //Передаем в массив провайдера чек-листы обернутые в Фантик для корректного отображения параметра(код товара)
            //в отчете Allure
            for (int i = 0; i < afterApiCheckList.size(); i++) {
                dataObject [i][0] = new FanticProdCode(afterApiCheckList.get(i));
            }
            return dataObject;

        } else {

            //Забираем значение параметры из Сьюита
            String inpType = BufferSuiteVar.get("inputType");

            //Получаем чек-лист для дальнейшей проверки
            String [][] fullCheckList = InputType.toFinalArray(inpType);

            //Ряд с которого начинают перечисляться коды товаров в массиве
            int startRow = ConstInt.startRow.getValue();

            //Создаем список для помещения "обернутых" проверочных массивов
            Object [][] dataObject = new Object[fullCheckList.length-startRow][1] ;

            //Создаем чек-лист для одного товара
            for (int i = startRow; i < fullCheckList.length; i++) {

                //Создаем чек лист для товара
                String [][] singleCheckList = new String[2][fullCheckList[0].length];
                System.arraycopy(fullCheckList[0], 0, singleCheckList[0], 0, singleCheckList[0].length);
                System.arraycopy(fullCheckList[i], 0, singleCheckList[1], 0, singleCheckList[0].length);

                //Оборачиваем в Фантик для красивого отображения кода товара в отчете Allure
                FanticProdCode fanticProdCode = new FanticProdCode(singleCheckList);

                //Добавляем фантик в массив
                dataObject [i-startRow][0] = fanticProdCode;

            }
            return dataObject;
        }
    }


    //Закрытие браузера
    @AfterSuite(groups = "UI", alwaysRun = true)
    public void closeDrivers() {
        BufferDriver.closeAllDrivers();
    }



    //Методы интерфейсов
    @Override
    public Boolean getRetryVar(ITestResult iTestResult) {
        String prodCode = iTestResult.getParameters()[0].toString();
        return retryValue.get(prodCode);
    }
    @Override
    public String getTestId(ITestResult iTestResult) {
        return iTestResult.getParameters()[0].toString();
    }
    @Override
    public WebDriver getDriver(ITestResult iTestResult) {
        String prodCode = iTestResult.getParameters()[0].toString();
        return codeDriver.get(prodCode);
    }
    @Override
    public String getScreenNameVar(ITestResult iTestResult) {
        return iTestResult.getParameters()[0].toString();
    }

    @Override
    public String getCutXpath(ITestResult iTestResult) {
        return Locators.ProductPageProdContainer.getXpath();
    }

}
