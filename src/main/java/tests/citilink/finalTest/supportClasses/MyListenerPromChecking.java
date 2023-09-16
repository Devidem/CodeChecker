package tests.citilink.finalTest.supportClasses;

import experiments.ITestResultManager;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

/**
 * Общий слушатель для UI тестов
 */
public class MyListenerPromChecking implements ITestListener {
    @Override
    public void onTestFailure(ITestResult iTestResult) {

        //Ставим условие на запуск только в UI тестах
        if (Arrays.stream(iTestResult.getMethod().getGroups()).anyMatch(x->x.contains("UI"))) {
            try {
                //Делаем скриншот, ограниченный контейнером с товаром
                ITestResultManager.addScreenShootOfElement(iTestResult);
            } catch (Exception e) {
                System.out.println("Не получилось сделать скриншот\n" + e.getMessage());
            } finally {
                //Возвращаем вебдрайвер в буфер
                ITestResultManager.returnBrowser(iTestResult);
            }


        }
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        //Ставим условие на запуск только в UI тестах
        if (Arrays.stream(iTestResult.getMethod().getGroups()).anyMatch(x->x.contains("UI"))) {
            //Возвращаем вебдрайвер в буфер
            ITestResultManager.returnBrowser(iTestResult);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        //Ставим условие на запуск только в UI тестах
        if (Arrays.stream(iTestResult.getMethod().getGroups()).anyMatch(x->x.contains("UI"))) {
            try {
                //Делаем скриншот, ограниченный контейнером с товаром
                ITestResultManager.addScreenShootOfElement(iTestResult);
            } catch (Exception e) {
                System.out.println("Не получилось сделать скриншот\n" + e.getMessage());
            } finally {
                //Возвращаем вебдрайвер в буфер
                ITestResultManager.returnBrowser(iTestResult);
            }
        }
    }
}
