package interfaces;

/**
 * Позволяет использовать класс в RetryAnalyzer, благодаря получению проверочной переменной через метод {@link #getRetryVar()}
 */
public interface Retryable {
    /**
     * Возвращает значение проверочной переменной (для RetryAnalyzer)
     */
    public String getRetryVar();
}
