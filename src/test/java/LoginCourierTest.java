import TestData.GeneratedCourierLogin;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static TestData.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {

    private static final String newLogin = new GeneratedCourierLogin().courierLogin;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Check Login Courier")
    @Description("Тест проверяет авторизацию курьера")
    public void checkLoginCourier() {
        sendPostCreateNewCourier();
        Response response = sendPostLoginCourier();
        compareResponseBodyLogin(response);
        compareResponseCodeIsLogin(response);
    }

    @Test
    @DisplayName("Check Validation Field Login")
    @Description("Тест проверяет обязательность поля login при авторизации курьера")
    public void checkValidationFieldLogin() {
        Response response = sendPostLoginCourierNotFieldLogin();
        compareResponseCodeBadRequest(response);
        compareResponseBodyNotRequiredParameter(response);
    }

    @Test
    @DisplayName("Check Correct Field Login")
    @Description("Тест проверяет валидацию значения поля login при авторизации курьера")
    public void checkCorrectFieldLogin() {
        Response response = sendPostLoginCourierIncorrectLogin();
        compareResponseCodeNotFound(response);
        compareResponseBodyIncorrectParameter(response);
    }

    @Test
    @DisplayName("Check Correct Field Password")
    @Description("Тест проверяет валидацию значения поля password при авторизации курьера")
    public void checkCorrectFieldPassword() {
        Response response = sendPostLoginCourierIncorrectPassword();
        compareResponseCodeNotFound(response);
        compareResponseBodyIncorrectParameter(response);
    }

    @Step("Отправить запрос на создание нового курьера /api/v1/courier")
    public void sendPostCreateNewCourier() {
        String jsonBody =
                "{\"login\": " + "\"" + newLogin + "\"" +
                        ", \"password\": " + "\"" + PASSWORD + "\"" +
                        ", \"firstName\": " + "\"" + newLogin + "\"" + "}";
        given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Отправить запрос для авторизации курьера /api/v1/courier/login")
    public static Response sendPostLoginCourier() {
        String jsonBody =
                "{\"login\": " + "\"" + newLogin + "\"" +
                        ", \"password\": " + "\"" + PASSWORD + "\"" + "}";
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Отправить запрос для авторизации курьера без поля login /api/v1/courier/login")
    public Response sendPostLoginCourierNotFieldLogin() {
        String jsonBody =
                "{\"password\": " + "\"" + PASSWORD + "\"" + "}";
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Отправить запрос для авторизации курьера c некорректным полем login /api/v1/courier/login")
    public Response sendPostLoginCourierIncorrectLogin() {
        String jsonBody =
                "{\"login\": " + "\"" + newLogin + "1" + "\"" +
                        ", \"password\": " + "\"" + PASSWORD + "\"" + "}";
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Отправить запрос для авторизации курьера c некорректным полем password /api/v1/courier/login")
    public Response sendPostLoginCourierIncorrectPassword() {
        String jsonBody =
                "{\"login\": " + "\"" + newLogin + "\"" +
                        ", \"password\": " + "\"" + PASSWORD + "1" + "\"" + "}";
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Сравнить код ответа")
    public void compareResponseCodeIsLogin(Response response) {
        response.then().assertThat().statusCode(CODE_OK);
    }

    @Step("Сравнить код ответа")
    public void compareResponseCodeBadRequest(Response response) {
        response.then().assertThat().statusCode(CODE_BAD_REQUEST);
    }

    @Step("Сравнить код ответа")
    public void compareResponseCodeNotFound(Response response) {
        response.then().assertThat().statusCode(CODE_NOT_FOUND);
    }

    @Step("Сравнить тело ответа")
    public void compareResponseBodyLogin(Response response) {
        response.then().assertThat().body("id", notNullValue());
    }

    @Step("Сравнить тело ответа")
    public void compareResponseBodyNotRequiredParameter(Response response) {
        response.then().assertThat()
                .body("code", equalTo(CODE_BAD_REQUEST))
                .body("message", equalTo(LOGIN_NOT_PARAMETER));
    }

    @Step("Сравнить тело ответа")
    public void compareResponseBodyIncorrectParameter(Response response) {
        response.then().assertThat()
                .body("code", equalTo(CODE_NOT_FOUND))
                .body("message", equalTo(LOGIN_NOT_FOUND));
    }

    @Step("Отправить запрос на удаление курьера /api/v1/courier/:id")
    public static void sendDeleteCourier(String courierId) {
        given().delete("/api/v1/courier/" + courierId);
    }

    @AfterClass
    public static void deleteCourier() {
        Response response = sendPostLoginCourier();
        String courierId = response.jsonPath().getString("id");
        sendDeleteCourier(courierId);
    }
}
