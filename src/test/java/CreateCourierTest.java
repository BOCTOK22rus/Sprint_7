import org.apache.commons.lang3.RandomStringUtils;
import testData.compare.CompareResponse;
import testData.courier.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;

import static testData.Constants.*;

public class CreateCourierTest {

    @Test
    @DisplayName("Check Create New Courier")
    @Description("Тест проверяет метод создания курьера")
    public void checkCreateNewCourier() {
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        Response response = new CourierClient().createCourier(login, password, login);
        new CompareResponse().compareResponseCode(response, CODE_CREATED);
        new CompareResponse().compareResponseBodyIsCreated(response, "ok");
        String id = response.jsonPath().getString("id");
        new CourierClient().deleteCourier(id);
    }

    @Test
    @DisplayName("Check Cannot Create Existing Courier")
    @Description("Тест проверяет метод создания курьера с существующим логином")
    public void checkCannotCreateExistingCourier() {
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        new CourierClient().createCourier(login, password, login);
        Response response = new CourierClient().createCourier(login, password, login);
        new CompareResponse().compareResponseCode(response, CODE_CONFLICT);
        new CompareResponse().compareResponseBody(response, CODE_CONFLICT, CREATED_CONFLICT);
        String id = response.jsonPath().getString("id");
        new CourierClient().deleteCourier(id);
    }

    @Test
    @DisplayName("Check Validation Field Login")
    @Description("Тест проверяет обязательность поля login при создании курьера")
    public void checkValidationFieldLogin() {
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        Response response = new CourierClient().createCourier("", password, login);
        new CompareResponse().compareResponseCode(response, CODE_BAD_REQUEST);
        new CompareResponse().compareResponseBody(response, CODE_BAD_REQUEST, CREATED_NOT_PARAMETER);
        String id = response.jsonPath().getString("id");
        new CourierClient().deleteCourier(id);
    }

    @Test
    @DisplayName("Check Validation Field Password")
    @Description("Тест проверяет обязательность поля password при создании курьера")
    public void checkValidationFieldPassword() {
        String login = RandomStringUtils.randomAlphabetic(6);
        Response response = new CourierClient().createCourier(login, "", login);
        new CompareResponse().compareResponseCode(response, CODE_BAD_REQUEST);
        new CompareResponse().compareResponseBody(response, CODE_BAD_REQUEST, CREATED_NOT_PARAMETER);
        String id = response.jsonPath().getString("id");
        new CourierClient().deleteCourier(id);
    }
}