package Experiments;

import java.util.Objects;

public class ObjectsEx {
    public static String toString (Object o) {
        String o_change = Objects.toString(o);
        if (Objects.equals(o_change, "null")) {
            o_change = "";
        }
        return o_change;
    }

}
