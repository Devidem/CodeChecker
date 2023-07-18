package tests.citilink.testngAllure.supprotClasses.promChecking.multi;

import enums.ConstInt;
import enums.ConstString;
import exceptions.myExceptions.MyFileIOException;
import io.qameta.allure.Step;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import selectors.InputType;
import tests.citilink.testngAllure.PromCheckingMulti;


public class FactoryPromCheckingMulti {

    String [][] checkList;
    String siteName;

    @Step ("Set Start Values")
    public void setValues (String inpType) throws MyFileIOException {

        //Получение чеклиста для дальнейшей проверки
        InputType inputType = new InputType(inpType);
        checkList = inputType.toFinalArray();

        //Получение полной ссылки сайта
        this.siteName = ConstString.CitilinkAdress.getValue();
    }

    @Test(groups = "multi")
    @Factory
    @Parameters ({"inputType", "browserName"})
    public Object[] factoryMethod(String inpType, String browserName) throws MyFileIOException {
        setValues(inpType);

        int startRow = ConstInt.startRow.getValue();

        Object [] dynamicObject = new Object[checkList.length-startRow];

        //Создается чек-лист для одного товара
        for (int i = startRow; i < checkList.length; i++) {

            String [][] singleCheckList = new String[2][checkList[0].length];

            System.arraycopy(checkList[0], 0, singleCheckList[0], 0, singleCheckList[0].length);
            System.arraycopy(checkList[i], 0, singleCheckList[1], 0, singleCheckList[0].length);

            dynamicObject [i-startRow] = new PromCheckingMulti(singleCheckList, browserName, this.siteName, i-startRow);
        }
        return dynamicObject;
    }
}
