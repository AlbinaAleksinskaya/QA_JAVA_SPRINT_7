import api.client.CourierClient;
import api.endpoints.EndPoints;
import api.models.Courier;
import api.utils.ScooterGenerateCurierData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScooterRegisterCourierTest extends BaseTest {
    private CourierClient courierClient;
    private ScooterGenerateCurierData scooterGenerateCurierData;
    private int courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        scooterGenerateCurierData = new ScooterGenerateCurierData();
    }

    @Test
    @DisplayName("Create courier")
    public void createCourierValidData() {
        courier = Courier.getRandom();
        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        boolean courierCreated = response.extract().path("ok");
        assertEquals("statusCode неверный", 201, statusCode);
        assertTrue("Курьер не создан", courierCreated);
    }

    @Test
    @DisplayName("Create courier without password")
    public void createCourierWithoutPassword(){
        courier = new Courier(RandomStringUtils.randomAlphabetic(10), "", RandomStringUtils.randomAlphabetic(10));
        String message = "Недостаточно данных для создания учетной записи";
        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        String courierWithoutCredentials = response.extract().path("message");
        assertEquals("statusCode неверный", 400, statusCode);
        assertTrue("Сообщение о создании курьера некорректно", courierWithoutCredentials.contains(message));
    }


    @Test
    @DisplayName("Create courier without login")
    public void createCourierWithoutLogin(){
        courier = new Courier("", RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        String message = "Недостаточно данных для создания учетной записи";
        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        String courierWithoutCredentials = response.extract().path("message");
        assertEquals("statusCode неверный", 400, statusCode);
        assertTrue("Сообщение о создании курьера некорректно", courierWithoutCredentials.contains(message));
     }

    @Test
    @DisplayName("Create courier with used login")
    public void createSecondIsSameCourierError() {
        courier = Courier.getRandom();
        String message = "Этот логин уже используется";
        boolean courierCreated = scooterGenerateCurierData.createCourier(courier);
        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        String secondCourierCreated = response.extract().path("message");
        assertEquals("statusCode неверный", 409, statusCode);
        assertTrue("Сообщение о создании второго такого же курьера некорректно", secondCourierCreated.contains(message));
    }
}