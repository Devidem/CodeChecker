package tests.citilink.testngAllure.supprotClasses.promChecking;

import enums.Locators;
import experiments.ITestResultManager;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyListenerPromChecking implements ITestListener {

    @Override
    public void onTestFailure(ITestResult iTestResult) {

        //Делаем скриншот, ограниченный контейнером с товаром
        ITestResultManager.addScreenShootOfElement(iTestResult, Locators.ProductPageProdContainer.getXpath());
    }

}
