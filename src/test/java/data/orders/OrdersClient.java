package data.orders;

import io.restassured.response.Response;
import data.RequestSpec;

import java.io.File;

import static io.restassured.RestAssured.given;
import static data.Constants.CANCEL_ORDERS;
import static data.Constants.URL_ORDERS;

public class OrdersClient extends RequestSpec {

    public Response createOrders(String pathFile) {
        File json = new File(pathFile);
        return given()
                .spec(getSpec())
                .body(json)
                .when()
                .post(URL_ORDERS);
    }

    public void cancelOrders(Response response) {
        String trackId = response.jsonPath().getString("track");
        given()
                .spec(getSpec())
                .put(CANCEL_ORDERS + trackId);
    }

    public Response sendGetAllOrders() {
        return given()
                .spec(getSpec())
                .get(URL_ORDERS);
    }
}
