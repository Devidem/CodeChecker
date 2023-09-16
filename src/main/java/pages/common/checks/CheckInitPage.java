package pages.common.checks;

import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Набор проверок для любой страницы
 * @param <T> Класс наследующийся от {@link CheckInitPage}
 */
public class CheckInitPage<T> {

    protected WebDriver driver;
    public CheckInitPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Проверка текущего URL на соответствие {@param link}
     * @param resultBoolean Мапа для хранения результата проверки - {@param link} используется в качестве ключа
     */
    public T currentUrl(String link, Map <String, Boolean> resultBoolean) {
        System.out.println(driver.getCurrentUrl());
        System.out.println();
        resultBoolean.put(link, driver.getCurrentUrl().contains(link));
        return (T) this;
    }


    //Геттеры и сеттеры
    public WebDriver getDriver() {
        return driver;
    }
}
