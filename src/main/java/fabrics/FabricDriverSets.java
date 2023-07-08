package fabrics;

import org.openqa.selenium.WebDriver;

import java.time.Duration;

/**
 * Фабрика настройки WebDriver
 */
public class FabricDriverSets {
    /**
     * Разворачивает окно и добавляет 3-х секундные implicitlyWait и pageLoadTimeout
     */
    public static WebDriver standard(WebDriver driver){
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
        return driver;
    }

    /**
     * Разворачивает окно и позволяет определить вручную implicitlyWait и pageLoadTimeout
     */
    public static WebDriver standardManual(WebDriver driver, int implicitlyWait, int pageLoadTimeout){
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        return driver;
    }

}
