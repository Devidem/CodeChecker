package Selen;

import org.openqa.selenium.*;


public class SelEx {
    WebDriver driver;

    public SelEx(WebDriver driver) {
        this.driver = driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    // Клик с игнором таймаута
    public static void click (WebElement element) {
        try {
            element.click();
        } catch (TimeoutException ignored) {
        }
    }

    //Переход на страничку с игнором таймаута
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

}
