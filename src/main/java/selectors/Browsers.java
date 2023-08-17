package selectors;

import converters.ExArray;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Управление браузером.
 */
public class Browsers extends Selectors {

    public Browsers(String input) {
        super(input);
    }

    /**
     * Запускает нужный браузер в зависимости от значения {@link #result}.
     */
    public WebDriver start() {

        selector();

        if(result.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "./SelenDrivers/chromedriver.exe");
            return new ChromeDriver();
        } else if (result.contains("firefox")) {
            System.setProperty("webdriver.gecko.driver", "./SelenDrivers/geckodriver.exe");
            return new FirefoxDriver();
        }


        System.out.println("Wrong Browser!"); //Скорее всего забыли про selector()!
        return null;
    }

    /**
     * Выбирает браузер из массива {@link #input} и передает значение в {@link #result}.
     */
    public void selector() {
        input = input.toLowerCase();

        String[] broList = {"chrome", "firefox"};

        if (input.contains("chrome")) {
            result = "chrome";

        } else if (input.contains("firefox")) {
            result = "firefox";

        } else {
            System.out.println("Unknown Browser");
            result = ExArray.selector1D(broList);

        }

    }
    public static String selector(String input) {
        input = input.toLowerCase();
        String result;

        String[] broList = {"chrome", "firefox"};

        if (input.contains("chrome")) {
            result = "chrome";

        } else if (input.contains("firefox")) {
            result = "firefox";

        } else {
            System.out.println("Unknown Browser");
            result = ExArray.selector1D(broList);

        }
    return result;
    }

}
