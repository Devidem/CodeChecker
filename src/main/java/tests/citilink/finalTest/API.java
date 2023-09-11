package tests.citilink.finalTest;

import buffers.BufferSuiteVar;
import buffers.PromCheckApiUiBuffer;
import converters.ExArray;
import enums.ApiLinks;
import enums.ConstInt;
import enums.JsonRequest;
import exceptions.myExceptions.MyFileIOException;
import exceptions.myExceptions.MyInputParamException;
import experiments.FanticProdCode;
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
import tests.citilink.finalTest.supportClasses.pojos.PojoPromoName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class API {

    //Массив для работы с ApiUiBuffer
    private final List<String[][]> afterApiCheckList = new ArrayList<>();

    @BeforeSuite(groups = "API")
    @Step("Подготовка чек-листа")
    @Parameters("inputType")
    public void testPrepare(String inputType) {

        //Передаем ссылку на afterApiCheckList в ApiUiBuffer
        PromCheckApiUiBuffer.setBufferCheckList(afterApiCheckList);
    }

    @Step("Проверка промо-акций через Api запрос")
    @Description("Проверка наличия у товаров промо-акций через Api запросы")
    @Owner("Dmitriy Kazantsev")
    @Test(groups = "API", dataProvider = "ApiVider", threadPoolSize = 1, priority = 1)
    public void apiPromsChecking(FanticProdCode productCode) {

        //Разворачиваем из фантика чек-лист для одного кода товара
        String[][] singleCheckList = productCode.getSingleCheckList();

        //Создаем переменную для того, чтобы сделать Assert после проверки всех акций
        String result = null;

        //Код товара
        String prodCode = singleCheckList[1][0];

        //Клон singleCheckList для записи результатов проверок
        String [][] resultSingleList = ExArray.clone2d(singleCheckList);

        //Клон singleCheckList для приложения входных данных к отчету
        String [][] inputList = ExArray.clone2d(singleCheckList);

        //Путь к объекту со списком акций
        String linkPathJson = "data.product.labels";

        //Применяем спецификацию
        ApiSpec.json200();

        //Добавляем блок try-finally для гарантии передачи массива проверок в буфер для будущих UI тестов, даже если
        //API тест сломается
        try {

            //Получаем лист Pojo классов с именами скидок
            List<PojoPromoName> promsList =
                    given().
                            body(JsonRequest.VarPromoListRequest.getBodyVariable(prodCode)).
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
                    resultSingleList[1][i] = "Passed";
                } else if (Objects.equals(promoValue, "") && !proms.contains(promoName)) {
                    resultSingleList[1][i] = "Passed";
                } else {
                    result = "Failed";
                    resultSingleList[1][i] = "Failed";

                    //Преобразуем чек-лист, чтобы проверка UI происходила в соответствии с ответами Api
                    if (!proms.contains(promoName)) {
                        singleCheckList[1][i] = "";
                    } else {
                        singleCheckList[1][i] = "*";
                    }
                }
            }
        } finally {
            //Передаем отредактированный чек-лист в коллекцию для дальнейшей проверки в UI тестах
            afterApiCheckList.add(singleCheckList);
        }

        //Прикладываем итоги проверки к тесту и делаем Assert для TestNG
        Allure.addAttachment("Input", (Arrays.deepToString(inputList[0])+ "\n" + Arrays.deepToString(inputList[1])));
        Allure.addAttachment("Result", (Arrays.deepToString(resultSingleList[0])+ "\n" + Arrays.deepToString(resultSingleList[1])));
        Assert.assertNotEquals(result, "Failed");
    }

    @Description("Формирует и передает чек-лист для одного кода товара")
    @DataProvider(name = "ApiVider")
    public Object[][] dataMethodApi() throws MyFileIOException, IOException, MyInputParamException {

        //Забираем значение параметры из Сьюита
        String inpType = BufferSuiteVar.get("inputType");

        //Получаем чек-лист для дальнейшей проверки
        String [][] fullCheckList;
        fullCheckList = InputType.toFinalArray(inpType);

        //Ряд с которого начинают перечисляться коды товаров в массиве
        int startRow = ConstInt.startRow.getValue();

        //Создаем список для помещения "обернутых" проверочных массивов
        Object [][] dataObject = new Object[fullCheckList.length-startRow][1] ;

        //Создаем чек-лист для одного товара
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
