package experiments;

/**
 * Обертка для отображения в отчете кода товара при передаче singleCheckList через @DataProvider
 */
public class FanticProdCode {
    private final String [][] singleCheckList;
    public FanticProdCode(String[][] prodCode) {
        this.singleCheckList = prodCode;
    }

    //Забираем код товара для отобрадения в Allure
    @Override
    public String toString() {
        return singleCheckList[1][0];
    }

    public String[][] getSingleCheckList() {
        return singleCheckList;
    }

}
