package pages.citilink;

import buffers.BufferDriver;
import exceptions.myExceptions.MyInputParamException;
import fabrics.SetDriver;
import org.openqa.selenium.WebDriver;
import pages.citilink.naviClasses.MainPage;
import pages.citilink.naviClasses.ProdPage;
import pages.common.naviClasses.NoPage;

/**
 * С помощью данного класса осуществляется начало написания шагов навигации.
 * Методы с именами страниц возвращают их классы, в которых реализован "Fluent interface".
 * В классах страниц можно вызвать метод goTo, который возвращает Navigator.
 */
public class Navigator {
    private WebDriver driver;

    public Navigator() {
    }
    public Navigator(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * @return Страница товара
     */
    public ProdPage onProdPage() {
        return new ProdPage(driver, this);
    }
    /**
     * @return Главная страница
     */
    public MainPage onMainPage() {
        return new MainPage(driver, this);
    }
    /**
     * @return Неизвестная страница
     */
    public NoPage onNoPage() {
        return new NoPage(driver, this);
    }


    /**
     * Запускает нужный вебдрайвер и запоминает его в {@link #driver}
     */
    public Navigator openBrowser (String browserName) throws MyInputParamException {
        driver = BufferDriver.getDriver(browserName);
        return this;
    }

    /**
     * Запускает нужный вебдрайвер и запоминает его в {@link #driver}
     */
    public Navigator openBrowserOrDefault (String browserName, String defaultBrowserName) {
        try {
            driver = BufferDriver.getDriver(browserName);
        } catch (MyInputParamException e) {
            try {
                driver = BufferDriver.getDriver(defaultBrowserName);
            } catch (MyInputParamException ex) {
                throw new RuntimeException(ex);
            }
        }
        return this;
    }

    /**
     * Помещает текущий вебдрайвер в {@link BufferDriver}
     */
    public Navigator returnBrowser () {
        BufferDriver.returnDriver(driver);
        driver = null;
        return this;
    }

    /**
     * Закрывает текущий вебдрайвер {@link #driver}
     */
    public Navigator closeBrowser () {
        driver.close();
        return this;
    }

    /**
     * Возвращает {@link SetDriver} для настройки текущего вебдрайвера {@link #driver}
     */
    public SetDriver configBrowser () {
        return new SetDriver(driver, this);
    }

}
