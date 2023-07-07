package threads;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.NoPage;
import pages.citilink.ProdPage;
import selectors.Browsers;
import selectors.Sites;

import java.time.Duration;

public class ChecksRunner extends Thread {

    String browserName;
    String siteName;

    @Override
    public void run() {

        //Выбираем и запускаем браузер
        Browsers browsers = new Browsers(browserName);
        WebDriver driver = browsers.start();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));

        WebDriverWait wait;
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        //Идем на сайт, но с игнором TimeoutException
        NoPage noPage = new NoPage(driver);
        noPage.get(siteName);

        //Создаем ProdPage, в котором есть все нужные методы для работы со страничкой продукта
        ProdPage prodPage = new ProdPage(driver, wait);


        prodPage.enterSearch(prodCode);
        prodPage.clickSearchResult(prodCode);

        // Создаем лист с акциями для текущего кода
        String[][] promsList = new String[2][checkList[0].length - 1];
        System.arraycopy(resultList[0], 1, promsList[0], 0, promsList[0].length);
        System.arraycopy(resultList[codeRow], 1, promsList[1], 0, promsList[0].length);

        //Вписываем в массив resultList результаты проверки листа с акциями (проверку делает prodPage.checkProms)
        System.arraycopy(prodPage.checkProms(promsList), 0, resultList[codeRow], 1, promsList[0].length);

    }
}
