package Selen;

import Converts.ArrayEx;
import Pages.ProdPage;
import Selectors.Browsers;
import Selectors.InputType;
import Selectors.Sites;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Test {
    public void codeChecks (String browser_name, String site_name, String input_type) throws IOException {

        //Получаем чеклист в зависимости от типа входных данных
        InputType inputType = new InputType();
        inputType.setInput(input_type);
        inputType.selector();
        String[][] checkList = inputType.toFinalArray();

        //Получаем полную ссылку сайта в зависимости от ввода
        Sites sites = new Sites();
        sites.setInput(site_name);
        sites.selector();
        site_name = sites.getResult();

        //Выбираем и запускаем браузер
        Browsers browsers = new Browsers();
        browsers.setInput(browser_name);
        browsers.selector();
        WebDriver driver = browsers.start();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        WebDriverWait wait;
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        //Идем на сайт, но с игнором таймаутов
        SelEx selEx = new SelEx();
        selEx.setDriver(driver);
        selEx.get(site_name);

        Scanner in = new Scanner(System.in);
        System.out.print("////Старт////");
        String num = in.nextLine();

        //Создаем ProdPage, в котором есть все нужные методы для работы со страничкой продукта
        ProdPage prodPage = new ProdPage();
        prodPage.setDriver(driver);
        prodPage.setWait(wait);

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

        System.out.println(Arrays.deepToString(checkList));
        System.out.println(Arrays.deepToString(resultList));

    }
}
