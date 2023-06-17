package Selectors;

import java.io.IOException;
import java.util.Objects;

public class InputType {
    public static String[][] selector (String Type) throws IOException {

        Type = Type.toLowerCase();

        if (Type.contains("file")) {

            return Files.selector();

        } else if (Type.contains("sql")) {

            System.out.println("Кто такой этот ваш SQl? Пока не сделан!");
        }

        System.out.println("Неверный тип входных данных!");
        return null;
    }
}
