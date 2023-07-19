package experiments;

import interfaces.Screenshootable;
import interfaces.ScreenshootableHash;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Набор методов для обработки ITestResult
 */
public class ITestResultManager {
    /**
     * Делает обрезанный по элементу скриншот
     */
    public static synchronized void addScreenShootOfElement(ITestResult iTestResult, String xpath) {

        //Получаем значение result из теста
        Object instance = iTestResult.getInstance();
        WebDriver driver = ((Screenshootable) instance).getDriver();

        //Делаем ограниченный скриншот по элементу и готовим к преобразованию в byte массив
        AShot ashot = new AShot();
        ByteArrayOutputStream screen = new ByteArrayOutputStream();
        WebElement element = driver.findElement(By.xpath(xpath));
        try {
            ImageIO.write(ashot.coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, element).getImage(), "png", screen);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Добавляем скриншот
        Allure.getLifecycle().addAttachment("FailureScreen_"+((Screenshootable) instance).getScreenVariable(), "image/png", "png", screen.toByteArray());

    }

    /**
     * Делает обрезанный по элементу скриншот в тестах с использованием {@link BufferDriver}
     */
    public static synchronized void addScreenShootOfElementHash(ITestResult iTestResult, String xpath) {

        //Получаем объект класса теста
        Object instance = iTestResult.getInstance();

        //По коду товара получаем драйвер из хеш-мапы
        String prodCode = iTestResult.getParameters()[0].toString();
        WebDriver driver = ((ScreenshootableHash) instance).getDriver(prodCode);

        //Делаем ограниченный скриншот по элементу и готовим к преобразованию в byte массив
        AShot ashot = new AShot();
        ByteArrayOutputStream screen = new ByteArrayOutputStream();
        WebElement element = driver.findElement(By.xpath(xpath));
        try {
            ImageIO.write(ashot.coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, element).getImage(), "png", screen);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Добавляем скриншот
        Allure.getLifecycle().addAttachment("FailureScreen_"+ prodCode, "image/png", "png", screen.toByteArray());

    }
}
