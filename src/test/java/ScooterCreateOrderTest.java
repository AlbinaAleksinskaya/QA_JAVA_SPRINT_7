import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import api.client.OrdersClient;
import api.models.Order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class ScooterCreateOrderTest {
    private OrdersClient ordersApiClient;
    private int track;

    private final String[] color;

    public ScooterCreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColorList() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"GREY", "BLACK"}},
                {new String[0]}

        };
    }

    @Before
    public void setUp() {
        ordersApiClient = new OrdersClient();
    }

    @Test
    @DisplayName("Create order")
    public void createOrderWithColors() {
        Order order = Order.getRandom();
        order.setColor(color);

        ValidatableResponse response = (ValidatableResponse) ordersApiClient.createOrders(order);
        int statusCode = response.extract().statusCode();
        track = response.extract().path("track");

        assertEquals("statusCode неверный", 201, statusCode);
        assertNotEquals("Track некоректный", 0, track);
    }

}