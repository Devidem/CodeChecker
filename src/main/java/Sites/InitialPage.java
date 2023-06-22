package Sites;



import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class InitialPage {

    private WebDriver driver;

    public InitialPage(WebDriver driver) {
        this.driver = driver;
    }

    //Клик с игнором таймаута
    public void click(WebElement element) {
        try {
//            System.out.println("Click try");
            element.click();
        } catch (TimeoutException ignored) {
        }
    }

    //Переход на страничку с игнором таймаута
    public void get(String link) {
        try {
            driver.get(link);
        } catch (TimeoutException ignored) {
        }
    }






    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}













