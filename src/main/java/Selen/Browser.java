package Selen;

import Selectors.Browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Browser {
    public static WebDriver go (String browser) {
        WebDriver driver = Browsers.selector(browser);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);


        return driver;
    }
}


