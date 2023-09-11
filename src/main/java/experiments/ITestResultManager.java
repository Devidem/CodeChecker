package experiments;

import buffers.BufferDriver;
import interfaces.Screenshootable;
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
     * Делает обрезанный по элементу скриншот в тестах с использованием {@link BufferDriver}
     */
    public static synchronized void addScreenShootOfElement(ITestResult iTestResult) {

        //Приводим iTestResult к Screenshootable
        Screenshootable screenshootable = (Screenshootable) iTestResult.getInstance();

        //Получаем данные из теста
        String prodCode = screenshootable.getScreenNameVar(iTestResult);
        WebDriver driver = screenshootable.getDriver(iTestResult);
        String cutXpath = screenshootable.getCutXpath(iTestResult);

        //Делаем ограниченный скриншот по элементу и готовим к преобразованию в byte массив
        AShot ashot = new AShot();
        ByteArrayOutputStream screenshoot = new ByteArrayOutputStream();
        WebElement cutElement = driver.findElement(By.xpath(cutXpath));
        try {
            ImageIO.write(ashot.coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, cutElement).getImage(), "png", screenshoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Добавляем скриншот
        Allure.getLifecycle().addAttachment("FailureScreen_"+ prodCode, "image/png", "png", screenshoot.toByteArray());

    }

    /**
     * Возвращает вебдрайвер в буфер {@link BufferDriver}
     */
    public static synchronized void returnBrowser (ITestResult iTestResult) {

        //Приводим iTestResult к Screenshootable
        Screenshootable screenshootable = (Screenshootable) iTestResult.getInstance();

        //Возвращаем драйвер
        BufferDriver.returnDriver(screenshootable.getDriver(iTestResult));
    }
}
