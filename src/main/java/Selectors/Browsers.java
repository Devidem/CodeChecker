package Selectors;

import Converts.Array;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class Browsers {
    public static WebDriver selector(String browser) throws IOException {
        browser = browser.toLowerCase();

        String [] broList = {"chrome", "firefox(для примера)"};

        if(browser.contains("chrome")) {
            browser = "chrome";
        } else {
            browser = Array.picker(broList);
        }

        if(browser.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "./SelenDrivers/chromedriver.exe");
            return new ChromeDriver();
        }

        System.out.println("Unknown Browser");
        return null;
    }
}
