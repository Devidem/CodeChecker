package tests.citilink.javaSelenOnly.old;

import exceptions.myExceptions.MyFileIOException;
import exceptions.myExceptions.MyInputParamException;

import java.io.IOException;

public class RunnerOld {
    public static void main(String[] args) {

        String inputType = "file";          //file, sql
        String browserName = "chrome";      //chrome, firefox
        int threadsNumber = 2;              //Количество потоков (указывается 3-й переменной в конструкторе)


        TestOld test = new TestOld();

        try {
            //Имеет 2 варианта - однопоточный и многопоточный
            test.codeChecks(browserName, inputType, threadsNumber);

        } catch (MyFileIOException | IOException | MyInputParamException e) {
            //Исключения дополнительно не обрабатываются
            throw new RuntimeException(e);
        }
    }
}
