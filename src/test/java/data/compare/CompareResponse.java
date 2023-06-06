package data.compare;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CompareResponse {

    private Response response;
    private String message;
    private int code;
    private String bodyPath;

    public void Response(Response response, int code, String message, String bodyPath) {
        this.response = response;
        this.message = message;
        this.code = code;
        this.bodyPath = bodyPath;
    }

    public void compareResponseBody(Response response, int code, String message) {
        response.then().assertThat()
                .body("code", equalTo(code))
                .body("message", equalTo(message));
    }

    public void compareResponseBodyIsCreated(Response response, String bodyPath) {
        response.then().assertThat().body(bodyPath, equalTo(true));
    }

    public void compareResponseCode(Response response, int code) {
        response.then().assertThat().statusCode(code);
    }

    public void compareResponseBodyIsNotNull(Response response, String bodyPath) {
        response.then().assertThat().body(bodyPath, notNullValue());
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBodyPath() {
        return bodyPath;
    }

    public void setBodyPath(String bodyPath) {
        this.bodyPath = bodyPath;
    }
}