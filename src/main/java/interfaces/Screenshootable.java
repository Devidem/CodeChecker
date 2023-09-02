package interfaces;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import tests.citilink.finalTest.supportClasses.MyListenerPromChecking;

/**
 * Позволяет делать скриншоты с помощью {@link experiments.ITestResultManager} и
 * использовать класс в {@link MyListenerPromChecking}
 * благодаря получению вебдрайвера
 */
public interface Screenshootable {
    /**
     * Возвращает WebDriver теста
     */
    WebDriver getDriver(ITestResult iTestResult);

    /**
     * Возвращает из теста String переменную, которая добавляется к имени скриншота
     */
    String getScreenNameVar(ITestResult iTestResult);

    /**
     * Возвращает из теста Xpath элемента, по которому будет производиться обрезание скриншота
     */
    String getCutXpath(ITestResult iTestResult);
}
