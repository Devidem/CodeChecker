package enums;

public enum ConstString {

    CitilinkAdress ("https://www.citilink.ru/"),
    InputFileDirectory ("./Inputs/Files"),
    ChromeDriverDirectory ("./SelenDrivers/chromedriver.exe"),
    FirefoxDriverDirectory ("./SelenDrivers/geckodriver.exe")
    ;



    public String getValue() {
        return value;
    }

    private final String value;

    ConstString(String value) {
        this.value = value;
    }
}
