package pages.citilink.builds;

import converters.ExArray;
import enums.Locators;
import fabrics.old.SetDriverOld;
import org.openqa.selenium.*;
import pages.citilink.Navigator;
import pages.citilink.pageObjects.MainPanel;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.Queue;


/**
 * Build класс для страницы товара
 */
public abstract class BuildProdPage<T> extends BuildCitiPage<T> {
    public BuildProdPage(WebDriver driver, Navigator navigator) {
        super(driver, navigator);
    }

    /**
     * Проверяет отображение акций на странице товара.
     * @return Возвращает одномерный String массив с результатами проверок.
     */
    public T checkProms(String [][] singleCheckList, Queue<String [][]> resultMap) {

        //Записываем код товара
        String prodCode = singleCheckList[1][0];

        //Создаем чек-лист для записи итогов проверки
        String[][] resultCheckList = ExArray.clone2d(singleCheckList);

        //Устанавливаем добавочное время ожидания для повторной попытки загрузки страницы
        int checkLoadTime = 3;

        //Xpath проверочного элемента для повторной попытки загрузки страницы
        String checkObjectXpath = Locators.ProdPageBasket.getXpath();

        //Записываем начальное значение PageLoadTimeout и ImplicitWait
        int startTimeout = SetDriverOld.getPageOut(driver);
        int startImplicit = SetDriverOld.getImpOut(driver);

        //Блок try-finally для гарантии возвращения WebDriver в буфер в изначальном состоянии
        try{

            //Настраиваем драйвер
            SetDriverOld.standardManual(driver, 2, 3);

            //Проверяем список акций у товара
            for (int o = 1; o < singleCheckList[0].length; o++) {

                //Начиная со второй скидки отключаем implicitlyWait, чтобы не тратить время на ожидание.
                //Без использования на первой скидке, могут быть проблемы с обнаружением элементов
                //сразу после загрузки страницы.
                if (o==2) {
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                }

                //Забираем значение и имя акции + вычисляем для нее xpath
                String promoName = singleCheckList[0][o];
                String promoValue = singleCheckList[1][o];
                String xpathPromo = Locators.VarProductPromoMain.getXpathVariable(promoName);

                //Цикл с алгоритмом проверки отдельной акции
                for (int i = 0; i < 2; i++) {

                    //Проверка акций
                    if (Objects.equals(promoValue, "*")) {
                        try {
                            WebElement promoElement = driver.findElement(By.xpath(xpathPromo));
                            resultCheckList[1][o] = "Passed";
                            break;

                        } catch (NoSuchElementException e) {
                            resultCheckList[1][o] = "Failed";
                        }

                    } else {
                        try {
                            WebElement promoElement = driver.findElement(By.xpath(xpathPromo));
                            resultCheckList[1][o] = "Failed";
                            break;

                        } catch (NoSuchElementException e) {
                            resultCheckList[1][o] = "Passed";
                        }
                    }

                    //Останавливаем запуск дополнительной проверки начиная со второй промо-акции
                    if (o > 1) {
                        break;
                    }

                    // Блок проверки загрузки страницы. Запускается однократно и только на первой скидке.
                    // Закрывает цикл, если нашел проверочный элемент (checkElement), а если нет, то обновляет страницу
                    // с увеличенным таймаутом и ищет его снова
                    // Если не нашел, то вписывает "Page404" в ячейку и завершает проверку для кода товара (не проверяет остальные акции)
                    // Если нашел, то цикл проверки скидки запускается снова
                    if (i == 0) {
                        try {
                            WebElement checkElement = driver.findElement(By.xpath(checkObjectXpath));
                            break;

                        } catch (NoSuchElementException e) {

                            try {
                                System.out.println("Start Refresh");
                                //Обновляем страницу с увеличенным pageLoadTimeout
                                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(SetDriverOld.getPageOut(driver) + checkLoadTime));
                                refresh();

                                //Проверяем наличие проверочного объекта после рефреша
                                WebElement checkElement = driver.findElement(By.xpath(checkObjectXpath));

                                //Обновляем значение проверочной переменной если смогли нормально загрузить страницу
                                System.out.println("Slow Loading");


                            } catch (NoSuchElementException | TimeoutException ex) {
                                System.out.println("Page 404");
                                resultCheckList[1][o] = "Page 404";
                            }
                        }
                    }
                }
            }

            //Выводим входные данные и результат проверки в консоль
            System.out.println(Arrays.deepToString(singleCheckList));
            System.out.println(Arrays.deepToString(resultCheckList));

        } finally {
            //Возвращаем драйвер в изначальном состоянии и добавляем результат в переданную мапу
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(startTimeout));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(startImplicit));
            resultMap.add(resultCheckList);
        }
        return (T) this;
    }

    /**
     * Возвращает PageObject {@link MainPanel} для данной страницы
     */
    //Данный метод сделан для демонстрации того, как можно использовать PageObject в текущей реализации
    public MainPanel<T> inMainPanel() {
        return new MainPanel<T>(getDriver(), (T)this);
    }
}




