package pages.citilink.builds;

import enums.Locators;
import experiments.ExSelen;
import org.openqa.selenium.WebDriver;
import pages.citilink.Navigator;


/**
 * Build класс главной страница сайта
 */
public abstract class BuildMainPage <T> extends BuildCitiPage <T> {
    public BuildMainPage(WebDriver driver, Navigator navigator) {
        super(driver, navigator);
    }

    /**
     * Клик по категории из блока "Популярные категории".
     * @param categoryName Полное название категории.
     */
    //Метод используется для демонстрации работы exSelen.xpathSelectByProperty
    public T clickPopCategory (String categoryName) {

        String xpath = Locators.PopularCategory.getXpath();
        String propertyName = "innerText";

        ExSelen exSelen = new ExSelen(driver);
        String categoryXpath = exSelen.xpathSelectByProperty(xpath, propertyName, categoryName);
        click(categoryXpath);
        return (T) this;
    }
}
