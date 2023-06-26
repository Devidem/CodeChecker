package sites.citilink;

import selen.SelEx;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Главная страница сайта
 */
// Класс создан для примера ООП - чтобы ProdPage не было одиноко!
public class MainPage extends CitiPage {
    public MainPage(WebDriver driverStart, WebDriverWait wait) {
        super(driverStart, wait);
    }
    private final WebDriver driverMain = getDriver();

    /**
     * Кликает по катекории из блока "Популярные категории"
     * @param categoryName Полное название категории
     */
    public void clickPopCategory (String categoryName) {

        String xpath = "//*[@class=\"edhylph0 app-catalog-1ljlt6q e3tyxgd0\"]";
        String propertyName = "innerText";

        SelEx selEx = new SelEx(driverMain);
        String categoryXpath = selEx.xpathSelectByProperty(xpath, propertyName, categoryName);
        WebElement category = driverMain.findElement(By.xpath(categoryXpath));
        click(category);

    }
}
