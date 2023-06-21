package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class Pages {
    WebDriver driver;
    WebDriverWait wait;

    //Клик с игнором таймаута
    public void click (WebElement element) {
        try {
//            System.out.println("Click try");
            element.click();
        } catch (TimeoutException ignored) {
        }
    }

    //Клик лупы
    public void clickSearchButton () {
        try {
            String xpathSearchButton = "//*[@class=\"css-1d9cswg e15krpzo1\"]/*[@type=\"submit\"]";
            WebElement element = driver.findElement(By.xpath(xpathSearchButton));
            click(element);
        } catch (TimeoutException ignored) {
        }
    }

    //Клик по товару из результатов быстрого поиска
    public void clickSearchResult (String prodCode) {
        WebElement prodLink = driver.findElement(By.xpath("//*[contains(@href,\"" + prodCode + "\")]//*[@data-meta-name=\"InstantSearchMainResult\"]"));
//        System.out.println("Finded");
        click(prodLink);
    }

    //Клик по каталогу
    public void clickCatalog () {
            String xpathCatalog = "//*[@class=\"css-3nmxdw eyoh4ac0\"]/*[@href=\"/catalog/\"]";
            WebElement element = driver.findElement(By.xpath(xpathCatalog)); {
            }
            element.click();
    }

    //Ввод текста в строку поиска
    public void enterSearch (String text) {
        WebElement Search = driver.findElement(By.xpath("//input[@type=\"search\"]"));
        Search.sendKeys(text);
        wait.until(ExpectedConditions.invisibilityOfElementLocated (By.xpath("//*[contains(text(),'просмотренные')]")));
    }

    //Переход на страничку с игнором таймаута
    public void get (String link) {
        try {
            driver.get(link);
        } catch (TimeoutException ignored) {
        }
    }




    //Геттеры сеттеры
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
