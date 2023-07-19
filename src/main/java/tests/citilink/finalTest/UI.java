package tests.citilink.finalTest;

import converters.ArrayEx;
import enums.ConstInt;
import enums.Locators;
import exceptions.myExceptions.MyFileIOException;
import experiments.ApiRequests;
import experiments.BufferDriver;
import experiments.FanticProdCode;
import experiments.SuiteReader;
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

    //Лист с проводимыми проверками (заполняется после обработки входных данных)
    private String [][] fullCheckList;

    private final HashMap <String, WebDriver> codeDriver = new HashMap<>();
    private final HashMap <String, String> codeResult = new HashMap<>();
    private final HashMap <String, String> suiteVariables = new HashMap<>();;

    @BeforeSuite (groups = "UI")
    public void getSuiteVariables () {
        suiteVariables.putAll(SuiteReader.getAllParameters());
    }
    //Проверка акций у товара
    @Step("Проверка промо-акций через UI")
    @Description("Проверка отображения промо-акций на странице товара")
    @Owner("Dmitriy Kazantsev")
    @Test(groups = "UI", dataProvider = "UI_Vider", retryAnalyzer = MyRetryAnalyzerPromCheckingHash.class,
            dependsOnGroups = "API", alwaysRun = true)
    public void promCheckUI(FanticProdCode product) {

        //Забираем драйвер и буффера
        WebDriver driver = BufferDriver.getChrome();

        //Запоминаем в мапе код-драйвер
        codeDriver.put(product.toString(), driver);

        //Создаем проверочную переменную и запоминаем ее в HashMap код-проверочная переменная
        String result = null;
        codeResult.put(product.toString(), result);


        try{


            //Настраиваем драйвер
            SetDriver.standard(driver);

            //Разворачиваем фантик
            String[][] singleCheckList = product.getSingleCheckList();

            // Переход на страницу товара
            ProdPage prodPage = new ProdPage(driver);
            String prodCode = singleCheckList[1][0];

            //Используем Api для получения ссылки страницы товара
            prodPage.get(ApiRequests.getProdLink(prodCode));

            // Создаем чеклист для записи итогов проверки
            String[][] resultCheckList = ArrayEx.clone2d(singleCheckList);

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
                    // Закрывает цикл, если нашел проверочный элемент (checkElement), а если нет, то ждет появления
                    // Если не дождался, то вписывает "404" в ячейку и завершает проверку для кода товара (не проверяет остальные акции)
                    // Если дождался, то цикл проверки скидки запускается снова
                    if (o == 1 && i == 0) {
                        try {
                            WebElement checkElement = driver.findElement(By.xpath(checkObjectXpath));
                            break;

                        } catch (NoSuchElementException e) {
                            try {
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
            BufferDriver.returnChrome(driver);
        }
    }

    @DataProvider (name = "UI_Vider", parallel = true)
    public Object[][] dataMethodUI()  {

        //Выбираем способ формирования данных в зависимости от того, запускались ли Api тесты
        if (PromCheckApiUiBuffer.getBufferCheckList() != null) {
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
            //Получаем полный чек-лист fullCheckList из источникаданных
            try {
                setValues(suiteVariables.get("inputType"));
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
    @AfterSuite(groups = "UI")
    public void closeDrivers() {
        BufferDriver.closeAllChrome();
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
