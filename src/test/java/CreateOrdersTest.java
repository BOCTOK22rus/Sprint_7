import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static TestData.Constants.BASE_URL;
import static TestData.Constants.CODE_CREATED;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Parameterized.Parameter
    public static String pathFile;

    @Parameterized.Parameters
    public static List<Object[]> getData() {
        return Arrays.asList(new Object[][] {
                {"src/test/java/TestData/resources/BlackColors.json"},
                {"src/test/java/TestData/resources/GreyColors.json"},
                {"src/test/java/TestData/resources/NotColors.json"},
                {"src/test/java/TestData/resources/TwoColors.json"}
        });
    }

    @Test
    @DisplayName("Check Create New Orders")
    @Description("Тест проверяет создание заказа")
    public void checkCreateNewOrders() {
        Response response = sendPostCreateNewOrders(pathFile);
        compareResponseBodyCreated(response);
        compareResponseCodeIsCreated(response);
        String trackId = response.jsonPath().getString("track");
        given().put("/api/v1/orders/cancel?track=" + trackId);
    }

    @Step("Отправить запрос на создание нового заказа /api/v1/orders")
    public static Response sendPostCreateNewOrders(String pathFile) {
        File json = new File(pathFile);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Сравнить код ответа")
    public void compareResponseCodeIsCreated(Response response) {
        response.then().assertThat().statusCode(CODE_CREATED);
    }

    @Step("Сравнить тело ответа")
    public void compareResponseBodyCreated(Response response) {
        response.then().assertThat().body("track", notNullValue());
    }
}