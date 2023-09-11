package tests.citilink.javaSelenOnly.old.multithread;

import buffers.BufferDriver;
import exceptions.myExceptions.MyInputParamException;
import fabrics.old.SetDriverOld;
import org.openqa.selenium.WebDriver;
import pages.citilink.old.MainPageOld;
import pages.citilink.old.NoPageOld;
import pages.citilink.old.ProdPageOld;

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
    //Объявляем счетчик, чтобы закрыть executorService, после выполнения всех проверок
    private CountDownLatch countDownLatch;

    /**
     * Производит многопоточную проверку промо-акций с 3 попытками в случае падения.
     * Учитывает ограничение по количеству потоков, а также оптимизирован с помощью CountDownLatch.
     */
    public void multiCheck() {
        //Устанавливаем счетчик равный количеству кодов товаров
        countDownLatch = new CountDownLatch(resultList.length - startRow);
        //Задаем указанное количество потоков
        executorService = Executors.newFixedThreadPool(threadsNumber);

        //Блок try-finally для гарантии закрытия вебдрайверов
        try {
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
            countDownLatch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        } finally {
            //Закрываем все вебдрайверы
            BufferDriver.closeAllDrivers();
        }
    }

    //Поток для тестирования промо-акций одного кода товара
    private class CheckThread extends Thread {
        public CheckThread(String[][] promsList, String prodCode, int codeRow) {
            this.promsList = promsList;
            this.prodCode = prodCode;
            this.codeRow = codeRow;
        }

        private String[][] promsList;
        private String prodCode;
        private int codeRow;

        @Override
        public void run() {
            // Защита от падения - перезапускает тест repeatsNum раз
            int repeatsNum = 3;
            try {
                for (int i = 0; i < repeatsNum; i++) {
                    try {
                        checkMethod();
                        break;
                        //Игнорируем Exception, кроме случая, когда это последний повтор
                    } catch (Exception e) {
                        if (i == repeatsNum - 1) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                //При падении проверки прописываем UNCHECKED в полях акций
            } catch (Exception e) {
                System.out.println("Failed Check");
                for (int i = 0; i < promsList[0].length; i++) {
                    resultList[codeRow][i + 1] = "UNCHECKED";
                }

            } finally {
                countDownLatch.countDown();
            }
        }

        //Метод проверки промо-акций для одного кода товара
        //Вынесен отдельно, чтобы сделать удобную настройку в run () методе
        private void checkMethod() throws MyInputParamException {

            //Выбор и запуск браузера + настройка
            WebDriver driver = BufferDriver.getDriver(browserName);
            SetDriverOld.standard(driver);

            //try-finally для обязательного закрытия драйвера в случае возникновения ошибок
            try {
                //Пропускаем переход на сайт, если мы уже на нем
                if (!driver.getCurrentUrl().contains(siteName)) {
                    //Переход на сайт, но с игнором TimeoutException
                    NoPageOld noPage = new NoPageOld(driver);
                    noPage.get(siteName);
                }

                //Переход на страничку продукта
                MainPageOld mainPage = new MainPageOld(driver);
                mainPage.enterSearch(prodCode);
                mainPage.clickSearchResult(prodCode);

                //Проверка акций для одного товара и сохранение результата
                ProdPageOld prodPage = new ProdPageOld(driver);
                System.arraycopy(prodPage.checkProms(promsList), 0, resultList[codeRow], 1, promsList[0].length);
            } finally {
                //Возвращаем драйвер в буфер
                BufferDriver.returnDriver(driver);
            }
        }
    }
}
