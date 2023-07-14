package interfaces;

import org.openqa.selenium.WebDriver;

/**
 * Позволяет использовать методы {@link experiments.ITestResultManager}
 */
public interface Screenshootable {
    /**
     * Возвращает WebDriver теста
     */
    public WebDriver getDriver();

    /**
     * Возвращает значение переменной присвояемой имени скришота в Allure отчете
     */
    public String getScreenVariable();
}
