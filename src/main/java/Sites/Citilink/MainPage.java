package Sites.Citilink;

import Selen.SelEx;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

// Главная страница сайта
// Создан для примера по ООП - чтобы ProdPage не было одиноко!
public class MainPage extends CitiPage {
    public MainPage(WebDriver driverStart, WebDriverWait wait) {
        super(driverStart, wait);
    }
    private WebDriver driverMain = getDriver();

    //Жамкаем по категории из блока "Популярные категории"
    public void clickPopCategory (String categoryName) {

        String xpath = "//*[@class=\"edhylph0 app-catalog-1ljlt6q e3tyxgd0\"]";
        String propertyName = "innerText";

        SelEx selEx = new SelEx(driverMain);
        selEx.xpathSelectByProperty(xpath, propertyName, categoryName);

    }
}
