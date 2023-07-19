package experiments;

import org.testng.Reporter;

import java.util.Map;

public class SuiteReader {

    public static String getParameter (String name) {
        return Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(name);
    }

    public static Map<String, String> getAllParameters () {
        return  Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getAllParameters();
    }
}
