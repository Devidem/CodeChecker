package tests.citilink.finalTest.supportClasses;

import interfaces.RetryableHash;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Objects;

/**
 * RetryAnalyzer для тестов с использванием {@link experiments.BufferDriver} - запускается только в группах "UI"
 * Делает {@link #maxTry} повторов, если страница товара не прогрузилась
 */
public class MyRetryAnalyzerPromCheckingHash implements IRetryAnalyzer {

    int maxTry = 3;
    int tryNum = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {

        //Ставим условие на запуск только в UI тестах
        if (Arrays.stream(iTestResult.getMethod().getGroups()).anyMatch(x->x.contains("UI"))) {

            //Получаем объект класса теста
            Object instance = iTestResult.getInstance();

            //По коду товара получаем проверочную переменную из хеш-мапы
            String prodCode = iTestResult.getParameters()[0].toString();
            String result = ((RetryableHash) instance).getRetryVar(prodCode);

            // Остановка повторов при достижении максимального количества
            if (tryNum==maxTry) {
                return false;
            }

            // Делаем повтор только в случаях, когда страница не прогрузилась
            // Failed получаем в случаях, когда страница прогрузилась, но проверка дала отрицательный результат и поэтому
            // перезапуск не нужен
            if (Objects.equals(result, "Failed")) {
                return false;
            } else {
                tryNum++;
                return true;
            }
        }
        return false;
    }
}
