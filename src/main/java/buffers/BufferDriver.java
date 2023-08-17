package buffers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import selectors.Browsers;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Буффер с очередями из драйверов
 * Помогает переиспользовать драйверы между тестами без закрытия, при одно- и многопоточном тестировании
 */
public class BufferDriver {
    private static Queue <WebDriver> chromeDriver = new LinkedList<>();

    private static Queue <WebDriver> firefoxDriver = new LinkedList<>();

    /**
     * Возвращает драйвер в соответствии с переданным имененем браузера
     * @param name - имя браузера
     */
    public synchronized static WebDriver getDriver(String name) {

        String broName = Browsers.selector(name);

        if (broName.contains("chrome")) {
            return getChrome();

        } else if (broName.contains("firefox")) {
            return getFirefox();

        } else {
            throw new RuntimeException("Ошибка при выборе браузера");
        }

    }

    /**
     * Помещает переданный WebDriver в буффер
     */
    public synchronized static void returnDriver (WebDriver driver) {
        if (driver instanceof ChromeDriver) {
            returnChrome(driver);

        } else if (driver instanceof FirefoxDriver) {
            returnFirefox(driver);

        }
    }

    /**
     * Закрывает все WebDriver в буффере и очищает его
     */
    public static void closeAllDrivers () {
        closeAllFirefox();
        closeAllChrome();
    }


    /**
     * Возвращает ChromeDriver из буффера.
     * Если буффер пуст, то создает новый ChromeDriver
     */
    public static WebDriver getChrome() {
        try {
            return chromeDriver.remove();
        } catch (NoSuchElementException e) {
            return new ChromeDriver();
        }
    }

    /**
     * Помещает переданный ChromeDriver в буффер
     */
    public static void returnChrome (WebDriver chrome) {
        chromeDriver.add(chrome);
    }

    /**
     * Закрывает все ChromeDriver в буффере и очищает его
     */
    public static void closeAllChrome () {
        while (chromeDriver.size() != 0) {
            chromeDriver.remove().close();
        }
    }

    /**
     * Возвращает ChromeDriver из буффера.
     * Если буффер пуст, то создает новый FirefoxDriver
     */
    public static WebDriver getFirefox() {
        try {
            return firefoxDriver.remove();
        } catch (NoSuchElementException e) {
            return new FirefoxDriver();
        }
    }

    /**
     * Помещает переданный FirefoxDriver в буффер
     */
    public static void returnFirefox (WebDriver firefox) {
        firefoxDriver.add(firefox);
    }

    /**
     * Закрывает все FirefoxDriver в буффере и очищает его
     */
    public static void closeAllFirefox () {
        while (firefoxDriver.size() != 0) {
            firefoxDriver.remove().close();
        }
    }


}
