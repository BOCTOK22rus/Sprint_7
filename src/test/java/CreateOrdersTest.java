import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import data.compare.CompareResponse;
import data.orders.OrdersClient;

import java.util.Arrays;
import java.util.List;

import static data.Constants.*;

@RunWith(Parameterized.class)
public class CreateOrdersTest {

    @Parameterized.Parameter
    public static String pathFile;

    @Parameterized.Parameters
    public static List<Object[]> getData() {
        return Arrays.asList(new Object[][]{
                {"src/test/java/data/resources/BlackColors.json"},
                {"src/test/java/data/resources/GreyColors.json"},
                {"src/test/java/data/resources/NotColors.json"},
                {"src/test/java/data/resources/TwoColors.json"}
        });
    }

    @Test
    @DisplayName("Check Create New Orders")
    @Description("Тест проверяет создание заказа")
    public void checkCreateNewOrders() {
        Response response = new OrdersClient().createOrders(pathFile);
        new CompareResponse().compareResponseCode(response, CODE_CREATED);
        new CompareResponse().compareResponseBodyIsNotNull(response, "track");
        new OrdersClient().cancelOrders(response);
    }
}