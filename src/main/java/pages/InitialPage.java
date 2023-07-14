package pages;


import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Класс с общими методами для абсолютно всех страниц
 */
public abstract class InitialPage {

    private WebDriver driver;

    public InitialPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Кликает с игнором TimeoutException.
     * @param element Веб-элемент.
     */
    public void click(WebElement element) {
        try {
            element.click();
        } catch (TimeoutException ignored) {
        }
    }

    /**
     * Делает переход на страницу с игнором TimeoutException.
     * @param link Адрес сайта.
     */
    public void get(String link) {
        try {
            driver.get(link);
        } catch (TimeoutException ignored) {
        }
    }

    /**
     * Закрывает браузер
     */
    @Step ("Закрытие браузера")
    public void close () {
        driver.close();
    }



    //Геттеры и Сеттеры
    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}













