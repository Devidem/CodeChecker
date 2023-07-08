package tests.threads;

import fabrics.FabricDriverSets;
import org.openqa.selenium.WebDriver;
import pages.NoPage;
import pages.citilink.MainPage;
import pages.citilink.ProdPage;
import selectors.Browsers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс для многопоточной проверки акций на страницах товаров
 */
public class MultiPromCheck {
    private final String browserName;
    private final String siteName;
    private final String[][] checkList;
    private final String[][] resultList;
    private final int startRow;
    private final int threadsNumber;
    public MultiPromCheck(String browserName, String siteName, String[][] checkList, String[][] resultList, int startRow, int threadsNumber) {
        this.browserName = browserName;
        this.siteName = siteName;
        this.checkList = checkList;
        this.resultList = resultList;
        this.startRow = startRow;
        this.threadsNumber = threadsNumber;
    }
    //Объявляем ExecutorService, чтобы регулировать и оптимизировать количество одновременных потоков
    ExecutorService executorService;
    //Объявялем счетчик, чтобы закрыть executorService, после выполнения всех проверок
    private CountDownLatch countDownLatch;

    /**
     * Производит многопоточную проверку промоакций с 3 попытками в случае падения.
     * Учитывает ограничение по количеству потоков, а также оптимизировам с помощью CountDownLatch.
     */
    public void multiCheck() {
        //Устанавливаем счетчик равный количеству кодов товаров
        countDownLatch = new CountDownLatch(resultList.length - startRow);
        //Задаем указанное количество потоков
        executorService = Executors.newFixedThreadPool(threadsNumber);

        //Формируем данные для проверки одного кода товара и отправляем их проверочному потоку checkThread
        for (int i = 0; i < resultList.length - startRow; i++) {
            int codeRow = i + startRow;

            //Берем код товара
            String prodCode = checkList[codeRow][0];

            // Создаем лист с акциями для текущего кода
            String[][] promsList = new String[2][checkList[0].length - 1];
            System.arraycopy(resultList[0], 1, promsList[0], 0, promsList[0].length);
            System.arraycopy(resultList[codeRow], 1, promsList[1], 0, promsList[0].length);

            //Запускаем поток проверки
            CheckThread checkThread = new CheckThread(promsList, prodCode, codeRow);
            executorService.submit(checkThread);
        }

        //Ждем окончание проверок, чтобы закрыть executorService
        try {
            countDownLatch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Поток для тестирования промоакций одного кода товара
    private class CheckThread extends Thread {
        String[][] promsList;
        String prodCode;
        int codeRow;
        public CheckThread(String[][] promsList, String prodCode, int codeRow) {
            this.promsList = promsList;
            this.prodCode = prodCode;
            this.codeRow = codeRow;
        }

        @Override
        public void run() {
            // Защита от падения - перезапускает тест repeatsNum раз
            int repeatsNum = 3;
            try {
                for (int i = 0; i < repeatsNum; i++) {
                    try {
                        checkMethod();
                        break;
                    } catch (Exception e) {
                        if (i==repeatsNum - 1) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            //При падении проверки прописываем UNCHECKED в полях акций
            } catch (Exception e) {
                System.out.println("Failed Check");

                for (int i = 0; i < promsList[0].length; i++) {
                    resultList[codeRow][i+1] = "UNCHECKED";
                }
            }
            countDownLatch.countDown();
        }

        //Метод проверки промоакций для одного кода товара
        //Вынесен отдельно, чтобы сделать удобную настройку в run () методе
        public void checkMethod() {
            //Выбор и запуск браузера
            Browsers browsers = new Browsers(browserName);
            WebDriver driver = FabricDriverSets.standard(browsers.start());

            //try-finally для обязательного закрытия драйвера в случае возникновения ошибок
            try {
                //Переход на сайт, но с игнором TimeoutException
                NoPage noPage = new NoPage(driver);
                noPage.get(siteName);

                //Переход на страничку продукта
                MainPage mainPage = new MainPage(driver);
                mainPage.enterSearch(prodCode);
                mainPage.clickSearchResult(prodCode);

                //Проверка акций для одного товара и сохранение результата
                ProdPage prodPage = new ProdPage(driver);
                System.arraycopy(prodPage.checkProms(promsList), 0, resultList[codeRow], 1, promsList[0].length);
            } finally {
                driver.close();
            }
        }
    }
}
