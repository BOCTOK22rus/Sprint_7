import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import data.compare.CompareResponse;
import data.orders.OrdersClient;

import static data.Constants.*;

public class GetOrdersTest {

    private static final String pathFile = "src/test/java/TestData/resources/TwoColors.json";

    @Test
    @DisplayName("Check Get All Orders")
    @Description("Тест проверяет получение списка заказов")
    public void checkGetAllOrders() {
        Response newOrders = new OrdersClient().createOrders(pathFile);
        new CompareResponse().compareResponseCode(newOrders, CODE_CREATED);
        Response allOrders = new OrdersClient().sendGetAllOrders();
        new CompareResponse().compareResponseCode(allOrders, CODE_OK);
        new CompareResponse().compareResponseBodyIsNotNull(allOrders, "orders");
        new OrdersClient().cancelOrders(newOrders);
    }
}