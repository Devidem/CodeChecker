import Selen.Test;

import java.io.IOException;

public class NewMain {
    public static void main(String[] args) throws IOException {

        String input = "file";          //file, sql(не реализовано)
        String browser = "chrome";      //chrome, firefox(не реализовано)
        String site = "CitiLink";       //citilink, dns(не реализовано)


        Test test = new Test();
        test.codeChecks (browser, site, input);

    }
}