package tests.ng;

import exceptions.myExceptions.MyFileIOException;
import fabrics.FabricDriverSets;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.NoPage;
import selectors.Browsers;
import selectors.InputType;
import selectors.Sites;


public class PromCheckingFactory {

//    String inpType = "file";         //file, sql(не реализовано)
//    String browserName = "chrome";   //chrome, firefox(не реализовано)
//    String siteName = "CitiLink";    //citilink, dns(не реализовано)
    String [][] checkList;
    WebDriver driver;

    public void setValues (String inpType, String browserName, String siteName) throws MyFileIOException {

        //Получение чеклиста для дальнейшей проверки
        InputType inputType = new InputType(inpType);
        checkList = inputType.toFinalArray();

        //Получение полной ссылки сайта
        Sites sites = new Sites(siteName);
        sites.selector();
        siteName = sites.getResult();

        //Выбор браузера и его запуск
        Browsers browsers = new Browsers(browserName);
        driver = FabricDriverSets.standard(browsers.start());

        //Переход на сайт
        NoPage noPage= new NoPage(driver);
        noPage.get(siteName);

//        System.out.println("Amogus");

    }
    @Test(groups = "factory")
    @org.testng.annotations.Factory
    @Parameters ({"inputType", "browserName", "siteName"})
    public Object[] factoryMethod(String inpType, String browserName, String siteName) throws MyFileIOException {
        setValues(inpType, browserName, siteName);

//        System.out.println("Nomogus");
        int startRow = 3-1;

        Object [] dynamicObject = new Object[checkList.length-startRow];

        //Создается чек-лист для одного товара
        for (int i = startRow; i < checkList.length; i++) {

            String [][] singleCheckList = new String[2][checkList[0].length];

            System.arraycopy(checkList[0], 0, singleCheckList[0], 0, singleCheckList[0].length);
            System.arraycopy(checkList[i], 0, singleCheckList[1], 0, singleCheckList[0].length);
//            System.out.println("Pepegus"+i);

//            PromChecking checking = new PromChecking(singleCheckList, this.driver, i-startRow);
//            System.out.println("Gogogus"+i);

            dynamicObject [i-startRow] = new PromChecking(singleCheckList, this.driver, i-startRow);
//            System.out.println("doomgus"+i);
        }

        return dynamicObject;

    }

}
