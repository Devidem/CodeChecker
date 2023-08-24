package interfaces;

import org.testng.ITestResult;
import tests.citilink.finalTest.supportClasses.MyRetryAnalyzerPromChecking;


/**
 * Позволяет использовать тестовый класс в {@link MyRetryAnalyzerPromChecking}.
 */
//Изначальная идея в том, что мы можем сами определять внутри теста, через булеву переменную, нужно ли анализировать
//зафейленный тест, а затем, если нужно, уже обрабатывать его с помощью кода из RetryAnalyzer.
public interface Retryable {
    /**
     * Возвращает значение проверочной переменной
     * @return true - запускать RetryAnalyzer, false - не запускать
     */
    Boolean getRetryVar(ITestResult iTestResult);

    /**
     * Возвращает уникальный идентификатор для отдельного теста
     */
    String getTestId (ITestResult iTestResult);
}
