package pages.citilink.naviClasses;

import interfaces.Checkable;
import org.openqa.selenium.WebDriver;
import pages.citilink.Navigator;
import pages.citilink.builds.BuildProdPage;
import pages.citilink.checks.CheckProdPage;

/**
 * Страница товара
 */
public final class ProdPage extends BuildProdPage<ProdPage> implements Checkable<CheckProdPage> {
    public ProdPage(WebDriver driver, Navigator navigator) {
        super(driver, navigator);
    }

    /**
     * Возвращает класс {@link CheckProdPage} с методами проверок на данной странице
     */
    @Override
    public CheckProdPage check() {
        return new CheckProdPage(getDriver(), this);
    }
}
