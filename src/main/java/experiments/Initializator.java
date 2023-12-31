package experiments;

import buffers.BufferDriver;
import buffers.BufferSuiteVar;
import enums.ConstString;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Класс для предварительных настроек, которым достаточно одной инициализации в процессе прогона тестов
 */
public class Initializator {

    @BeforeSuite (groups = "Init")
    public void initCollection () {

        //Указываем расположение вебдрайверов
        System.setProperty("webdriver.gecko.driver", ConstString.FirefoxDriverDirectory.getValue());
        System.setProperty("webdriver.chrome.driver", ConstString.ChromeDriverDirectory.getValue());

        //Считываем параметры из Сьюита
        BufferSuiteVar.read();

        // Копирование categories.json в allure-results
        FileManager.copyCategories();
    }

    @Test (groups = "Init")
    public void showMessage () {
        System.out.println("Предустановки выполнены успешно");
    }

    @AfterSuite (groups = "Init")
    public void afterSuite () {
        //Закрываем все браузеры из буфера
        BufferDriver.closeAllDrivers();
    }
}
