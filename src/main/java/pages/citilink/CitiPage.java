package pages.citilink;

import locators.Locators;
import pages.InitialPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Класс с общими методами для страниц сайта Citilink
 */
//Шапка с поиском и каталогом есть на всех страницах
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
            String xpath = Locators.SearchButton.getXpath();
            WebElement element = driverPages.findElement(By.xpath(xpath));
            click(element);
        } catch (TimeoutException ignored) {
        }
    }


    /**
     * Кликает по товару из результатов быстрого поиска.
     * @param prodCode Код товара.
     */
    public void clickSearchResult (String prodCode) {
        String xpath = Locators.VarSearchResult.getXpathVariable(prodCode);
        WebElement prodLink = driverPages.findElement(By.xpath(xpath));
        click(prodLink);
    }

    /**
     * Кликает по кнопке каталога, находящейся слева от поиска.
     */
    public void clickCatalog () {
            String xpath = Locators.Catalog.getXpath();
            WebElement element = driverPages.findElement(By.xpath(xpath));
            click(element);
    }

    /**
     * Вводит текст в строку поиска.
     * @param text Текст запроса.
     */
    public void enterSearch (String text) {
        String xpathSearch = Locators.SearchField.getXpath();
        String xpathWatched = Locators.SearchWatchedBefore.getXpath();

        WebElement Search = driverPages.findElement(By.xpath(xpathSearch));
        Search.sendKeys(text);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathWatched)));
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
