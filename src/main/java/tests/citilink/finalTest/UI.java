package tests.citilink.finalTest;

import buffers.BufferDriver;
import buffers.BufferSuiteVar;
import buffers.PromCheckApiUiBuffer;
import converters.ExArray;
import enums.ConstInt;
import enums.ConstString;
import enums.Locators;
import exceptions.myExceptions.MyFileIOException;
import exceptions.myExceptions.MyInputParamException;
import experiments.FanticAllure;
import interfaces.Retryable;
import interfaces.Screenshootable;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.citilink.Navigator;
import selectors.InputType;
import tests.citilink.finalTest.supportClasses.MyListenerPromChecking;
import tests.citilink.finalTest.supportClasses.MyRetryAnalyzerPromChecking;

import java.io.IOException;
import java.util.*;

@Listeners(MyListenerPromChecking.class)
public class UI implements Screenshootable, Retryable {

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
    public void promCheckUI(FanticAllure<String[][]> fantic) {

        //Разворачивается фантик и записываем код товара
        String[][] singleCheckList = fantic.getObject();
        String prodCode = singleCheckList[1][0];

        //Устанавливаем начальные значения для финального ассерта и запуска RetryAnalyzer
        retryValue.put(prodCode, false);
        codeResult.put(prodCode, null);

        //Коллекция для хранения результата проверки
        Queue <String[][]> testResult = new LinkedList<>();

        //Класс навигации
        Navigator navigate = new Navigator();

//        //Используем Api для получения ссылки страницы товара
//        String productLink = ApiRequests.getProdLink(prodCode);

        try {
            navigate
                    .openBrowserOrDefault(BufferSuiteVar.get("browserName"), "Chrome")
                    .configBrowser().standard()
                    .then()
                    .onNoPage()
                    .getIfNoContains(ConstString.CitilinkAdress.getValue())
                    .then()
                    .onProdPage()
                    .inMainPanel()
                    .enterSearch(prodCode)
                    .clickSearchResult(prodCode)
                    .then()
                    .check().promoActions(singleCheckList, testResult);
        } finally {
            codeDriver.put(prodCode, navigate.onNoPage().getDriver());
        }

        //Проверяем результат проверки на ошибки
        String[][] checkResult = testResult.remove();
        for (int i = 1; i < checkResult[1].length; i++) {
            //Ошибки требующие перезапуска теста
            if (!(checkResult[1][i].toLowerCase().contains("failed") | checkResult[1][i].toLowerCase().contains("passed"))){
                retryValue.put(prodCode, true);
                codeResult.put(prodCode, "NotNull!!!");
                break;
            //Ошибки не требующие перезапуска теста
            } else if (checkResult[1][i].toLowerCase().contains("failed")) {
                codeResult.put(prodCode, "NotNull!!!");
            }
        }

        //Прикладываем подробные входные данные и результат проверки к тесту, а затем делаем финальный Assert
        Allure.addAttachment("Input", (Arrays.deepToString(singleCheckList[0])+ "\n" + Arrays.deepToString(singleCheckList[1])));
        Allure.addAttachment("Result", (Arrays.deepToString(checkResult[0])+ "\n" + Arrays.deepToString(checkResult[1])));
        Assert.assertNull(codeResult.get(prodCode));
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
                dataObject [i][0] = new FanticAllure<>(afterApiCheckList.get(i), x -> x[1][0]);
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

            //Разбиваем чек-лист на одиночные (для каждого кода товара свой)
            Queue <String [][]> resultSep = new LinkedList<>();
            ExArray.separateTableQueue(fullCheckList, resultSep);

            //Добавляем "обернутые" чек-листы в массив DataProvider
            for (int i = 0; resultSep.size()!=0; i++) {
                dataObject [i][0] = new FanticAllure<>(resultSep.remove(), x -> x[1][0]);
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
