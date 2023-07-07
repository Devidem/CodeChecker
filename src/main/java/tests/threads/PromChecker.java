package tests.threads;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.NoPage;
import pages.citilink.ProdPage;
import selectors.Browsers;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PromChecker {
    private String browserName;
    private String siteName;
    private String[][] checkList;
    private String[][] resultList;
    private int startRow;
    private int threadsNumber;
    public PromChecker(String browserName, String siteName, String[][] checkList, String[][] resultList, int startRow, int threadsNumber) {
        this.browserName = browserName;
        this.siteName = siteName;
        this.checkList = checkList;
        this.resultList = resultList;
        this.startRow = startRow;
        this.threadsNumber = threadsNumber;
    }
    private CountDownLatch countDownLatch;
    private ExecutorService executorService;

    /**
     * Производит многопоточную проверку промоакций с 3 попытками в случае падения.
     * Учитывает ограничение по количеству потоков, а также оптимизировам с помощью CountDownLatch.
     * @return Итоги проверки акций для товаров из {@link #checkList}
     */
    public String[][] multiCheck() {
        countDownLatch = new CountDownLatch(resultList.length - startRow);
        executorService = Executors.newFixedThreadPool(threadsNumber);

        for (int i = 0; i < resultList.length - startRow; i++) {
            int codeRow = i + startRow;

            //Берем код товара
            String prodCode = checkList[codeRow][0];

            // Создаем лист с акциями для текущего кода
            String[][] promsList = new String[2][checkList[0].length - 1];
            System.arraycopy(resultList[0], 1, promsList[0], 0, promsList[0].length);
            System.arraycopy(resultList[codeRow], 1, promsList[1], 0, promsList[0].length);

            checkThread thread = new checkThread(promsList, prodCode, codeRow);
            executorService.submit(thread);
        }

        try {
            countDownLatch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.resultList;
    }


    //Класс потока тестирования промоакций одного кода товара
    private class checkThread extends Thread {
        String[][] promsList;
        String prodCode;
        int codeRow;
        public checkThread(String[][] promsList, String prodCode, int codeRow) {
            this.promsList = promsList;
            this.prodCode = prodCode;
            this.codeRow = codeRow;
        }

        @Override
        public void run() {
            // Защита от случайного падения - перезапускает 3 раза, если тест падает с ошибкой
            try {
                for (int i = 0; i < 3; i++) {
                    try {
                        checkMethod();
                        break;
                    } catch (Exception e) {
                        if (i==2) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            //При падении проверки прописываем UNCHECKED
            } catch (Exception ignored) {
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
            //Выбираем и запускаем браузер
            Browsers browsers = new Browsers(browserName);
            WebDriver driver = browsers.start();
            driver.manage().window().maximize();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

            WebDriverWait wait;
            wait = new WebDriverWait(driver, Duration.ofSeconds(2));

            //Идем на сайт, но с игнором TimeoutException
            NoPage noPage = new NoPage(driver);
            noPage.get(siteName);

            //Создаем ProdPage, в котором есть все нужные методы для работы со страничкой продукта
            ProdPage prodPage = new ProdPage(driver, wait);

            //Переход на страничку продукта
            prodPage.enterSearch(prodCode);
            prodPage.clickSearchResult(prodCode);

            System.arraycopy(prodPage.checkProms(promsList), 0, resultList[codeRow], 1, promsList[0].length);
            driver.close();

        }
    }
}
