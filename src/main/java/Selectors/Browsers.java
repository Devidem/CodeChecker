package Selectors;

import Converts.ArrayEx;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Управление браузером.
 */
public class Browsers extends Selectors {

    public Browsers(String input) {
        super(input);
    }

    /**
     * Запускает нужный браузер в зависимости от значения {@link #result}.
     */
    public WebDriver start() {

        selector();

        if(result.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "./SelenDrivers/chromedriver.exe");
            return new ChromeDriver();
        }

        System.out.println("Wrong Browser!"); //Скорее всего забыли про selector()!
        return null;
    }

    /**
     * Выбирает браузер из массива {@link #input} и передает значение в {@link #result}.
     */
    public void selector() {
        input = input.toLowerCase();

        String[] broList = {"chrome", "firefox(для примера)"};

        if (input.contains("chrome")) {
            result = "chrome";

        } else if (input.contains("firefox")) {
            result = "firefox";

        } else {
            System.out.println("Unknown Browser");
            result = ArrayEx.selector1D(broList);

        }

    }


    //    /**
//     * Запускает запрашиваемый браузер. В случае некорректного ввода предлагает выбрать браузер из списка доступных.
//     * @param browserName Имя браузера.
//     */
//    public static WebDriver start(String browserName) {
//        String checkName = selector(browserName);
//
//        if(checkName.contains("chrome")) {
//            System.setProperty("webdriver.chrome.driver", "./SelenDrivers/chromedriver.exe");
//            return new ChromeDriver();
//
//        } else {
//            System.out.println("Wrong Browser!"); //Скорее всего забыли про selector()!
//            return null;
//
//        }
//
//    }

//    /**
//     * Выбирает нужный браузер из списка доступных.
//     * Дает сделать ручной выбор при некорректном вводе имени браузера.
//     * @param browserName Имя браузера.
//     */
//    public static String selector(String browserName) {
//        browserName = browserName.toLowerCase();
//
//        String[] broList = {"chrome", "firefox(для примера работы селектора)"};
//
//        if (browserName.contains("chrome")) {
//            return "chrome";
//
//        } else {
//            System.out.println("Unknown Browser");
//            return ArrayEx.selector1D(broList);
//
//        }
//
//    }

}
