package testData;

public class Constants {

    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public static final int CODE_OK = 200;
    public static final int CODE_CREATED = 201;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_CONFLICT = 409;

    public static final String CREATED_CONFLICT = "Этот логин уже используется. Попробуйте другой.";
    public static final String CREATED_NOT_PARAMETER = "Недостаточно данных для создания учетной записи";

    public static final String CREATE_COURIER = "/api/v1/courier";
    public static final String LOGIN_COURIER = "/api/v1/courier/login";
    public static final String URL_ORDERS = "/api/v1/orders";
    public static final String CANCEL_ORDERS = "/api/v1/orders/cancel?track=";
}