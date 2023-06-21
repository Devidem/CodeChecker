package Selectors;

import Converts.ArrayEx;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browsers extends Selectors {

    public WebDriver start() {

        if(result.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "./SelenDrivers/chromedriver.exe");
            return new ChromeDriver();
        }

        System.out.println("Wrong Browser!"); //Скорее всего забыли про selector()!
        return null;
    }


    public void selector() {
        input = input.toLowerCase();

        String[] broList = {"chrome", "firefox(для примера)"};

        if (input.contains("chrome")) {
            result = "chrome";

        } else if (input.contains("firefox")) {
            result = "firefox";

        } else {
            System.out.println("Unknown Browser");

            ArrayEx arrayEx = new ArrayEx();
            arrayEx.setInput1D(broList);
            arrayEx.selector1D();
            result = arrayEx.getResult1D();

        }

    }

}
