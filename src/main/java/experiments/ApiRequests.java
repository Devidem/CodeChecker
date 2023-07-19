package experiments;

import enums.ApiLinks;
import enums.ConstString;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

/**
 * Набор запросов API с использованием RestAssured
 */
public class ApiRequests {
    /**
     * Получение ссылки страницы товара
     * @param prodCode Код товара
     * @return Полная ссылка на страницу товара
     */
    public static String getProdLink(String prodCode) {
        String linkPathJson = "products[0].link_url";
        String responseLink =
                given().
                        when().
                        contentType(ContentType.JSON).
                        get(ApiLinks.SearchProdLink.getLinkVariable(prodCode)).
                        then().
                        statusCode(200).
                        extract().body().jsonPath().get(linkPathJson);

        String prodLink = (ConstString.CitilinkAdress.getValue() + responseLink).replace(".ru//", ".ru/");
        return prodLink;
    }
}
