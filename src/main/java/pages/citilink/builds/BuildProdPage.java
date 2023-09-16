package pages.citilink.builds;

import org.openqa.selenium.WebDriver;
import pages.citilink.Navigator;
import pages.citilink.pageObjects.MainPanel;


/**
 * Build класс для страницы товара
 */
public abstract class BuildProdPage<T> extends BuildCitiPage<T> {
    public BuildProdPage(WebDriver driver, Navigator navigator) {
        super(driver, navigator);
    }

    /**
     * Возвращает PageObject {@link MainPanel} для данной страницы
     */
    //Данный метод сделан для демонстрации того, как можно использовать PageObject в текущей реализации
    public MainPanel<T> inMainPanel() {
        return new MainPanel<T>(getDriver(), (T)this);
    }
}




