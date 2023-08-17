package tests.citilink.finalTest;

import buffers.BufferSuiteVar;
import converters.ExArray;
import enums.ConstInt;
import enums.Locators;
import exceptions.myExceptions.MyFileIOException;
import experiments.ApiRequests;
import buffers.BufferDriver;
import experiments.FanticProdCode;
import fabrics.SetDriver;
import interfaces.RetryableHash;
import interfaces.ScreenshootableHash;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.citilink.ProdPage;
import selectors.InputType;
import tests.citilink.finalTest.supportClasses.MyListenerPromCheckingHash;
import tests.citilink.finalTest.supportClasses.MyRetryAnalyzerPromCheckingHash;
import tests.citilink.finalTest.supportClasses.PromCheckApiUiBuffer;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Listeners(MyListenerPromCheckingHash.class)
public class UI implements ScreenshootableHash, RetryableHash {

    //Массив с проводимыми проверками (используется в случаях, когда API тест не проводился)
    private String [][] fullCheckList;

    //Мапа для удобного получения соответствующего драйвера в Listeners
    private final HashMap <String, WebDriver> codeDriver = new HashMap<>();

    //Мапа для удобного получения переменной result (по ее значению мы определяем нужен ли повтор) в RetryAnalyzer
    private final HashMap <String, String> codeResult = new HashMap<>();


