import TestData.GeneratedCourierLogin;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;

import static TestData.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {

    private static final String newLogin = new GeneratedCourierLogin().courierLogin;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Check Create New Courier")
    @Description("Тест проверяет метод создания курьера")
    public void checkCreateNewCourier() {
        Response response = sendPostCreateNewCourier();
        compareResponseBodyCreated(response);
        compareResponseCodeIsCreated(response);
    }

    @Test
    @DisplayName("Check Cannot Create Existing Courier")
    @Description("Тест проверяет метод создания курьера с существующим логином")
    public void checkCannotCreateExistingCourier() {
        Response response = sendPostCreateExistingCourier();
        compareResponseBodyConflict(response);
        compareResponseCodeIsConflict(response);
    }

    @Test
    @DisplayName("Check Validation Field Login")
    @Description("Тест проверяет обязательность поля login при создании курьера")
    public void checkValidationFieldLogin() {
        Response response = sendPostCreateNewCourierNotFieldLogin();
        compareResponseCodeBadRequest(response);
        compareResponseBodyNotRequiredParameter(response);
    }

    @Test
    @DisplayName("Check Validation Field Password")
    @Description("Тест проверяет обязательность поля password при создании курьера")
    public void checkValidationFieldPassword() {
        Response response = sendPostCreateNewCourierNotFieldPassword();
        compareResponseCodeBadRequest(response);
        compareResponseBodyNotRequiredParameter(response);
    }

    @Step("Отправить запрос на создание нового курьера /api/v1/courier")
    public Response sendPostCreateNewCourier() {
        String jsonBody =
                "{\"login\": " + "\"" + newLogin + "\"" +
                        ", \"password\": " + "\"" + PASSWORD + "\"" +
                        ", \"firstName\": " + "\"" + newLogin + "\"" + "}";
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Отправить запрос на создание дубля курьера /api/v1/courier")
    public Response sendPostCreateExistingCourier() {
        String jsonBody =
                "{\"login\": " + "\"" + LOGIN + "\"" +
                        ", \"password\": " + "\"" + PASSWORD + "\"" +
                        ", \"firstName\": " + "\"" + LOGIN + "\"" + "}";
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Отправить запрос на создание нового курьера без поля login /api/v1/courier")
    public Response sendPostCreateNewCourierNotFieldLogin() {
        String jsonBody =
                "{\"password\": " + "\"" + PASSWORD + "\"" +
                        ", \"firstName\": " + "\"" + newLogin + "\"" + "}";
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Отправить запрос на создание нового курьера без поля password /api/v1/courier")
    public Response sendPostCreateNewCourierNotFieldPassword() {
        String jsonBody =
                "{\"login\": " + "\"" + newLogin + "\"" +
                        ", \"firstName\": " + "\"" + newLogin + "\"" + "}";
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Сравнить код ответа")
    public void compareResponseCodeIsCreated(Response response) {
        response.then().assertThat().statusCode(CODE_CREATED);
    }

    @Step("Сравнить код ответа")
    public void compareResponseCodeIsConflict(Response response) {
        response.then().assertThat().statusCode(CODE_CONFLICT);
    }

    @Step("Сравнить код ответа")
    public void compareResponseCodeBadRequest(Response response) {
        response.then().assertThat().statusCode(CODE_BAD_REQUEST);
    }

    @Step("Сравнить тело ответа")
    public void compareResponseBodyCreated(Response response) {
        response.then().assertThat().body("ok", equalTo(true));
    }

    @Step("Сравнить тело ответа")
    public void compareResponseBodyConflict(Response response) {
        response.then().assertThat()
                .body("code", equalTo(CODE_CONFLICT))
                .body("message", equalTo(CREATED_CONFLICT));
    }

    @Step("Сравнить тело ответа")
    public void compareResponseBodyNotRequiredParameter(Response response) {
        response.then().assertThat()
                .body("code", equalTo(CODE_BAD_REQUEST))
                .body("message", equalTo(CREATED_NOT_PARAMETER));
    }

    @Step("Отправить запрос для получения id курьера /api/v1/courier/login")
    public static Response getIdCourier() {
        String jsonBody =
                "{\"login\": " + "\"" + newLogin + "\"" +
                        ", \"password\": " + "\"" + PASSWORD + "\"" + "}";
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Отправить запрос на удаление курьера /api/v1/courier/:id")
    public static void sendDeleteCourier(String courierId) {
        given().delete("/api/v1/courier/" + courierId);
    }

    @AfterClass
    public static void deleteCourier() {
        Response response = getIdCourier();
        String courierId = response.jsonPath().getString("id");
        sendDeleteCourier(courierId);
    }
}