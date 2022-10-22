import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import api.client.OrdersClient;
import api.utils.ScooterGenerateCurierData;
import api.utils.ScooterGenerateOrderData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScooterAcceptOrderByCourierTest {

    private OrdersClient ordersApiClient;
    private ScooterGenerateOrderData scooterGenerateOrderData;
    private ScooterGenerateCurierData scooterGenerateCurierData;
    private int courierId;
    private int idOrder;

    @Before
    public void setUp() {
        ordersApiClient = new OrdersClient();
        scooterGenerateOrderData = new ScooterGenerateOrderData();
        scooterGenerateCurierData = new ScooterGenerateCurierData();

        idOrder = scooterGenerateOrderData.createRandomOrder();
        courierId = scooterGenerateCurierData.createRandomCourier();
    }

    @After
    public void tearDown() {
        boolean isCourierDeleted = scooterGenerateCurierData.deleteCourier(courierId);
        assertTrue("Курьер не удален", isCourierDeleted);
        boolean isOrderFinished = scooterGenerateOrderData.finishOrder(idOrder);
        assertTrue("Орден не закрыт", isOrderFinished);
    }

    @Test
    @DisplayName("Accept order")
    public void acceptOrderValid() {

        ValidatableResponse response = (ValidatableResponse) ordersApiClient.acceptOrders(idOrder, courierId);
        int statusCode = response.extract().statusCode();
        boolean isAcceptOrder = response.extract().path("ok");

        assertEquals("statusCode неверный", 200, statusCode);
        assertTrue("Заказ не принят", isAcceptOrder);
    }
}