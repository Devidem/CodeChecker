package pages.citilink;

import enums.Locators;
import experiments.SelEx;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * Главная страница сайта
 */
// Класс создан для примера ООП - чтобы ProdPage не было одиноко!
public class MainPage extends CitiPage {
    public MainPage(WebDriver driverStart) {
        super(driverStart);
    }
    private final WebDriver driverMain = getDriver();

    /**
     * Кликает по катекории из блока "Популярные категории"
     * @param categoryName Полное название категории
     */
    public void clickPopCategory (String categoryName) {

        String xpath = Locators.PopularCategory.getXpath();
        String propertyName = "innerText";

        SelEx selEx = new SelEx(driverMain);
        String categoryXpath = selEx.xpathSelectByProperty(xpath, propertyName, categoryName);
        WebElement category = driverMain.findElement(By.xpath(categoryXpath));
        click(category);

    }
}
