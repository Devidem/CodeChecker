package experiments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Буффер с очередями из драйверов
 * Помогает переиспользовать драйверы между тестами без закрытия, при обычном и многопоточном тестировании
 */
public class BufferDriver {
    private static Queue <WebDriver> chromeDriver = new LinkedList<>();

    /**
     * Возвращает ChromeDriver из буффера.
     * Если буффер пуст, то создает новый ChromeDriver
     */
    public synchronized static WebDriver getChrome() {
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
    public static synchronized void closeAllChrome () {
        while (chromeDriver.size() != 0) {
            chromeDriver.remove().close();
        }
    }
}
