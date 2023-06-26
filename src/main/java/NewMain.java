import exceptions.myExceptions.MyFileIOException;
import selen.Test;

public class NewMain {
    public static void main(String[] args) {

        String input = "file";          //file, sql(не реализовано)
        String browser = "chrome";      //chrome, firefox(не реализовано)
        String site = "CitiLink";       //citilink, dns(не реализовано)


        Test test = new Test();

        try {
            test.codeChecks (browser, site, input);
        } catch (MyFileIOException e) {
            //Все ошибки исключения подписаны с помощью сообщений
            throw new RuntimeException(e);
        }


    }

}