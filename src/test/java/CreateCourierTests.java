import clients.CourierClient;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierGenerator;
import org.junit.*;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;

public class CreateCourierTests {

    private CourierClient courierClient;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }


    @Test
    public void courierCanBeCreated() {
        Courier testCourier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.createCourier(testCourier);
        int status = createResponse.extract().statusCode();
        boolean ok = createResponse.extract().path("ok");
        assertEquals("Not valid status code", HTTP_CREATED, status);
        assertTrue("Not valid response. Courier wasn't created", ok);

        courierClient.deleteCourierInTheEnd(testCourier.getLogin(), testCourier.getPassword());
    }

    @Test
    public void twoIdenticalLoginsCantBeCreated() {
        Courier testCourier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.createCourier(testCourier);
        Courier testCourierWithSameLogin = new Courier("Odin", testCourier.getLogin(), "NewPass");
        ValidatableResponse createResponseIdentical = courierClient.createCourier(testCourierWithSameLogin);
        int status = createResponseIdentical.extract().statusCode();
        String response = createResponseIdentical.extract().path("message");
        assertEquals("Not valid status code", HTTP_CONFLICT, status);
        assertEquals("Not valid response", "Этот логин уже используется", response);

        courierClient.deleteCourierInTheEnd(testCourier.getLogin(), testCourier.getPassword());
    }

    @Test
    public void missingLoginError() {
        Courier testCourier = CourierGenerator.getRandomWithoutLogin();
        ValidatableResponse createResponse = courierClient.createCourier(testCourier);
        int status = createResponse.extract().statusCode();
        String response = createResponse.extract().path("message");
        assertEquals("Not valid status code", HTTP_BAD_REQUEST, status);
        assertEquals("Not valid response", "Недостаточно данных для создания учетной записи", response);
    }

    @Test
    public void missingPasswordError() {
        Courier testCourier = CourierGenerator.getRandomWithoutPassword();
        ValidatableResponse createResponse = courierClient.createCourier(testCourier);
        int status = createResponse.extract().statusCode();
        String response = createResponse.extract().path("message");
        assertEquals("Not valid status code", HTTP_BAD_REQUEST, status);
        assertEquals("Not valid response", "Недостаточно данных для создания учетной записи", response);
    }

}
