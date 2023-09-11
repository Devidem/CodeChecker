package pages.citilink.pageObjects;

import enums.Locators;
import interfaces.Backable;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Главная панель страниц сайта Ситилинк, которая есть на каждой странице
 * @param <T> Класс страницы, к которой можно будет вернуться
 */
//Данный класс создан для демонстрации того, как можно использовать PageObject в текущей реализации
public class MainPanel<T> implements Backable<T> {
    private final WebDriver driver;
    private final T t;

    public MainPanel(WebDriver driver, T t) {
        this.driver = driver;
        this.t = t;
    }

    /**
     * Клик по лупе.
     */
    public MainPanel<T> clickSearchButton () {
        try {
            String xpath = Locators.SearchButton.getXpath();
            driver.findElement(By.xpath(xpath)).click();
        } catch (TimeoutException ignored) {
        }
        return this;
    }


    /**
     * Клик по товару из результатов быстрого поиска.
     * @param prodCode Код товара.
     */
    @Step("Клик по товару из результатов быстрого поиска")
    public MainPanel<T> clickSearchResult (String prodCode) {
        String xpath = Locators.VarSearchResult.getXpathVariable(prodCode);
        driver.findElement(By.xpath(xpath)).click();
        return this;
    }

    /**
     * Клик по кнопке каталога, находящейся слева от поиска.
     */
    public MainPanel<T> clickCatalog () {
        String xpath = Locators.Catalog.getXpath();
        driver.findElement(By.xpath(xpath)).click();
        return this;
    }

    /**
     * Вводит текст в строку поиска, предварительно очистив ее.
     * @param text Текст запроса.
     */
    @Step("Ввод текста в строку поиска")
    public MainPanel<T> enterSearch (String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        String xpathSearch = Locators.SearchField.getXpath();
        String xpathWatched = Locators.SearchWatchedBefore.getXpath();

        //Ввод текста с предварительной очисткой поля
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
        return this;
    }


    //Методы Backable<T>
    @Override
    public T and() {
        return t;
    }
    @Override
    public T then() {
        return t;
    }

    @Override
    public T also() {
        return t;
    }


}
