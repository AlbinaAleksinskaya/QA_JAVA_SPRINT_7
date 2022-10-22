import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import api.client.CourierClient;
import api.endpoints.EndPoints;
import api.utils.ScooterGenerateCurierData;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class ScooterDeleteCourierTest extends BaseTest {
    private CourierClient courierApiClient;
    private ScooterGenerateCurierData scooterGenerateCurierData;
    private int courierId;

    @Before
    public void setUp() {
        courierApiClient = new CourierClient();
        scooterGenerateCurierData = new ScooterGenerateCurierData();
    }

    @Test
    @DisplayName("Delete courier")
    public void deleteCourierValid() {

        courierId = scooterGenerateCurierData.createRandomCourier();
        boolean isCourierDeleted = courierApiClient.deleteCourier(courierId).assertThat().statusCode(200).extract().path("ok");
        assertTrue("Курьер не удален", isCourierDeleted);
    }

    @Test
    @DisplayName("Delete courier with undefinded id")
    public void deleteCourierWithInvalidId() {
        courierId = 0;
        String message = "Курьера с таким id нет";

        ValidatableResponse response = (ValidatableResponse) courierApiClient.deleteCourier(courierId);
        int statusCode = response.extract().statusCode();
        String courierDeleted = response.extract().path("message");

        assertEquals("statusCode неверный", 404, statusCode);
        assertTrue("Сообщение о удалении курьера некорректно", courierDeleted.contains(message));
    }

    @Test
    @DisplayName("Delete courier without id")
    public void deleteCourierWithoutId() {
        String message = "Недостаточно данных для удаления курьера";

        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .when()
                .delete(EndPoints.BASE_URL + EndPoints.COURIER_URL)
                .then();
        int statusCode = response.extract().statusCode();
        String courierDeleted = response.extract().path("message");

        assertEquals("statusCode неверный", 404, statusCode);
        assertTrue("Сообщение о недостаточности данных некорректно", courierDeleted.contains(message));
    }
}