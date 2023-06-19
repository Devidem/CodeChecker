package Selectors;

import Interfaces.Arrayer;

import java.io.IOException;
import java.util.Objects;

public class InputType extends Selectors implements Arrayer {

    public String [][] toFinalArray() throws IOException {
        Files files = new Files();
        files.selector();
        return files.toFinalArray();
    }

    public void selector () {

        String Type = input.toLowerCase();

        if (Type.contains("file")) {

            Files files = new Files();
            files.selector();
            result = files.getResult();

        } else if (Type.contains("sql")) {

            System.out.println("Кто такой этот ваш SQl? Пока не сделан!");
        }

        System.out.println("Неверный тип входных данных!");
    }
}
