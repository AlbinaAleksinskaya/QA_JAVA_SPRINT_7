import api.client.OrdersClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.notNullValue;

public class ScooterGetOrderListTest extends BaseTest {

    @Test
    @DisplayName("Check response for get orders")
    public void testCheckResponseForGetOrderList() {
        OrdersClient orderClient = new OrdersClient();
        Response response = orderClient.getResponseForOrders();
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("orders", notNullValue());
    }
}