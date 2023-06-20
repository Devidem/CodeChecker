package Selen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.Scanner;

// Представленные пробеги предназначены для демонстрации работы некоторых методов.
// Пробеги не оптимизированы и не являются лучшим вариантом применения метода - они просто дают возможность быстро увидеть результат работы.
public class DemoRuns {

    // 1. Демонстрация SelEx.xpathSelectByProperty.
    // Осуществляется выбор xpath из элементов входящих в блок "Популярные категории" по innerText: "Садовая техника"
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "./SelenDrivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.citilink.ru/");

        // xpath блока "Популярные категории"
        String xpath = "//*[@class=\"edhylph0 app-catalog-1ljlt6q e3tyxgd0\"]";

        String propertyName = "innerText";
        String propertyValue ="Садовая техника";

        SelEx selEx = new SelEx();
        selEx.setDriver(driver);
        xpath = selEx.xpathSelectByProperty(xpath, propertyName, propertyValue);

        WebElement element = driver.findElement(By.xpath(xpath));
        element.click();


        Scanner in = new Scanner(System.in);
        System.out.print("////Введите что-либо для закрытия браузера////");
        String num = in.nextLine();
        driver.close();

    }

}
