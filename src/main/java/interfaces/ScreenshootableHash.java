package interfaces;

import org.openqa.selenium.WebDriver;

/**
 * Позволяет использовать методы {@link experiments.ITestResultManager} в тестах
 * с использванием {@link experiments.BufferDriver}
 */
public interface ScreenshootableHash {

    /**
     * Возвращает WebDriver теста
     */
    WebDriver getDriver(String hashKey);

}
