package buffers;

import java.util.HashMap;
import java.util.Objects;

/**
 * Буфер для раздельного хранения количества произведенных повторов у тестов.
 * Позволяет сделать подсчет количества повторов в тестах раздельным - у каждого свое значение.
 */
public class BufferRetryNum {
    private static final HashMap <String, Integer> buffer = new HashMap<>();

    /**
     * Возвращает количество произведенных повторов для теста (начинается с 0)
     */
    public synchronized static int get (String id) {

        //Если переменной нет, то добавляем 0 значение
        if (Objects.equals(buffer.get(id), null)) {
            buffer.put(id, 0);
        }

        return buffer.get(id);
    }

    /**
     * Увеличивает значение количества произведенных повторов
     */
    public synchronized static void increase (String id) {
        buffer.put(id, (get(id) + 1));
    }
}
