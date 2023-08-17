package interfaces;

import buffers.BufferDriver;
import tests.citilink.finalTest.supportClasses.MyRetryAnalyzerPromCheckingHash;

/**
 * Позволяет использовать класс в {@link MyRetryAnalyzerPromCheckingHash},
 * благодаря получению проверочной переменной через метод {@link #getRetryVar(String)}.
 * Успользуется в тестах с использованием {@link BufferDriver}
 */
public interface RetryableHash {

    /**
     * Возвращает значение проверочной переменной (для RetryAnalyzer)
     */
    String getRetryVar(String hashKey);
}
