package Selen;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SelEx {
    WebDriver driver;
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    // Клик и Гет, чтобы не ждать полной зарузки сайта, благодаря игнору TimeoutException
    public static void click (WebElement element) {
        try {
            element.click();
        } catch (TimeoutException ignored) {
        }
    }

    public void get (String link) {
        try {
            driver.get(link);
        } catch (TimeoutException ignored) {
        }
    }

    //Выбор xpath элемента с определенным значением property из тех, что входят в xpathContainer (первых чайлдов, без углубления).
    public String xpathSelectByProperty (String xpathContainer, String propertyName, String propertyValue) {

        String newXpath = xpathContainer;
        WebElement element;

        String findedValue = null;
        int elemNum = 1;

        while (!propertyValue.equals(findedValue)) {
            newXpath = xpathContainer + "/*" + "[" + elemNum + "]";
            elemNum++;

            try {
                element = driver.findElement(By.xpath(newXpath));
                findedValue = element.getAttribute(propertyName);

            // Отлавливаем отсутствие элемента c нужным проперти
            } catch (NoSuchElementException e) {
                System.out.println(xpathContainer + " - Element was not found!");
                return null;

            }



        }

        return newXpath;

    }

//    public void dynamicTimeout (WebDriver driver, String xpathChecker, int dynTimer) {
//
//
//        try {
//            WebElement element = driver.findElement(By.xpath(xpathChecker));
//
//        } catch (TimeoutException e) {
//            driver.manage().timeouts().implicitlyWait((dynTimer+2), TimeUnit.SECONDS);
//
//        }
//
//
//    }


}
