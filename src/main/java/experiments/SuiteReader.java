package experiments;

import org.testng.Reporter;

import java.util.Map;

/**
 * Содержит методы для работы с Suite.xml
 */
public class SuiteReader {

    /**
     * Возвращает значение параметра.
     * @param name Имя параметра.
     */
    public static String getParameter (String name) {
        return Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(name);
    }

    /**
     * Возвращает коллекцию всех имеющихся параметров
     */
    public static Map<String, String> getAllParameters () {
        return  Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getAllParameters();
    }
}
