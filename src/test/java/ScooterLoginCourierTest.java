import api.client.CourierClient;
import api.models.Courier;
import api.models.CourierCredentials;
import api.utils.ScooterGenerateCurierData;
import api.utils.ScooterGenerateOrderData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScooterLoginCourierTest extends BaseTest {
    private String random = RandomStringUtils.randomAlphabetic(10);
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;
    private ScooterGenerateOrderData scooterGenerateOrderData;
    private ScooterGenerateCurierData scooterGenerateCurierData;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
        scooterGenerateOrderData = new ScooterGenerateOrderData();
    }

    @After
    public void tearDown() {
        boolean isCourierDeleted = scooterGenerateCurierData.deleteCourier(courierId);
        assertTrue("Курьер не удален", isCourierDeleted);
    }

    @Test
    @DisplayName("Courier auth")
    public void loginCourierValid() {
        courier = new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        ValidatableResponse response = (ValidatableResponse) courierClient.loginCourier(CourierCredentials.getCredentials(courier));
        int statusCode = response.extract().statusCode();
        courierId = response.extract().path("id");

        assertEquals("statusCode неверный", 200, statusCode);
        assertNotEquals("Id курьера некоректный", 0, courierId);
    }

    @Test
    @DisplayName("Auth courier with invalid data")
    public void loginCourierInvalid() {
        String message = "Учетная запись не найдена";
        ValidatableResponse response = (ValidatableResponse) courierClient.loginCourier(courier);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");
        assertEquals("statusCode неверный", 404, statusCode);
        assertTrue("Сообщение об ошибке некорректно", errorMessage.contains(message));
    }

    @Test
    @DisplayName("Auth courier with nullable login")
    public void loginCourierEmptyLogin() {
        String message = "Недостаточно данных для входа";
        ValidatableResponse response = (ValidatableResponse) courierClient.loginCourier(courier);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");
        assertEquals("statusCode неверный", 400, statusCode);
        assertTrue("Сообщение об ошибке некорректно", errorMessage.contains(message));
    }
}