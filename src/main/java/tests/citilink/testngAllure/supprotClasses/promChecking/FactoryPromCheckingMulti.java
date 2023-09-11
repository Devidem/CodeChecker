package tests.citilink.testngAllure.supprotClasses.promChecking;

import enums.ConstInt;
import enums.ConstString;
import exceptions.myExceptions.MyFileIOException;
import exceptions.myExceptions.MyInputParamException;
import io.qameta.allure.Step;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import selectors.InputType;
import tests.citilink.testngAllure.PromCheckingMultiFactory;

import java.io.IOException;


public class FactoryPromCheckingMulti {

    String [][] checkList;
    String siteName;

    @Step ("Set Start Values")
    public void setValues (String inpType) throws MyFileIOException, IOException, MyInputParamException {

        //Получение чек-листа для дальнейшей проверки
        checkList = InputType.toFinalArray(inpType);

        //Получение полной ссылки сайта
        this.siteName = ConstString.CitilinkAdress.getValue();
    }

    @Test(groups = "multi")
    @Factory
    @Parameters ({"inputType", "browserName"})
    public Object[] factoryMethod(String inpType, String browserName) throws MyFileIOException, IOException, MyInputParamException {
        setValues(inpType);

        int startRow = ConstInt.startRow.getValue();

        Object [] dynamicObject = new Object[checkList.length-startRow];

        //Создается чек-лист для одного товара
        for (int i = startRow; i < checkList.length; i++) {

            String [][] singleCheckList = new String[2][checkList[0].length];

            System.arraycopy(checkList[0], 0, singleCheckList[0], 0, singleCheckList[0].length);
            System.arraycopy(checkList[i], 0, singleCheckList[1], 0, singleCheckList[0].length);

            dynamicObject [i-startRow] = new PromCheckingMultiFactory(singleCheckList, browserName, this.siteName, i-startRow);
        }
        return dynamicObject;
    }
}
