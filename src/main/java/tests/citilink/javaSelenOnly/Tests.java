package tests.citilink.javaSelenOnly;

import buffers.BufferDriver;
import converters.ExArray;
import enums.ConstString;
import exceptions.myExceptions.MyFileIOException;
import exceptions.myExceptions.MyInputParamException;
import pages.citilink.Navigator;
import selectors.InputType;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Набор тестов
 */
public class Tests {
    /**
     * Проверяет наличие отображения акций на странице кода товара (однопоточный вариант).
     * В качестве входных данных используется .xls файл, расположенные в папке проекта ./Inputs/Excel или PostgreSql.
     * Результат проверки записывается в .xls файл по адресу ./Outputs/Excel - в имени указывается дата и результат.
     * @param inputType Тип входных данных - Excel, Sql.
     * @param browserName Название браузера - Chrome, Firefox.
     */
    public void promCheckingSingleThread(String inputType, String browserName) throws MyFileIOException, IOException, MyInputParamException {

        //Получаем чек-лист
        String[][] checkList = InputType.toFinalArray(inputType);

        //Разбиваем на одиночные чек-листы
        Queue<String[][]> singleCheckLists = new LinkedList<>();
        ExArray.separateTableQueue(checkList, singleCheckLists);

        //Создаем коллекцию для хранения результатов проверок одиночных чек-листов
        Queue<String[][]> resultCollection = new LinkedList<>();

        //Создаем класс для проведения проверки
        Navigator navigator = new Navigator();

        //Блок try-finally для гарантии закрытия браузера
        try {
            //Переходим на сайт
            navigator
                    .openBrowser(browserName)
                    .configBrowser().standard()
                    .then()
                    .onNoPage()
                    .get(ConstString.CitilinkAdress.getValue());

            //Проверяем одиночные чек-листы
            while (!singleCheckLists.isEmpty()) {

                //Забираем чек-лист из коллекции и запоминаем код товара
                String[][] singleCheckList = singleCheckLists.remove();
                String prodCode = singleCheckList[1][0];

                //Переходим на страницу товара и делаем проверку
                navigator
                        .onMainPage()
                        .enterSearch(prodCode)
                        .clickSearchResult(prodCode)
                        .then().onProdPage()
                        .check().promoActions(singleCheckList, resultCollection);
            }
        } finally {
            //Закрываем браузер
            navigator
                    .closeBrowser();
        }

        //Объединяем результаты проверку в одну таблицу
        String[][] result = new String[resultCollection.size() + 1][resultCollection.element()[0].length];
        ExArray.unionTablesQueue(resultCollection, result);

        //Записываем результаты в файл
        ExArray.toExcelTest(result);
    }

    /**
     * Проверяет наличие отображения акций на странице кода товара (многопоточный вариант).
     * В качестве входных данных используется .xls файл, расположенные в папке проекта ./Inputs/Excel или PostgreSql.
     * Результат проверки записывается в .xls файл по адресу ./Outputs/Excel - в имени указывается дата и результат.
     * @param inputType Тип входных данных - Excel, Sql.
     * @param browserName Название браузера - Chrome, Firefox.
     * @param threadCount Количество потоков
     */
    public void promCheckingMultiThread(String inputType, String browserName, int threadCount) throws MyFileIOException, IOException, MyInputParamException, InterruptedException {

        //Получаем чек-лист
        String[][] checkList = InputType.toFinalArray(inputType);

        //Разбиваем на одиночные чек-листы
        Queue<String[][]> singleCheckLists = new LinkedList<>();
        ExArray.separateTableQueue(checkList, singleCheckLists);

        //Создаем коллекцию для хранения результатов проверок одиночных чек-листов
        Queue<String[][]> resultCollection = new LinkedList<>();

        CountDownLatch countDownLatch = new CountDownLatch(singleCheckLists.size());
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        while (!singleCheckLists.isEmpty()) {
            executorService.submit(new promCheckingThread(inputType, browserName, singleCheckLists.remove(), resultCollection, countDownLatch));
        }

        countDownLatch.await();
        executorService.shutdown();
        BufferDriver.closeAllDrivers();

        //Объединяем результаты проверку в одну таблицу
        String[][] result = new String[resultCollection.size() + 1][resultCollection.element()[0].length];
        ExArray.unionTablesQueue(resultCollection, result);

        //Записываем результаты в файл
        ExArray.toExcelTest(result);
    }

    /**
     * Класс для запуска потока одиночной проверки промо-акций на странице товара в методе {@link #promCheckingMultiThread(String, String, int)}
     */
    private class promCheckingThread extends Thread {
        private String inputType;
        private String browserName;
        private String[][] singleCheckList;
        private Queue<String[][]> resultCollection;
        private CountDownLatch countDownLatch;

        public promCheckingThread(String inputType, String browserName, String[][] singleCheckList, Queue<String[][]> resultCollection, CountDownLatch countDownLatch) {
            this.inputType = inputType;
            this.browserName = browserName;
            this.singleCheckList = singleCheckList;
            this.resultCollection = resultCollection;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            //Запускаем проверку с 2 повторами в случае падения
            for (int i = 0; true; i++) {
                try {
                    test();
                    break;
                } catch (Exception e) {
                    if (i==2) {
                        countDownLatch.countDown();
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        /**
         * Метод одиночной проверки промо-акций на странице товара
         */
        public void test() {

            //Запоминаем ссылку на сайт
            String checkLink = ConstString.CitilinkAdress.getValue();

            //Создаем класс для проведения проверки
            Navigator navigator = new Navigator();

            //Блок try-finally для гарантии закрытия браузера
            try {
                Map<String, Boolean> booleanResults = new HashMap<>();

                //Получаем вебдрайвер и проверяем текущий URL
                navigator
                        .openBrowser(browserName)
                        .configBrowser().standard()
                        .then()
                        .onNoPage()
                        .check().currentUrl(checkLink, booleanResults);

                //Если не на сайте ситилинка, то переходим на него
                if (!booleanResults.get(checkLink)) {
                    navigator
                            .onNoPage()
                            .get(checkLink);
                }

                //Переходим на страницу товара и делаем проверку
                String prodCode = singleCheckList[1][0];
                navigator
                        .onMainPage()
                        .enterSearch(prodCode)
                        .clickSearchResult(prodCode)
                        .then().onProdPage()
                        .check().promoActions(singleCheckList, resultCollection)
                        .and()
                        .then()
                        .returnBrowser();

                countDownLatch.countDown();

            //Набор обязательных действий на случай падения теста
            //В данном случае сделано не через finally намеренно для демонстрации использования Navigator в тесте (больше шагов подряд!)
            } catch (Exception e){
                //Добавляем зафейленный результат
                singleCheckList[1][1] = "Failed";
                resultCollection.add(singleCheckList);
                //Возвращаем браузер в буфер
                navigator.returnBrowser();
                throw new RuntimeException(e);
            }
        }
    }
}
