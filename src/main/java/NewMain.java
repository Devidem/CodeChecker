import exceptions.myExceptions.MyFileIOException;
import tests.Test;

public class NewMain {
    public static void main(String[] args) {

        String input = "file";          //file, sql(не реализовано)
        String browser = "chrome";      //chrome, firefox(не реализовано)
        String site = "CitiLink";       //citilink, dns(не реализовано)
        int threadsNumber = 3;          //Количество потоков (указывается 4-й переменной в конструкторе)


        Test test = new Test();

        try {
            //Имеет 2 варианта - однопоточный и многопоточный
            test.codeChecks(browser, site, input, threadsNumber);
        } catch (MyFileIOException e) {
            //Все ошибки и исключения подписаны с помощью сообщений
            throw new RuntimeException(e);
        }


    }

}
