package tests.citilink.javaSelenOnly;

import exceptions.myExceptions.MyFileIOException;
import exceptions.myExceptions.MyInputParamException;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) {

        String inputType = "file";          //file, sql
        String browserName = "chrome";      //chrome, firefox
        int threadsNumber = 2;              //Количество потоков (указывается 3-й переменной в конструкторе)

        Tests tests = new Tests();
        try {
            //Имеет 2 варианта - однопоточный и многопоточный
            tests.promCheckingMultiThread(browserName, inputType, threadsNumber);

        } catch (MyFileIOException | IOException | InterruptedException | MyInputParamException e) {
            //Исключения дополнительно не обрабатываются
            throw new RuntimeException(e);
        }
    }
}