    //Проверка акций у товара
    @Step("Проверка промо-акций через UI")
    @Description("Проверка отображения промо-акций на странице товара")
    @Owner("Dmitriy Kazantsev")
    @Test(groups = "UI", dataProvider = "UI_Vider", retryAnalyzer = MyRetryAnalyzerPromCheckingHash.class,
            alwaysRun = true, priority = 2)
    public void promCheckUI(FanticProdCode product) {

        //Забирается драйвер из буффера
        WebDriver driver = BufferDriver.getDriver(BufferSuiteVar.get("browserName"));

        //Запоминается драйвер в мапе код-драйвер
        codeDriver.put(product.toString(), driver);

        //Создается проверочная переменная и запоминается в мапе код-проверочная переменная
        String result = null;
        codeResult.put(product.toString(), result);

        //Блок try-finally для гарантии возвращения WebDriver в буффер
        try{


            //Настраивается драйвер
            SetDriver.standard(driver);

            //Разворачивается фантик
            String[][] singleCheckList = product.getSingleCheckList();

            // Переход на страницу товара
            ProdPage prodPage = new ProdPage(driver);
            String prodCode = singleCheckList[1][0];

            //Используем Api для получения ссылки страницы товара
            prodPage.get(ApiRequests.getProdLink(prodCode));

            // Создаем чеклист для записи итогов проверки
            String[][] resultCheckList = ExArray.clone2d(singleCheckList);

            //Xpath элемента проверки и время ожидания прогрузки страницы
            String checkObjectXpath = Locators.ProductAbout.getXpath();

            //Время ожидания дозагрузки страницы
            int checkLoadTime = 5;
            WebDriverWait checkLoadWait = new WebDriverWait(driver, Duration.ofSeconds(checkLoadTime));

            //Запоминаем установленное значение ImplicitWait
            int impWait = (int) driver.manage().timeouts().getImplicitWaitTimeout().getSeconds();

            //Проверка списка акций у товара
            for (int o = 1; o < singleCheckList[0].length; o++) {

                //Забираем значение и имя акции + вычисляем для нее xpath
                String promoName = singleCheckList[0][o];
                String promoValue = singleCheckList[1][o];
                String xpathPromo = Locators.VarProductPromoMain.getXpathVariable(promoName);

                // Цикл с алгоритмом проверки отдельной акции
                for (int i = 0; i < 2; i++) {

                    // Проверка акций
                    if (Objects.equals(promoValue, "*")) {
                        try {
                            WebElement promoElement = driver.findElement(By.xpath(xpathPromo));
                            resultCheckList[1][o] = "Passed";
                            break;

                        } catch (NoSuchElementException e) {
                            resultCheckList[1][o] = "Failed";
                            codeResult.put(prodCode, "Failed");
                        }

                    } else {
                        try {
                            WebElement promoElement = driver.findElement(By.xpath(xpathPromo));
                            resultCheckList[1][o] = "Failed";
                            codeResult.put(prodCode, "Failed");
                            break;

                        } catch (NoSuchElementException e) {
                            resultCheckList[1][o] = "Passed";
                        }
                    }

                    // Блок проверки загрузки страницы. Запускается однократно и только на первой скидке
                    // Закрывает цикл, если нашел проверочный элемент (checkElement), а если нет, то обновляет страницу и ждет появления
                    // Если не дождался, то вписывает "404" в ячейку и завершает проверку для кода товара (не проверяет остальные акции)
                    // Если дождался, то цикл проверки скидки запускается снова
                    if (o == 1 && i == 0) {
                        try {
                            WebElement checkElement = driver.findElement(By.xpath(checkObjectXpath));
                            break;

                        } catch (NoSuchElementException e) {
                            try {
                                driver.navigate().refresh();
                                checkLoadWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkObjectXpath)));
                                System.out.println("Slow Loading");

                            } catch (TimeoutException ex) {
                                System.out.println("Page 404");
                                resultCheckList[1][o] = "Page 404";
                                codeResult.put(prodCode, "Page 404");
                                throw new RuntimeException("Page 404");
                            }
                        }
                    }

                    // Регулируем ожидания implicitlyWait, чтобы не тратить время на ожидания после 1 шага и
                    // возвращаем прежнее значение после окончания проверки
                    if (o == 1) {
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                    } else if (o == singleCheckList.length - 2) {
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(impWait));
                    }
                }
            }

            System.out.println(Arrays.deepToString(resultCheckList));

            //Прикладываем итоги проверки к тесту и делаем Assert
            Assert.assertNull(codeResult.get(prodCode));


        } finally {
            //Возвращаем драйвер и буффера
            BufferDriver.returnDriver(driver);
        }
    }

    @DataProvider (name = "UI_Vider", parallel = true)
    public Object[][] dataMethodUI()  {

        //Выбираем способ формирования данных в зависимости от того, запускались ли Api тесты и передавали ли они
        //какие-то данные
        if (PromCheckApiUiBuffer.getBufferCheckList() != null && PromCheckApiUiBuffer.getBufferCheckList().size() != 0) {
            //Забираем готовые чеклисты для каждого товара, после прохождения Api тестов
            List<String[][]> afterApiCheckList = PromCheckApiUiBuffer.getBufferCheckList();

            //Задаем размер для массива провайдера
            Object [][] dataObject = new Object[afterApiCheckList.size()][1];

            //Передаем в массив провайдера чеклисты обернутые в Фантик для корректного отображения параметра(код товара)
            //в отчете Allure
            for (int i = 0; i < afterApiCheckList.size(); i++) {
                dataObject [i][0] = new FanticProdCode(afterApiCheckList.get(i));
            }
            return dataObject;

        } else {
            //Получаем полный чек-лист fullCheckList из источника данных
            try {
                setValues(BufferSuiteVar.get("inputType"));
            } catch (MyFileIOException e) {
                throw new RuntimeException(e);
            }

            //Ряд с которого начинают перечисляться коды товаров в массиве
            int startRow = ConstInt.startRow.getValue();

            Object [][] dataObject = new Object[fullCheckList.length-startRow][1] ;

            //Создается чек-лист для одного товара
            for (int i = startRow; i < fullCheckList.length; i++) {

                //Создаем чек лист для товара
                String [][] singleCheckList = new String[2][fullCheckList[0].length];
                System.arraycopy(fullCheckList[0], 0, singleCheckList[0], 0, singleCheckList[0].length);
                System.arraycopy(fullCheckList[i], 0, singleCheckList[1], 0, singleCheckList[0].length);

                //Оборачиваем в Фантик для красивого отображения кода товара в отчете Allure
                FanticProdCode fanticProdCode = new FanticProdCode(singleCheckList);

                //Добавляем фантик в массив
                dataObject [i-startRow][0] = fanticProdCode;

            }
            return dataObject;

        }
    }

    //Получение чеклиста
    public void setValues (String inpType) throws MyFileIOException {

        //Получение чеклиста для дальнейшей проверки
        InputType inputType = new InputType(inpType);
        fullCheckList = inputType.toFinalArray();
    }

    //Закрытие браузера
    @AfterSuite(groups = "UI", alwaysRun = true)
    public void closeDrivers() {
        BufferDriver.closeAllDrivers();
    }

    //Методы интерфейсов
    @Override
    public String getRetryVar(String hashKey) {
        return codeResult.get(hashKey);
    }

    @Override
    public WebDriver getDriver(String hashKey) {
        return codeDriver.get(hashKey);
    }

    //Геттеры
    public String[][] getFullCheckList() {
        return fullCheckList;
    }

}
