package experiments;

import buffers.BufferSuiteVar;
import enums.ConstString;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Класс для предварительных настроек, которым достаточно одной инициализации в начале прогона тестов
 */
public class Initializator {

    @BeforeSuite (groups = "Init")
    public void initCollection () {
        System.setProperty("webdriver.gecko.driver", ConstString.FirefoxDriverDirectory.getValue());
        System.setProperty("webdriver.chrome.driver", ConstString.ChromeDriverDirectory.getValue());
        BufferSuiteVar.read();
    }

    @Test (groups = "Init")
    public void showMessage () {
        System.out.println("Предустановки выполнены успешно");
    }

}
