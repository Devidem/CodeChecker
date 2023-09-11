package pages.citilink.builds;

import enums.Locators;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.citilink.Navigator;
import pages.common.builds.BuildInitialPage;

import java.time.Duration;

/**
 * Build класс с общими методами для всех страниц сайта Citilink
 */
//Шапка с поиском и каталогом есть на всех страницах
public abstract class BuildCitiPage<T> extends BuildInitialPage<T> {
    public BuildCitiPage(WebDriver driver, Navigator navigator) {
        super(driver, navigator);
    }

    /**
     * Клик по лупе.
     */
    public T clickSearchButton () {
        try {
            String xpath = Locators.SearchButton.getXpath();
            click(xpath);
        } catch (TimeoutException ignored) {
        }
        return (T) this;
    }


    /**
     * Клик по товару из результатов быстрого поиска.
     * @param prodCode Код товара.
     */
    @Step("Клик по товару из результатов быстрого поиска")
    public T clickSearchResult (String prodCode) {
        String xpath = Locators.VarSearchResult.getXpathVariable(prodCode);
        click(xpath);
        return (T) this;
    }

    /**
     * Клик по кнопке каталога, находящейся слева от поиска.
     */
    public T clickCatalog () {
        String xpath = Locators.Catalog.getXpath();
        click(xpath);
        return (T) this;
    }

    /**
     * Вводит текст в строку поиска, предварительно очистив ее.
     * @param text Текст запроса.
     */
    @Step("Ввод текста в строку поиска")
    public T enterSearch (String text) {
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
        return (T) this;
    }
}
