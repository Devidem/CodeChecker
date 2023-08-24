package tests.citilink.testngAllure.supprotClasses.promChecking;

import enums.Locators;
import experiments.ITestResultManager;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyListenerPromCheckingOld implements ITestListener {

    @Override
    public void onTestFailure(ITestResult iTestResult) {

        //Делаем скриншот, ограниченный контейнером с товаром
        ITestResultManager.addScreenShootOfElementOld(iTestResult, Locators.ProductPageProdContainer.getXpath());
    }

}
