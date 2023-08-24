package fabrics;

import org.openqa.selenium.WebDriver;

import java.time.Duration;

/**
 * Фабрика настройки WebDriver
 */
public class SetDriver {
    /**
     * Разворачивает окно и добавляет 3-х секундные implicitlyWait и pageLoadTimeout
     */
    public static void standard(WebDriver driver){
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));

    }

    /**
     * Разворачивает окно и позволяет определить вручную implicitlyWait и pageLoadTimeout
     */
    public synchronized static void standardManual(WebDriver driver, int implicitlyWait, int pageLoadTimeout){
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
    }

    public synchronized static int getPageOut(WebDriver driver){
        return (int) driver.manage().timeouts().getPageLoadTimeout().getSeconds();
    }

    public synchronized static int getImpOut(WebDriver driver){
        return (int) driver.manage().timeouts().getPageLoadTimeout().getSeconds();
    }

}
