import clients.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierGenerator;
import org.junit.*;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;

public class CreateCourierTests {

    private CourierClient courierClient;
    private Courier testCourier;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void clearUp() {
        courierClient.deleteCourierInTheEnd(testCourier.getLogin(), testCourier.getPassword());
    }

    @Test
    @DisplayName("Создание курьера")
    public void courierCanBeCreated() {
        testCourier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.createCourier(testCourier);
        int status = createResponse.extract().statusCode();
        boolean ok = createResponse.extract().path("ok");
        assertEquals("Not valid status code", HTTP_CREATED, status);
        assertTrue("Not valid response. Courier wasn't created", ok);
    }

    @Test
    @DisplayName("Создание двух пользователей с одинаковым логином")
    public void twoIdenticalLoginsCantBeCreated() {
        testCourier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.createCourier(testCourier);
        Courier testCourierWithSameLogin = new Courier("Odin", testCourier.getLogin(), "NewPass");
        ValidatableResponse createResponseIdentical = courierClient.createCourier(testCourierWithSameLogin);
        int status = createResponseIdentical.extract().statusCode();
        String response = createResponseIdentical.extract().path("message");
        assertEquals("Not valid status code", HTTP_CONFLICT, status);
        assertEquals("Not valid response", "Этот логин уже используется", response);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void missingLoginError() {
        testCourier = CourierGenerator.getRandomWithoutLogin();
        ValidatableResponse createResponse = courierClient.createCourier(testCourier);
        int status = createResponse.extract().statusCode();
        String response = createResponse.extract().path("message");
        assertEquals("Not valid status code", HTTP_BAD_REQUEST, status);
        assertEquals("Not valid response", "Недостаточно данных для создания учетной записи", response);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void missingPasswordError() {
        testCourier = CourierGenerator.getRandomWithoutPassword();
        ValidatableResponse createResponse = courierClient.createCourier(testCourier);
        int status = createResponse.extract().statusCode();
        String response = createResponse.extract().path("message");
        assertEquals("Not valid status code", HTTP_BAD_REQUEST, status);
        assertEquals("Not valid response", "Недостаточно данных для создания учетной записи", response);
    }

}
