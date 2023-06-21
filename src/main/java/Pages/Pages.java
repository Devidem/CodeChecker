package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Pages {
    WebDriver driver;
    WebDriverWait wait;

    public void click (WebElement element) {
        try {
            element.click();
        } catch (TimeoutException ignored) {
        }
    }
    public void catalogClick () {
        try {
            String xpathCatalog = "//*[@class=\"css-3nmxdw eyoh4ac0\"]/*[@href=\"/catalog/\"]";
            WebElement element = driver.findElement(By.xpath(xpathCatalog)); {
            }
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

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }

    public void setMulti(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
}
