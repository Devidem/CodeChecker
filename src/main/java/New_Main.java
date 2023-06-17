


import Selectors.Browsers;
import Selectors.InputType;
import Selectors.Sites;
import Selen.Test;

import java.io.IOException;

public class New_Main {
    public static void main(String[] args) throws IOException {
        String input = "file";
        String browser = "chrome";
        String site = "citilink";


        Test test = new Test();
        test.codeChecks (browser, site, input);

    }
}