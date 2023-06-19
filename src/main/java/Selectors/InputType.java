package Selectors;

import Converts.Array;
import Interfaces.Arrayer;

import java.io.IOException;

public class InputType extends Selectors implements Arrayer {

    public String [][] toFinalArray() throws IOException {
        if (result.contains("file")) {
            Files files = new Files();
            files.selector();
            return files.toFinalArray();
        }

        System.out.println("Wrong Type!"); //Скорее всего забыли про selector()!
        return null;

    }

    public void selector () {

        String Type = input.toLowerCase();
        String [] inputList = {"file", "sql(для примера)"};

        if (Type.contains("file")) {
            result = "file";

        } else if (Type.contains("sql")) {
            System.out.println("Кто такой этот ваш SQl? Пока не сделан!");

        } else {
            System.out.println("Unknown Type");

            Array array = new Array();
            array.setInput1D(inputList);
            array.selector1D();
            result = array.getResult1D();

        }

    }

}
