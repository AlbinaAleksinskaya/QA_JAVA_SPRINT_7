import api.client.OrdersClient;
import api.models.OrderResponse;
import api.models.Orders;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScooterGetOrderListTest extends BaseTest {
    private OrdersClient ordersClient;
    private Response response;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Get orders list")
    public void getListOfOrdersWithoutParams() {

        response = ordersClient.getListOfOrders();
        int statusCode = response.then().extract().statusCode();
        OrderResponse[] orderResponse = response.body().as(Orders.class).getOrders();

        assertEquals("statusCode неверный", 200, statusCode);
        assertTrue("Нет заказов", orderResponse.length > 0);
    }
}