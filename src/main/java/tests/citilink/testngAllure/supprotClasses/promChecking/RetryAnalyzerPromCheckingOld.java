package tests.citilink.testngAllure.supprotClasses.promChecking;

import interfaces.old.RetryableOld;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Objects;

/**
 * Делает {@link #maxTry} повторов, если страница товара не прогрузилась
 */
public class RetryAnalyzerPromCheckingOld implements IRetryAnalyzer {

    int maxTry = 3;
    int tryNum = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {

        //Получаем значение result из теста
        Object instance = iTestResult.getInstance();
        String result = ((RetryableOld) instance).getRetryVar();

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
}
