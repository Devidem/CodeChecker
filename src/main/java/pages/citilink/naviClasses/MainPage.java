package pages.citilink.naviClasses;

import org.openqa.selenium.WebDriver;
import pages.citilink.Navigator;
import pages.citilink.builds.BuildMainPage;


/**
 * Главная страница сайта
 */
// Класс создан для примера ООП - чтобы ProdPage не было одиноко!
public final class MainPage extends BuildMainPage<MainPage> {
    public MainPage(WebDriver driver, Navigator navigator) {
        super(driver, navigator);
    }
}
