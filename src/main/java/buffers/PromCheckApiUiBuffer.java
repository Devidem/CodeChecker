package buffers;

import java.util.List;

/**
 * Буфер для передачи сформированных чек-листов проверки промо-акций товаров между {@link tests.citilink.finalTest.API}
 * и {@link tests.citilink.finalTest.UI}
 */
public class PromCheckApiUiBuffer {

    //Лист с проводимыми проверками (заполняется после обработки входных данных)
    private static List<String[][]> bufferCheckList;


    /**
     * @return Возвращает чек-лист, отредактированный в ходе Api тестирования.
     */
    public static List<String[][]> getBufferCheckList() {
        return bufferCheckList;
    }

    /**
     * Передает чек-лист в буфер.
     * @param checkList Отредактированный в ходе Api тестирования чек-лист.
     */
    public static void setBufferCheckList(List<String[][]> checkList) {
        bufferCheckList = checkList;
    }
}
