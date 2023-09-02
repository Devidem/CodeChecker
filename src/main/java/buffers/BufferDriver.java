package buffers;

import exceptions.myExceptions.MyInputParamException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Буфер с очередями из драйверов.
 * Помогает переиспользовать драйверы между тестами без закрытия, при одно- и многопоточном тестировании.
 */
public class BufferDriver {
    private static final Queue <WebDriver> chromeDriver = new LinkedList<>();

    private static final Queue <WebDriver> firefoxDriver = new LinkedList<>();

    /**
     * Возвращает драйвер в соответствии с переданным имененем браузера
     * @param name - имя браузера
     */
    public synchronized static WebDriver getDriver(String name) throws MyInputParamException {

        String broName = name.toLowerCase();

        if (broName.contains("chrome")) {
            return getChrome();

        } else if (broName.contains("firefox")) {
            return getFirefox();

        } else {
            throw new MyInputParamException("Неверный входной параметр \"browserName\" \nДоступные варианты - Сhrome/Firefox");
        }

    }

    /**
     * Помещает переданный WebDriver в буфер
     */
    public synchronized static void returnDriver (WebDriver driver) {
        if (driver instanceof ChromeDriver) {
            returnChrome(driver);

        } else if (driver instanceof FirefoxDriver) {
            returnFirefox(driver);

        }
    }

    /**
     * Закрывает все WebDriver в буфере и очищает его
     */
    public static void closeAllDrivers () {
        closeAllFirefox();
        closeAllChrome();
    }


    /**
     * Возвращает ChromeDriver из буфера.
     * Если буфер пуст, то создает новый ChromeDriver
     */
    public static WebDriver getChrome() {
        try {
            return chromeDriver.remove();
        } catch (NoSuchElementException e) {
            return new ChromeDriver();
        }
    }

    /**
     * Помещает переданный ChromeDriver в буфер
     */
    public static synchronized void returnChrome (WebDriver chrome) {
        chromeDriver.add(chrome);
    }

    /**
     * Закрывает все ChromeDriver в буфере и очищает его
     */
    public static void closeAllChrome () {
        while (chromeDriver.size() != 0) {
            chromeDriver.remove().close();
        }
    }

    /**
     * Возвращает ChromeDriver из буфера.
     * Если буфер пуст, то создает новый FirefoxDriver
     */
    public static WebDriver getFirefox() {
        try {
            return firefoxDriver.remove();
        } catch (NoSuchElementException e) {
            return new FirefoxDriver();
        }
    }

    /**
     * Помещает переданный FirefoxDriver в буфер
     */
    public static synchronized void returnFirefox (WebDriver firefox) {
        firefoxDriver.add(firefox);
    }

    /**
     * Закрывает все FirefoxDriver в буфере и очищает его
     */
    public static void closeAllFirefox () {
        while (firefoxDriver.size() != 0) {
            firefoxDriver.remove().close();
        }
    }


}
