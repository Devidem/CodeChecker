package tests.citilink.testngAllure.supprotClasses.promChecking;

import enums.ConstInt;
import enums.ConstString;
import exceptions.myExceptions.MyFileIOException;
import fabrics.SetDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.NoPage;
import selectors.Browsers;
import selectors.InputType;
import tests.citilink.testngAllure.PromCheckingSingleFactory;


public class FactoryPromChecking {

    String [][] checkList;
    WebDriver driver;

    @Step ("Set Start Values")
    public void setValues (String inpType, String browserName) throws MyFileIOException {

        //Получение чеклиста для дальнейшей проверки
        InputType inputType = new InputType(inpType);
        checkList = inputType.toFinalArray();

        //Получение полной ссылки сайта
        String siteName = ConstString.CitilinkAdress.getValue();

        //Выбор браузера и его запуск + настройка
        Browsers browsers = new Browsers(browserName);
        driver = browsers.start();
        SetDriver.standard(driver);

        //Переход на сайт
        NoPage noPage= new NoPage(driver);
        noPage.get(siteName);

    }

    @Test(groups = "factory")
    @org.testng.annotations.Factory
    @Parameters ({"inputType", "browserName"})
    public Object[] factoryMethod(String inpType, String browserName) throws MyFileIOException {
        setValues(inpType, browserName);

        int startRow = ConstInt.startRow.getValue();

        Object [] dynamicObject = new Object[checkList.length-startRow];

        //Создается чек-лист для одного товара
        for (int i = startRow; i < checkList.length; i++) {

            String [][] singleCheckList = new String[2][checkList[0].length];

            System.arraycopy(checkList[0], 0, singleCheckList[0], 0, singleCheckList[0].length);
            System.arraycopy(checkList[i], 0, singleCheckList[1], 0, singleCheckList[0].length);

            dynamicObject [i-startRow] = new PromCheckingSingleFactory(singleCheckList, this.driver, i-startRow);

        }
        return dynamicObject;
    }
}
