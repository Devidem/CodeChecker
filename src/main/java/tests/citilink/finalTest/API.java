package tests.citilink.finalTest;

import converters.ExArray;
import enums.ApiLinks;
import enums.JsonRequest;
import exceptions.myExceptions.MyFileIOException;
import experiments.FanticProdCode;
import experiments.ProviderGenerator;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import selectors.InputType;
import tests.citilink.finalTest.supportClasses.ApiSpec;
import tests.citilink.finalTest.supportClasses.PromCheckApiUiBuffer;
import tests.citilink.finalTest.supportClasses.pojos.PojoPromoName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class API {

    //Лист с проводимыми проверками (заполняется после обработки входных данных)
    private String [][] fullCheckList;
    private final List<String[][]> afterApiCheckList = new ArrayList<>();

    //Создаем массив для записи "Passed" и "Failed"
    //Создается для того, чтобы сделать Assert после проверки всех акций
    private String [] result;

    @BeforeSuite(groups = "API")
    @Step("Подготовка чеклиста")
    @Parameters("inputType")
    public void testPrepare(String inputType) throws MyFileIOException {

        //Передаем ссылку на afterApiCheckList в буффер
        PromCheckApiUiBuffer.setBufferCheckList(afterApiCheckList);

        //Получение чеклиста для дальнейшей проверки
        fullCheckList = InputType.toFinalArray(inputType);
    }

    @Step("Проверка промо-акций через Api запрос")
    @Description("Проверка наличия у товаров промо-акций через Api запросы")
    @Owner("Dmitriy Kazantsev")
    @Test(groups = "API", dataProvider = "ApiVider", threadPoolSize = 1, priority = 1)
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

        //Применяем спецификацию
        ApiSpec.json200();

        //Добавляем блок try-finally для гарантии передачи массива проверок в буффер для будущих UI тестов, даже если
        //API тест сломается
        try {

            //Получаем лист Pojo классов с именами скидок
            List<PojoPromoName> promsList =
                    given().
                            body(JsonRequest.PromoListRequest.getBodyVariable(prodCode)).
                            when().
                            post(ApiLinks.SearchProdPromo.getLink()).
                            then().
                            extract().body().jsonPath().getList(linkPathJson, PojoPromoName.class);

            //Преобразуем promsList в строку для упрощения дальнейших проверок
            String proms = promsList.stream().map(x -> x.getTitle()).reduce("", (sum, elem) -> sum + elem);

            for (int i = 1; i < singleCheckList[0].length; i++) {

                //Забираем данные для проверки одной акции у товара
                String promoName = singleCheckList[0][i];
                String promoValue = singleCheckList[1][i];

                //Проверяем соответствие ответа и записываем результаты
                if (Objects.equals(promoValue, "*") && proms.contains(promoName)) {
                    result[0] = "Passed";
                    resultSingleList[1][i] = "Passed";
                } else if (Objects.equals(promoValue, "") && !proms.contains(promoName)) {
                    result[0] = "Passed";
                    resultSingleList[1][i] = "Passed";
                } else {
                    result[1] = "Failed";
                    resultSingleList[1][i] = "Failed";

                    //Преобразуем чек-лист, чтобы проверка UI происходила в соответствии с ответом Api
                    singleCheckList[1][i] = "";
                }
            }
        } finally {
            //Передаем отредактированный чек-лист в коллекцию для дальнейшей проверки в UI тестах
            afterApiCheckList.add(singleCheckList);
        }


        //Прикладываем итоги проверки к тесту и делаем Assert для TestNG
        Allure.addAttachment("Result", (Arrays.deepToString(resultSingleList[0])+ "\n" + Arrays.deepToString(resultSingleList[1])));
        Assert.assertNotEquals(result[1], "Failed");
    }

    @Description("Формирует и передает чек-лист для одного кода товара")
    @DataProvider(name = "ApiVider")
    public Object[][] dataMethodApi() throws MyFileIOException {

        //Генерируем данные для DataProvider
        return ProviderGenerator.getPromData();
    }
}
