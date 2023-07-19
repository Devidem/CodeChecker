package experiments;

/**
 * Обертка для корректного отображения кода товара в отчете Allure,
 * при передаче через @DataProvider сформированных чеклистов
 */
public class FanticProdCode {
    private final String [][] singleCheckList;

    /**
     * Обертка для корректного отображения кода товара в отчете Allure,
     * при передаче через @DataProvider сформированных чеклистов
     */
    public FanticProdCode(String[][] prodCode) {
        this.singleCheckList = prodCode;
    }

    //Переписываем метод для отображения кода товара в Allure отчете
    @Override
    public String toString() {
        return singleCheckList[1][0];
    }

    /**
     * Возвращает обернутый массив
     */
    public String[][] getSingleCheckList() {
        return singleCheckList;
    }

}
