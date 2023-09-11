package enums;

/**
 * Содержит String переменные, которые не требуют создания отдельного Enum, но используются в нескольких местах
 */
public enum ConstString {

    CitilinkAdress ("https://www.citilink.ru/"),
    InputFileDirectory ("./Inputs/Files"),
    ChromeDriverDirectory ("./SelenDrivers/chromedriver.exe"),
    FirefoxDriverDirectory ("./SelenDrivers/geckodriver.exe")
    ;


//--------------------------------------------------------------------------------------------------------------------//

    /**
     * Получение постоянной переменной
     */
    public String getValue() {
        return value;
    }

//--------------------------------------------------------------------------------------------------------------------//
    private final String value;

    ConstString(String value) {
        this.value = value;
    }
}
