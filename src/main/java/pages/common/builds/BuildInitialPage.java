package pages.common.builds;


import interfaces.Backable;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import pages.citilink.Navigator;

/**
 * Абстрактный класс с методами общими для абсолютно всех страниц
 */
public abstract class BuildInitialPage<T> implements Backable <Navigator> {

    protected WebDriver driver;
    protected Navigator navigator;

    public BuildInitialPage(WebDriver driver, Navigator navigator) {
        this.driver = driver;
        this.navigator = navigator;
    }


    /**
     * Клик с игнором TimeoutException.
     * @param xPath веб-элемента
     */
    public T click(String xPath) {
        try {
            driver.findElement(By.xpath(xPath)).click();
        } catch (TimeoutException ignored) {
        }
        return (T) this;
    }

    /**
     * Переход на страницу с игнором TimeoutException.
     * @param link Адрес сайта.
     */
    public T get(String link) {
        try {
            driver.get(link);
        } catch (TimeoutException ignored) {
        }
        return (T) this;
    }



    /**
     * Закрывает браузер
     */
    @Step ("Закрытие браузера")
    public T close () {
        driver.close();
        return (T) this;
    }

    /**
     * Обновляет страницу (F5)
     */
    public T refresh () {
        try {
            driver.navigate().refresh();
        } catch (TimeoutException ignored) {
        }
        return (T) this;
    }

    //Интерфейсы

    /**
     * Возвращает к {@link Navigator}
     * @return {@link Navigator}
     */
    @Override
    public Navigator and() {
        return navigator;
    }

    /**
     * Возвращает к {@link Navigator}
     * @return {@link Navigator}
     */
    @Override
    public Navigator then() {
        return navigator;
    }

    /**
     * Возвращает к {@link Navigator}
     * @return {@link Navigator}
     */
    @Override
    public Navigator also() {
        return navigator;
    }


    //Геттеры и Сеттеры
    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}













