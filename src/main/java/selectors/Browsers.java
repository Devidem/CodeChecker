package selectors;

import exceptions.myExceptions.MyInputParamException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Управление браузером.
 */
public class Browsers {

    /**
     * Возвращает нужный вебдрайвер в зависимости от значения {@param browserName}.
     */
    public static WebDriver getDriver(String browserName) throws MyInputParamException {

        String result = select(browserName);

        if(result.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "./SelenDrivers/chromedriver.exe");
            return new ChromeDriver();
        } else if (result.contains("firefox")) {
            System.setProperty("webdriver.gecko.driver", "./SelenDrivers/geckodriver.exe");
            return new FirefoxDriver();
        }

        //Здесь мы не должны оказываться
        throw new RuntimeException();
    }

    public static String select(String input) throws MyInputParamException {
        input = input.toLowerCase();

        if (input.contains("chrome")) {
            return "chrome";

        } else if (input.contains("firefox")) {
            return "firefox";

        } else {
            throw new MyInputParamException("Неверный входной параметр \"browserName\" \nДоступные варианты - Сhrome/Firefox");

        }
    }
}
