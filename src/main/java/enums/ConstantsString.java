package enums;

public enum ConstantsString {

    CitilinkAdress ("https://www.citilink.ru/"),
    InputFileDirectory ("./Inputs/Files")
    ;



    public String getValue() {
        return value;
    }

    private final String value;
    
    ConstantsString(String value) {
        this.value = value;
    }
}
