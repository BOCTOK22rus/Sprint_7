package TestData;

public class Constants {

    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public static final int CODE_OK = 200;
    public static final int CODE_CREATED = 201;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_CONFLICT = 409;

    public static final String LOGIN = "Ilya86";
    public static final String PASSWORD = "12345";

    public static final String CREATED_CONFLICT = "Этот логин уже используется. Попробуйте другой.";
    public static final String CREATED_NOT_PARAMETER = "Недостаточно данных для создания учетной записи";
    public static final String LOGIN_NOT_PARAMETER = "Недостаточно данных для входа";
    public static final String LOGIN_NOT_FOUND = "Учетная запись не найдена";
}