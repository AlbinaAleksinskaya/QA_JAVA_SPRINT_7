package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import api.endpoints.EndPoints;

public class OrdersClient {

    public final String PATH = EndPoints.BASE_URL +EndPoints.ORDER_URL;

    @Step("Create order")
    public Response createOrders(Object order) {
        return (Response) given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Cancel order")
    public Response cancelOrders(int orderTrack) {
        return (Response) given()
                .header("Content-type", "application/json")
                .and()
                .body(orderTrack)
                .when()
                .put(PATH + "/cancel")
                .then();
    }

    @Step("Finish order")
    public Response finishOrders(int id) {
        String body = "{\"id\":\"" + id + "\"}";
        return (Response) given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .put(PATH + "/finish/" + id)
                .then();
    }

    @Step("Get orders list")
    public Response getListOfOrders() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get(PATH);
    }

    @Step("Agree order")
    public Response acceptOrders(int orderId) {
        return (Response) given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .put(PATH + "/accept/" + orderId)
                .then();
    }

    @Step("Agree order of courier")
    public Response acceptOrders(int orderId, int courierId) {
        return (Response) given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .queryParam("courierId", courierId)
                .put(PATH + "/accept/" + orderId)
                .then();
    }


    @Step("Get order with number of order")
    public Response getOrdersByTrack(int track) {
        return (Response) given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .queryParam("t", track)
                .get(PATH + "/track")
                .then();
    }

    @Step("Get order without number of order")
    public Response getOrdersByTrack() {
        return (Response) given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get(PATH + "/track")
                .then();
    }
}
