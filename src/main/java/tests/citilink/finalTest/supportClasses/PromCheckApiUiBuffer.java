package tests.citilink.finalTest.supportClasses;

import java.util.List;

/**
 * Буффер для передачи сформированных чек-листов проверки промоакций товаров между {@link tests.citilink.finalTest.API}
 * и {@link tests.citilink.finalTest.UI}
 */
public class PromCheckApiUiBuffer {

    //Лист с проводимыми проверками (заполняется после обработки входных данных)
    private static List<String[][]> bufferCheckList;


    public static List<String[][]> getBufferCheckList() {
        return bufferCheckList;
    }
    public static void setBufferCheckList(List<String[][]> checkList) {
        bufferCheckList = checkList;
    }
}
