package clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.ListOfOrders;
import models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private final String ORDER_URI = BASE_URI + "/api/v1/orders";

    @Step("Create order {order}")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDER_URI)
                .then();
    }

    @Step("Get list of orders without id")
    public ListOfOrders getList() {
        return given()
                .header("Content-type", "application/json")
                .get(ORDER_URI)
                .body()
                .as(ListOfOrders.class);
    }
}
