package testData.courier;

import io.restassured.response.Response;
import testData.RequestSpec;
import testData.courier.data.Courier;
import testData.courier.data.Login;

import static testData.Constants.CREATE_COURIER;
import static io.restassured.RestAssured.given;
import static testData.Constants.LOGIN_COURIER;

public class CourierClient extends RequestSpec {

    public Response createCourier(String login, String password, String firstName) {
        Courier courier = new Courier(login, password, firstName);
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(CREATE_COURIER);
    }

    public Response loginCourier(String login, String password) {
        Login courierLogin = new Login(login, password);
        return given()
                .spec(getSpec())
                .body(courierLogin)
                .when()
                .post(LOGIN_COURIER);
    }

    public void deleteCourier(String id) {
        given()
                .spec(getSpec())
                .delete("/api/v1/courier/" + id);
    }
}