package selen;

import converters.ArrayEx;
import exceptions.myExceptions.MyFileIOException;
import selectors.Browsers;
import selectors.InputType;
import selectors.Sites;
import sites.citilink.ProdPage;
import sites.NoPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Набор тестов
 */
public class Test {
    /**
     * Проверяет наличие отображения акций на странице кода товара.
     * В качестве входных данных используются .xls файлы, расположенные в папке проекта ./Inputs/Excel
     * Результат проверки записывается в .xls файл по адресу ./Outputs/Excel - в имени указывается дата и результат.
     * @param browserName Название браузера - Chrome, Firefox(не реализовано)
     * @param siteName Название сайта - Citilink, Dns(не реализовано)
     * @param inputType Тип входных данных - Excel, Sql(не реализовано)
     */
    public void codeChecks (String browserName, String siteName, String inputType) throws MyFileIOException {

        //Получаем чеклист в зависимости от типа входных данных
        InputType inpType = new InputType(inputType);
        String[][] checkList = inpType.toFinalArray();

        //Получаем полную ссылку сайта в зависимости от ввода
        Sites sites = new Sites(siteName);
        sites.selector();
        siteName = sites.getResult();

        //Выбираем и запускаем браузер
        Browsers browsers = new Browsers(browserName);
        WebDriver driver = browsers.start();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        WebDriverWait wait;
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        //Идем на сайт, но с игнором TimeoutException
        NoPage noPage= new NoPage(driver);
        noPage.get(siteName);

        //Просто кнопочка для старта (Citilink ругался 429 ошибкой)
        Scanner in = new Scanner(System.in);
        System.out.print("////Старт////");
        String num = in.nextLine();

        //Создаем ProdPage, в котором есть все нужные методы для работы со страничкой продукта
        ProdPage prodPage = new ProdPage(driver, wait);

        //Создаем итоговый массив клонированием проверяемого и задаем строку первого кода товара
        String[][] resultList = ArrayEx.clone2d(checkList);
        int startRow = 2;


        //Проверяем чеклист
        for (int i = 0; i < resultList.length - startRow; i++) {
            int codeRow = i + startRow;

            //Берем код товара и идем на страничку
            String prodCode = checkList[codeRow][0];
            prodPage.enterSearch(prodCode);
            prodPage.clickSearchResult(prodCode);

            // Создаем лист с акциями для текущего кода
            String[][] promsList = new String[2][checkList[0].length - 1];
            System.arraycopy(resultList[0], 1, promsList[0], 0, promsList[0].length);
            System.arraycopy(resultList[codeRow], 1, promsList[1], 0, promsList[0].length);

            //Вписываем в массив resultList результаты проверки листа с акциями (проверку делает prodPage.checkProms)
            System.arraycopy(prodPage.checkProms(promsList), 0, resultList[codeRow], 1, promsList[0].length);

        }
        driver.close();

        //Создаем Эксельку с результатом
        ArrayEx.toExcelTest(resultList);

        //Вывод в консоль для просмотра результата - нужно только во время написания кода/проверки
        System.out.println(Arrays.deepToString(checkList));
        System.out.println(Arrays.deepToString(resultList));

    }
}
