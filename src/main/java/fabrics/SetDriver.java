package fabrics;

import interfaces.Backable;
import org.openqa.selenium.WebDriver;
import pages.citilink.Navigator;

import java.time.Duration;

/**
 * Фабрика настройки WebDriver
 */
public class SetDriver implements Backable<Navigator> {
    private WebDriver driver;
    private Navigator navigator;

    public SetDriver(WebDriver driver, Navigator navigator) {
        this.driver = driver;
        this.navigator = navigator;
    }

    /**
     * Разворачивает окно и добавляет 3-х секундные implicitlyWait и pageLoadTimeout
     */
    public SetDriver standard(){
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
        return this;
    }

    /**
     * Разворачивает окно и позволяет определить вручную implicitlyWait и pageLoadTimeout
     */
    public synchronized SetDriver standardManual(int implicitlyWait, int pageLoadTimeout){
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        return this;
    }

    /**
     * Возвращает текущее значение PageLoadTimeout
     */
    public synchronized static int getPageOut(WebDriver driver){
        return (int) driver.manage().timeouts().getPageLoadTimeout().getSeconds();
    }

    /**
     * Возвращает текущее значение ImplicitWaitTimeout
     */
    public synchronized static int getImpOut(WebDriver driver){
        return (int) driver.manage().timeouts().getImplicitWaitTimeout().getSeconds();
    }




    // Методы Backable<Navigator>
    /**
     * Возврат к {@link #navigator}
     */
    @Override
    public Navigator and() {
        return navigator;
    }

    /**
     * Возврат к {@link #navigator}
     */
    @Override
    public Navigator then() {
        return navigator;
    }

    /**
     * Возврат к {@link #navigator}
     */
    @Override
    public Navigator also() {
        return navigator;
    }
}
