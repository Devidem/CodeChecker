package pages.citilink.old;

import enums.Locators;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Класс с общими методами для страниц сайта Citilink
 */
//Шапка с поиском и каталогом есть на всех страницах
public abstract class CitiPageOld extends InitialPageOld {

    public CitiPageOld(WebDriver driver) {
        super(driver);
    }
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));



    /**
     * Кликает по лупе.
     */
    public void clickSearchButton () {
        try {
            String xpath = Locators.SearchButton.getXpath();
            WebElement element = driver.findElement(By.xpath(xpath));
            click(element);
        } catch (TimeoutException ignored) {
        }
    }


    /**
     * Кликает по товару из результатов быстрого поиска.
     * @param prodCode Код товара.
     */
    @Step("Клик по товару из результатов быстрого поиска")
    public void clickSearchResult (String prodCode) {
        String xpath = Locators.VarSearchResult.getXpathVariable(prodCode);
        WebElement prodLink = driver.findElement(By.xpath(xpath));
        click(prodLink);
    }

    /**
     * Кликает по кнопке каталога, находящейся слева от поиска.
     */
    public void clickCatalog () {
            String xpath = Locators.Catalog.getXpath();
            WebElement element = driver.findElement(By.xpath(xpath));
            click(element);
    }

    /**
     * Вводит текст в строку поиска.
     * @param text Текст запроса.
     */
    @Step("Ввод текста в строку поиска")
    public void enterSearch (String text) {
        String xpathSearch = Locators.SearchField.getXpath();
        String xpathWatched = Locators.SearchWatchedBefore.getXpath();

        WebElement Search = driver.findElement(By.xpath(xpathSearch));
        Search.clear();
        Search.sendKeys(text);

        //Ожидается исчезновение окна с прежними просмотренными товарами - если не ждать, то может по нему кликнуть,
        //вместо найденного результата + могут возникнуть проблемы с определением веб-элементов в дальнейшем (теряются
        //после изменений в окне результатов)
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathWatched)));
        } catch (Exception ignored) {
        }
    }


    //Геттеры сеттеры
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
