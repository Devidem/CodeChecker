package interfaces.old;

/**
 * Позволяет использовать класс в RetryAnalyzer, благодаря получению проверочной переменной через метод {@link #getRetryVar()}
 */
public interface RetryableOld {
    /**
     * Возвращает значение проверочной переменной (для RetryAnalyzer)
     */
    String getRetryVar();
}
