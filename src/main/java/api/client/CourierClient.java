package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import api.endpoints.EndPoints;


public class CourierClient {

    public final String PATH = EndPoints.BASE_URL + EndPoints.COURIER_URL;

    @Step("Get response for incorrect password")
    public Response getIncorrectPasswordResponse(Object body) {
        return (Response) given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(PATH + "login/")
                .then();
    }

    @Step("Create courier")
    public ValidatableResponse createCourier(Object courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Login courier")
    public Response loginCourier(Object courierCredentials) {
        return (Response) given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then();
    }

    @Step("Delete courier")
    public Response deleteCourier(int courierId) {
        return (Response) given()
                .header("Content-type", "application/json")
                .when()
                .delete(PATH + courierId)
                .then();
    }
}
