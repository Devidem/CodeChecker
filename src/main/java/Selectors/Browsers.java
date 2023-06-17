package Selectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browsers {
    public static WebDriver selector(String browser) {
        browser = browser.toLowerCase();

        if(browser.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "./SelenDrivers/chromedriver.exe");
            WebDriver driver = new ChromeDriver();
            return driver;
        }

        System.out.println("Unknown Browser");
        return null;
    }
}
