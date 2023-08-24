package tests.citilink.finalTest.supportClasses;

import buffers.BufferRetryNum;
import interfaces.Retryable;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Objects;

/**
 * RetryAnalyzer для "UI" групп
 * Делает {@link #maxTry} повторов для каждого теста
 */
public class MyRetryAnalyzerPromChecking implements IRetryAnalyzer {

    //Определяем максимальное число повторов
    int maxTry = 3;

    @Override
    public boolean retry(ITestResult iTestResult) {

        //Ставим условие на запуск только в UI тестах
        if (Arrays.stream(iTestResult.getMethod().getGroups()).anyMatch(x->x.contains("UI"))) {

            //Преобразуем iTestResult в Retryable
            Retryable retryable = (Retryable) iTestResult.getInstance();

            //Узнаем, требует ли ошибка анализа
            if (Objects.equals(retryable.getRetryVar(iTestResult), false)) {
                return false;
            }

            //Сохраняем идентификатор теста
            String TestId = retryable.getTestId(iTestResult);

            //Делаем повтор и увеличиваем значение счетчика повторов, пока не достигнем его максимального значения
            if (BufferRetryNum.get(TestId) < maxTry) {
                BufferRetryNum.increase(retryable.getTestId(iTestResult));
                return true;
            }
        }
        return false;
    }
}