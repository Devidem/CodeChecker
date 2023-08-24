package tests.citilink.finalTest.supportClasses;

import enums.Locators;
import experiments.ITestResultManager;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class MyListenerPromChecking implements ITestListener {
    @Override
    public void onTestFailure(ITestResult iTestResult) {

        //Ставим условие на запуск только в UI тестах
        if (Arrays.stream(iTestResult.getMethod().getGroups()).anyMatch(x->x.contains("UI"))) {
            //Делаем скриншот, ограниченный контейнером с товаром
            ITestResultManager.addScreenShootOfElement(iTestResult, Locators.ProductPageProdContainer.getXpath());
        }
    }
}
