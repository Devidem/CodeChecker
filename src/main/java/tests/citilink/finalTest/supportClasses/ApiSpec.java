package tests.citilink.finalTest.supportClasses;

import enums.ConstString;
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
    public static RequestSpecification reqSpec(){
        return new RequestSpecBuilder().
                setContentType(ContentType.JSON).
                build();
    }
    public static ResponseSpecification respSpec(){
        return new ResponseSpecBuilder().
                expectStatusCode(200).
                build();
    }

    /**
     * Устанавливает ContentType.JSON и expectStatusCode200
     */
    public static void json200 (){
        RestAssured.requestSpecification = reqSpec();
        RestAssured.responseSpecification = respSpec();
    }

}
