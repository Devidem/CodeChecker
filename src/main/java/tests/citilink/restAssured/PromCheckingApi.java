package tests.citilink.restAssured;

import converters.ExArray;
import enums.ApiLinks;
import enums.ConstInt;
import enums.JsonRequest;
import exceptions.myExceptions.MyFileIOException;
import experiments.FanticProdCode;
import experiments.FileManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import selectors.InputType;
import tests.citilink.finalTest.supportClasses.pojos.PojoPromoName;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class PromCheckingApi {

    //Лист с проводимыми проверками (заполняется после обработки входных данных)
    private String [][] fullCheckList;

    //Создаем массив для записи "Passed" и "Failed"
    //Создается для того, чтобы сделать Assert после проверки всех элементов
    private String [] result;

    @BeforeSuite(groups = "apiSingle")
    @Step ("Подготовка чеклиста")
    @Parameters("inputType")
    public void testPrepare(String inputType) throws MyFileIOException {

        // Копирование categories.json в allure-results
        FileManager.copyCategories();

        //Получение чеклиста для дальнейшей проверки
        fullCheckList = InputType.toFinalArray(inputType);
    }

    @Step("Проверка промо-акций через Api запрос")
    @Description("Проверка наличия у товаров промо-акций через Api запросы")
    @Owner("Dmitriy Kazantsev")
    @Test(groups = "apiSingle", dataProvider = "ApiVider")
    public void apiPromsChecking(FanticProdCode productCode) {
        //Разворачиваем из фантика чек-лист для одного кода товара
        String[][] singleCheckList = productCode.getSingleCheckList();

        //Обновляем result
        result = new String[2];

        //Код товара
        String prodCode = singleCheckList[1][0];

        //Клон singleCheckList для записи результатов проверок
        String [][] resultSingleList = ExArray.clone2d(singleCheckList);

        //Путь к объекту с списком акций
        String linkPathJson = "data.product.labels";

        //Получаем коллекцию Pojo классов с именами скидок
        List<PojoPromoName> promsList =
                given().
                        body(JsonRequest.PromoListRequest.getBodyVariable(prodCode)).
                        contentType(ContentType.JSON).
                        when().
                        post(ApiLinks.SearchProdPromo.getLink()).
                        then().
                        statusCode(200).
                        extract().body().jsonPath().getList(linkPathJson, PojoPromoName.class);

        //Преобразуем promsList в строку для упрощения дальнейших проверок
        String proms = promsList.stream().map(x->x.getTitle()).reduce("", (sum, elem)->sum+elem);

        for (int i = 1; i < singleCheckList[0].length; i++) {

            //Забираем данные для проверки одной акции у товара
            String promoName = singleCheckList[0][i];
            String promoValue = singleCheckList[1][i];

            //Проверяем соответствие ответа и записываем результаты
            if (Objects.equals(promoValue, "*") && proms.contains(promoName)) {
                result[0] = "Passed";
                resultSingleList [1][i] = "Passed";
            } else if (Objects.equals(promoValue, "") && !proms.contains(promoName)) {
                result[0] = "Passed";
                resultSingleList [1][i] = "Passed";
            } else {
                result[1] = "Failed";
                resultSingleList [1][i] = "Failed";
            }
        }

        //Прикладываем итоги проверки к тесту и делаем Assert для TestNG
        Allure.addAttachment("Result", (Arrays.deepToString(resultSingleList[0])+ "\n" + Arrays.deepToString(resultSingleList[1])));
        Assert.assertNotEquals(result[1], "Failed");
    }

    @Description("Формирует и передает чек-лист для одного кода товара")
    @DataProvider(name = "ApiVider")
    public Object[][] dataMethod()  {

        //Ряд с которого начинают перечисляться коды товаров
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

    //Геттеры
    public String[][] getFullCheckList() {
        return fullCheckList;
    }

    public String[] getResult() {
        return result;
    }
}
