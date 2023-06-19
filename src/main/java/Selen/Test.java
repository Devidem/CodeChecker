package Selen;

import Converts.Array;
import Selectors.Browsers;
import Selectors.InputType;
import Selectors.Sites;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Test {
    public void codeChecks (String browser_name, String site_name, String input_type) throws IOException {

        InputType inputType = new InputType();
        inputType.setInput(input_type);
        inputType.selector();
        String[][] Codes = inputType.toFinalArray();

        Sites sites = new Sites();
        sites.setInput(site_name);
        sites.selector();
        site_name = sites.getResult();

        Browsers browsers = new Browsers();
        browsers.setInput(browser_name);
        browsers.selector();
        WebDriver driver = browsers.start();


        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        driver.get(site_name);

        WebDriverWait wait;
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));


        int startrow = 2;
        int startcell = 1;
        int arrow = 0;

        String[][] Result = Array.clone2d(Codes);

        PrPage prPage = new PrPage();
        prPage.setDriver(driver);
        prPage.setWait(wait);

        for (int i = 0; i < Result.length - startrow; i++) {
            arrow = i + startrow;

            String prodcod = Codes[arrow][0];

            prPage.setProdCod(prodcod);
            prPage.toProdPage();

            String[][] codeProms = new String[2][Codes[0].length - startcell];
            System.arraycopy(Result[0], 1, codeProms[0], 0, codeProms[0].length);
            System.arraycopy(Result[arrow], 1, codeProms[1], 0, codeProms[0].length);

            System.arraycopy(prPage.checkProms(codeProms), 0, Result[arrow], 1, codeProms[0].length);

        }

        System.out.println(Arrays.deepToString(Codes));
        System.out.println(Arrays.deepToString(Result));

        driver.close();

        Array.toExcelTest(Result);
    }
}
