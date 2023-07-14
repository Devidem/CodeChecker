import exceptions.myExceptions.MyFileIOException;
import tests.citilink.javaSelenOnly.Test;

public class NewMain {
    public static void main(String[] args) {

        String input = "file";          //file, sql(не реализовано)
        String browser = "chrome";      //chrome, firefox(не реализовано)
        int threadsNumber = 2;          //Количество потоков (указывается 3-й переменной в конструкторе)


        Test test = new Test();

        try {
            //Имеет 2 варианта - однопоточный и многопоточный
            test.codeChecks(browser, input, threadsNumber);
        } catch (MyFileIOException e) {
            //Все ошибки и исключения подписаны с помощью сообщений
            throw new RuntimeException(e);
        }


    }

}
