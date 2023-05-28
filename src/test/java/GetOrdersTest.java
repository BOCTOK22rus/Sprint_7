import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static TestData.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersTest {

    private static final String pathFile = "src/test/java/TestData/resources/TwoColors.json";

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Check Get All Orders")
    @Description("Тест проверяет получение списка заказов")
    public void checkGetAllOrders() {
        Response createResponse = sendPostCreateNewOrders(pathFile);
        Response getResponse = sendGetAllOrders();
        compareResponseBody(getResponse);
        compareResponseCode(getResponse);
        cancelOrders(createResponse);
    }

    @Step("Отправить запрос на создание нового заказа /api/v1/orders")
    public Response sendPostCreateNewOrders(String pathFile) {
        File json = new File(pathFile);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Отправить запрос на получение списка заказов /api/v1/orders")
    public Response sendGetAllOrders() {
        return given().get("/api/v1/orders");
    }

    @Step("Отправить запрос на отмену заказа /api/v1/orders/cancel")
    public void cancelOrders(Response createResponse) {
        String trackId = createResponse.jsonPath().getString("track");
        given().put("/api/v1/orders/cancel?track=" + trackId);
    }

    @Step("Сравнить код ответа")
    public void compareResponseCode(Response response) {
        response.then().assertThat().statusCode(CODE_OK);
    }

    @Step("Сравнить тело ответа")
    public void compareResponseBody(Response response) {
        response.then().assertThat().body("orders", notNullValue());
    }
}
