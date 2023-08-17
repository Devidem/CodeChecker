package experiments;

import java.util.Objects;

/**
 * Измененные методы класса Object
 */
public class ExObjects {
    /**
     * Аналог Object.toString, но преобразует null в пустую строку
     */
    public static String toString (Object object) {
        String o_change = Objects.toString(object);
        if (Objects.equals(o_change, "null")) {
            o_change = "";
        }
        return o_change;
    }

}
