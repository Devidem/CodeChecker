package Sites.Citilink;

import Sites.InitialPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Класс с общими методами для страниц сайта Citilink
 */
public abstract class CitiPage extends InitialPage {

    private WebDriver driverPages = getDriver();
    private WebDriverWait wait;

    public CitiPage(WebDriver driverStart, WebDriverWait wait) {
        super(driverStart);
        this.wait = wait;
    }

    /**
     * Кликает по лупе.
     */
    public void clickSearchButton () {
        try {
            String xpathSearchButton = "//*[@class=\"css-1d9cswg e15krpzo1\"]/*[@type=\"submit\"]";
            WebElement element = driverPages.findElement(By.xpath(xpathSearchButton));
            click(element);
        } catch (TimeoutException ignored) {
        }
    }


    /**
     * Кликает по товару из результатов быстрого поиска.
     * @param prodCode Код товара.
     */
    public void clickSearchResult (String prodCode) {
        WebElement prodLink = driverPages.findElement(By.xpath("//*[contains(@href,\"" + prodCode + "\")]//*[@data-meta-name=\"InstantSearchMainResult\"]"));
//        System.out.println("Finded");
        click(prodLink);
    }

    /**
     * Кликает по кнопке каталога, находящейся слева от поиска.
     */
    public void clickCatalog () {
            String xpathCatalog = "//*[@class=\"css-3nmxdw eyoh4ac0\"]/*[@href=\"/catalog/\"]";
            WebElement element = driverPages.findElement(By.xpath(xpathCatalog)); {
            }
            element.click();
    }

    /**
     * Вводит текста в строку поиска.
     * @param text Текст запроса.
     */
    public void enterSearch (String text) {
        WebElement Search = driverPages.findElement(By.xpath("//input[@type=\"search\"]"));
        Search.sendKeys(text);
        wait.until(ExpectedConditions.invisibilityOfElementLocated (By.xpath("//*[contains(text(),'просмотренные')]")));
    }


    //Геттеры сеттеры
    public WebDriverWait getWait() {
        return wait;
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }

    public void setMulti(WebDriver driver, WebDriverWait wait) {
        this.driverPages = driver;
        this.wait = wait;
    }
}
