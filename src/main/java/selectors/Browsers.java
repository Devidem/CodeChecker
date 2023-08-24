package selectors;

import converters.ExArray;
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
    public static WebDriver getDriver(String browserName) {

        String result = select(browserName);

        if(result.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "./SelenDrivers/chromedriver.exe");
            return new ChromeDriver();
        } else if (result.contains("firefox")) {
            System.setProperty("webdriver.gecko.driver", "./SelenDrivers/geckodriver.exe");
            return new FirefoxDriver();
        }

        throw new RuntimeException("Wrong Browser!");//Скорее всего забыли про select()!
    }

    public static String select(String input) {
        input = input.toLowerCase();
        String result;

        String[] broList = {"chrome", "firefox"};

        if (input.contains("chrome")) {
            result = "chrome";

        } else if (input.contains("firefox")) {
            result = "firefox";

        } else {
            //Предлагаем сделать выбор вручную
            System.out.println("Unknown Browser");
            result = ExArray.selector1D(broList);

        }
    return result;
    }

}
