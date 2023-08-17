package buffers;

import experiments.SuiteReader;

import java.util.HashMap;

/**
 * Буффер для сохранения переменных из Suite
 * Используется для применения параметров в DataProvider
 */
public class BufferSuiteVar {

    //Мапа для параметризации DataProvider через Suite переменные
    private static final HashMap<String, String> suiteVariables = new HashMap<>();

    /**
     * Возвращает значение параметра
     * @param varName Имя параметра
     */
    public static String get(String varName) {
        return suiteVariables.get(varName);
    }

    /**
     * Записывает все параметры Suite в буффер
     */
    public static void read() {
        suiteVariables.putAll(SuiteReader.getAllParameters());
    }

}
