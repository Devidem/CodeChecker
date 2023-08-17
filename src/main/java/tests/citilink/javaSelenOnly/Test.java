package tests.citilink.javaSelenOnly;

import converters.ExArray;
import enums.ConstInt;
import enums.ConstString;
import exceptions.myExceptions.MyFileIOException;
import fabrics.SetDriver;
import org.openqa.selenium.WebDriver;
import pages.NoPage;
import pages.citilink.ProdPage;
import selectors.Browsers;
import selectors.InputType;
import tests.citilink.javaSelenOnly.multithread.MultiPromCheck;

import java.util.Arrays;

/**
 * Набор тестов
 */
public class Test {
    /**
     * Проверяет наличие отображения акций на странице кода товара.
     * В качестве входных данных используются .xls файлы, расположенные в папке проекта ./Inputs/Excel
     * Результат проверки записывается в .xls файл по адресу ./Outputs/Excel - в имени указывается дата и результат.
     * @param browserName Название браузера - Chrome, Firefox(не реализовано)
     * @param inputType Тип входных данных - Excel, Sql(не реализовано)
     */
    public void codeChecks (String browserName, String inputType) throws MyFileIOException {

        //Получение чеклиста для дальнейшей проверки
        InputType inpType = new InputType(inputType);
        String[][] checkList = inpType.toFinalArray();

        //Выбор браузера и его запуск + настройка
        Browsers browsers = new Browsers(browserName);
        WebDriver driver = browsers.start();
        SetDriver.standard(driver);

        //Адрес сайта
        String siteLink = ConstString.CitilinkAdress.getValue();

        //Переход на сайт
        NoPage noPage= new NoPage(driver);
        noPage.get(siteLink);

        //Создание массива для результатов проверки клонированием проверяемого
        String[][] resultList = ExArray.clone2d(checkList);
        //Номер строки, с которой начинается перечисление кодов в Excel
        int startRow = ConstInt.startRow.getValue();

        //Создание ProdPage, чтобы не объявлять каждый раз в цикле
        ProdPage prodPage = new ProdPage(driver);

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
     * В качестве входных данных используются .xls файлы, расположенные в папке проекта ./Inputs/Excel
     * Результат проверки записывается в .xls файл по адресу ./Outputs/Excel - в имени указывается дата и результат.
     * @param browserName Название браузера - Chrome, Firefox(не реализовано)
     * @param inputType Тип входных данных - Excel, Sql(не реализовано)
     * @param threadsNumber Количество потоков
     */
    public void codeChecks (String browserName,String inputType, int threadsNumber) throws MyFileIOException {

        //Получение чеклиста для дальнейшей проверки
        InputType inpType = new InputType(inputType);
        String[][] checkList = inpType.toFinalArray();

        //Адрес сайта
        String siteLink = ConstString.CitilinkAdress.getValue();

        //Создание массива для результатов проверки клонированием проверяемого
        String[][] resultList = ExArray.clone2d(checkList);
        int startRow = ConstInt.startRow.getValue();

        //Многопоточная проверка промоакций
        MultiPromCheck multiPromCheck = new MultiPromCheck(browserName, siteLink, checkList, resultList, startRow, threadsNumber);
        multiPromCheck.multiCheck();

        //Создание Excel с результатами
        ExArray.toExcelTest(resultList);

        //Вывод в консоль для просмотра результата - нужно только во время написания кода/проверки
        System.out.println(Arrays.deepToString(checkList));
        System.out.println(Arrays.deepToString(resultList));

    }
}
