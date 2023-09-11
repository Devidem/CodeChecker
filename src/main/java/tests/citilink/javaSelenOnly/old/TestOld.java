package tests.citilink.javaSelenOnly.old;

import converters.ExArray;
import enums.ConstInt;
import enums.ConstString;
import exceptions.myExceptions.MyFileIOException;
import exceptions.myExceptions.MyInputParamException;
import fabrics.old.SetDriverOld;
import org.openqa.selenium.WebDriver;
import pages.citilink.old.NoPageOld;
import pages.citilink.old.ProdPageOld;
import selectors.Browsers;
import selectors.InputType;
import tests.citilink.javaSelenOnly.old.multithread.MultiPromCheck;

import java.io.IOException;
import java.util.Arrays;

/**
 * Набор тестов
 */
public class TestOld {
    /**
     * Проверяет наличие отображения акций на странице кода товара (однопоточный вариант).
     * В качестве входных данных используется .xls файл, расположенные в папке проекта ./Inputs/Excel или PostgreSql.
     * Результат проверки записывается в .xls файл по адресу ./Outputs/Excel - в имени указывается дата и результат.
     * @param browserName Название браузера - Chrome, Firefox
     * @param inputType Тип входных данных - Excel, Sql
     */
    public void codeChecks (String browserName, String inputType) throws MyFileIOException, MyInputParamException, IOException {

        //Получение чек-листа для дальнейшей проверки
        String[][] checkList = InputType.toFinalArray(inputType);

        //Выбор браузера и его запуск + настройка
        WebDriver driver = Browsers.getDriver(browserName);
        SetDriverOld.standard(driver);

        //Адрес сайта
        String siteLink = ConstString.CitilinkAdress.getValue();

        //Переход на сайт
        NoPageOld noPage= new NoPageOld(driver);
        noPage.get(siteLink);

        //Создание массива для результатов проверки клонированием проверяемого
        String[][] resultList = ExArray.clone2d(checkList);
        //Номер строки, с которой начинается перечисление кодов в Excel
        int startRow = ConstInt.startRow.getValue();

        //Создание ProdPage, чтобы не объявлять каждый раз в цикле
        ProdPageOld prodPage = new ProdPageOld(driver);

        //Проверка каждого товара
        for (int i = 0; i < resultList.length - startRow; i++) {
            int codeRow = i + startRow;

            // Переход на страницу товара
            // Не создается отдельный MainPage для открытия страницы первого товара, поскольку это усложнит код,
            // а все нужные методы уже есть в ProdPage
            String prodCode = checkList[codeRow][0];
            prodPage.enterSearch(prodCode);
            prodPage.clickSearchResult(prodCode);

            // Создание листа с акциями для текущего кода
            String[][] promsList = new String[2][checkList[0].length - 1];
            System.arraycopy(resultList[0], 1, promsList[0], 0, promsList[0].length);
            System.arraycopy(resultList[codeRow], 1, promsList[1], 0, promsList[0].length);

            // Вписывание результатов проверки листа с акциями в resultList (проверку делает prodPage.checkProms)
            System.arraycopy(prodPage.checkProms(promsList), 0, resultList[codeRow], 1, promsList[0].length);

        }
        driver.close();

        //Создание Excel с результатом
        ExArray.toExcelTest(resultList);

        //Вывод в консоль для просмотра результата - нужно только во время написания кода/проверки
        System.out.println(Arrays.deepToString(checkList));
        System.out.println(Arrays.deepToString(resultList));

    }

    /**
     * Проверяет наличие отображения акций на странице кода товара (многопоточный вариант).
     * В качестве входных данных используется .xls файл, расположенные в папке проекта ./Inputs/Excel или PostgreSql.
     * Результат проверки записывается в .xls файл по адресу ./Outputs/Excel - в имени указывается дата и результат.
     * @param browserName Название браузера - Chrome, Firefox
     * @param inputType Тип входных данных - Excel, Sql
     * @param threadsNumber Количество потоков
     */
    public void codeChecks (String browserName,String inputType, int threadsNumber) throws MyFileIOException, IOException, MyInputParamException {

        //Получение чек-листа для дальнейшей проверки
        String[][] checkList = InputType.toFinalArray(inputType);

        //Адрес сайта
        String siteLink = ConstString.CitilinkAdress.getValue();

        //Создание массива для результатов проверки клонированием проверяемого
        String[][] resultList = ExArray.clone2d(checkList);
        int startRow = ConstInt.startRow.getValue();

        //Многопоточная проверка промо-акций
        MultiPromCheck multiPromCheck = new MultiPromCheck(browserName, siteLink, checkList, resultList, startRow, threadsNumber);
        multiPromCheck.multiCheck();

        //Создание Excel с результатами
        ExArray.toExcelTest(resultList);

        //Вывод в консоль для просмотра результата - нужно только во время написания кода/проверки
        System.out.println(Arrays.deepToString(checkList));
        System.out.println(Arrays.deepToString(resultList));

    }
}
