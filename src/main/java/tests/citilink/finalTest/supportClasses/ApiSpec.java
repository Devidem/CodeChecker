package tests.citilink.finalTest.supportClasses;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Набор спецификаций для Api тестов
 */
public class ApiSpec {

    /**
     * @return ResponseSpecification с параметром ContentType.JSON
     */
    public static RequestSpecification reqJson() {
        return new RequestSpecBuilder().
                setContentType(ContentType.JSON).
                build();
    }

    /**
     * @return ResponseSpecification с ожиданием 200 кода
     */
    public static ResponseSpecification resp200() {
        return new ResponseSpecBuilder().
                expectStatusCode(200).
                build();
    }

    /**
     * Устанавливает спецификации ContentType.JSON и expectStatusCode200
     */
    public static void json200 () {
        RestAssured.requestSpecification = reqJson();
        RestAssured.responseSpecification = resp200();
    }

}
