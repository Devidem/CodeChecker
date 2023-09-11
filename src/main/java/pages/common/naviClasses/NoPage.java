package pages.common.naviClasses;

import interfaces.Checkable;
import org.openqa.selenium.WebDriver;
import pages.citilink.Navigator;
import pages.common.builds.BuildInitialPage;
import pages.common.checks.CheckInit;

/**
 * Пустая/неизвестная страница
 */
public class NoPage extends BuildInitialPage<NoPage> implements Checkable<CheckInit<NoPage>> {
    public NoPage(WebDriver driver, Navigator navigator) {
        super(driver, navigator);
    }


    //Методы Checkable
    @Override
    public CheckInit<NoPage> check() {
        return new CheckInit<NoPage> (getDriver());
    }
}
