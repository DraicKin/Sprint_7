package clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierCredentials;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class CourierClient extends BaseClient {

    private final String COURIER_URI = BASE_URI + "/api/v1/courier";
    @Step("Create courier")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_URI)
                .then();
    }

    @Step("Login courier with credentials")
    public ValidatableResponse loginCourier(CourierCredentials courierCredentials) {
        return given()
                .header("Content-type", "application/json")
                .body(courierCredentials)
                .when()
                .post(COURIER_URI + "/login")
                .then();
    }

    @Step("Delete courier with id")
    public ValidatableResponse deleteCourier(int id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_URI + "/" + id)
                .then();
    }

    @Step("Cleaning up")
    public void deleteCourierInTheEnd (String login, String password) {
        CourierCredentials credentials = new CourierCredentials(login, password);
        ValidatableResponse loginResponse = loginCourier(credentials);
        int id = loginResponse.extract().path("id");
        deleteCourier(id).statusCode(HTTP_OK);
    }

}
