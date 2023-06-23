package Selen;

import org.openqa.selenium.*;

/**
 * Класс с дополнительными методами, которые не явялются общими для классов Page типа.
 */
public class SelEx {
    WebDriver driver;

    public SelEx(WebDriver driver) {
        this.driver = driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Вычисляет Xpath локатор для нужного элемента из контейнера по значению Property (первых чайлдов, без углубления).
     * @param xpathContainer Xpath контейнера
     * @param propertyName Название Property
     * @param propertyValue Значение Property
     * @return Xpath найденного элемента
     */
    public String xpathSelectByProperty (String xpathContainer, String propertyName, String propertyValue) {

        String newXpath = xpathContainer;
        WebElement element;

        String findedValue = null;
        int elemNum = 1;

        //Перебираем элементы контейнера по номеру расположения и останавливаем цикл если нашли нужный
        while (!propertyValue.equals(findedValue)) {
            newXpath = xpathContainer + "/*" + "[" + elemNum + "]";
            elemNum++;

            try {
                element = driver.findElement(By.xpath(newXpath));
                findedValue = element.getAttribute(propertyName);

            // Отлавливаем отсутствие элемента c нужным проперти
            // Если после перебора всех элементов в контейнере, нужного не оказалось
            } catch (NoSuchElementException e) {
                System.out.println(xpathContainer + " - Element was not found!");
                return null;

            }

        }
        return newXpath;

    }

}
