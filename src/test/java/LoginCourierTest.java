import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import data.compare.CompareResponse;
import data.courier.CourierClient;

import static data.Constants.*;

public class LoginCourierTest {

    @Test
    @DisplayName("Check Login Courier")
    @Description("Тест проверяет авторизацию курьера")
    public void checkLoginCourier() {
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        new CourierClient().createCourier(login, password, login);
        Response response = new CourierClient().loginCourier(login, password);
        new CompareResponse().compareResponseCode(response, CODE_OK);
        new CompareResponse().compareResponseBodyIsNotNull(response, "id");
        String id = response.jsonPath().getString("id");
        new CourierClient().deleteCourier(id);
    }

    @Test
    @DisplayName("Check Validation Field Login")
    @Description("Тест проверяет обязательность поля login при авторизации курьера")
    public void checkValidationFieldLogin() {
        String password = RandomStringUtils.randomAlphabetic(6);
        Response response = new CourierClient().loginCourier(null, password);
        new CompareResponse().compareResponseCode(response, CODE_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check Correct Field Login")
    @Description("Тест проверяет валидацию значения поля login при авторизации курьера")
    public void checkCorrectFieldLogin() {
        String password = RandomStringUtils.randomAlphabetic(6);
        Response response = new CourierClient().loginCourier("", password);
        new CompareResponse().compareResponseCode(response, CODE_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check Correct Field Password")
    @Description("Тест проверяет валидацию значения поля password при авторизации курьера")
    public void checkCorrectFieldPassword() {
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        Response createResponse = new CourierClient().createCourier(login, password, login);
        Response loginResponse = new CourierClient().loginCourier(login, "");
        new CompareResponse().compareResponseCode(loginResponse, CODE_BAD_REQUEST);
        String id = createResponse.jsonPath().getString("id");
        new CourierClient().deleteCourier(id);
    }
}