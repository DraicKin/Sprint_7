import clients.CourierClient;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierCredentials;
import models.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class LoginCourierTests {

    private CourierClient courierClient;
    private Courier testCourier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        testCourier = CourierGenerator.getRandom();
        courierClient.createCourier(testCourier).statusCode(HTTP_CREATED);
    }

    @After
    public void cleanUp() {
        courierClient.deleteCourierInTheEnd(testCourier.getLogin(), testCourier.getPassword());
    }

    @Test
    public void successCourierLogin() {
        CourierCredentials credentials = new CourierCredentials(testCourier.getLogin(), testCourier.getPassword());

        ValidatableResponse loginResponse = courierClient.loginCourier(credentials);
        int loginStatus = loginResponse.extract().statusCode();
        assertEquals("Not valid status code", HTTP_OK, loginStatus);
        int id = loginResponse.extract().path("id");
        assertNotEquals("ID = 0", 0, id);
    }

    @Test
    public void invalidLoginCourierLogin() {
        CourierCredentials invalidCredentials = new CourierCredentials(
                testCourier.getLogin().substring(1),
                testCourier.getPassword()
        );

        ValidatableResponse loginResponse = courierClient.loginCourier(invalidCredentials);
        int loginStatus = loginResponse.extract().statusCode();
        assertEquals("Not valid status code", HTTP_NOT_FOUND, loginStatus);
        String loginResponseMessage = loginResponse.extract().path("message");
        assertEquals("Not valid response.", "Учетная запись не найдена", loginResponseMessage);
    }

    @Test
    public void invalidPasswordCourierLogin() {
        CourierCredentials invalidCredentials = new CourierCredentials(
                testCourier.getLogin(),
                testCourier.getPassword().substring(1)
        );

        ValidatableResponse loginResponse = courierClient.loginCourier(invalidCredentials);
        int loginStatus = loginResponse.extract().statusCode();
        assertEquals("Not valid status code", HTTP_NOT_FOUND, loginStatus);
        String loginResponseMessage = loginResponse.extract().path("message");
        assertEquals("Not valid response.", "Учетная запись не найдена", loginResponseMessage);
    }

    @Test
    public void missingPasswordCourierLogin() {
        CourierCredentials invalidCredentials = new CourierCredentials(
                testCourier.getLogin(),
                null
        );

        ValidatableResponse loginResponse = courierClient.loginCourier(invalidCredentials);
        int loginStatus = loginResponse.extract().statusCode();
        assertEquals("Not valid status code", HTTP_BAD_REQUEST, loginStatus);
        String loginResponseMessage = loginResponse.extract().path("message");
        assertEquals("Not valid response.", "Недостаточно данных для входа", loginResponseMessage);
    }

    @Test
    public void missingLoginCourierLogin() {
        CourierCredentials invalidCredentials = new CourierCredentials(
                null,
                testCourier.getPassword()
        );

        ValidatableResponse loginResponse = courierClient.loginCourier(invalidCredentials);
        int loginStatus = loginResponse.extract().statusCode();
        assertEquals("Not valid status code", HTTP_BAD_REQUEST, loginStatus);
        String loginResponseMessage = loginResponse.extract().path("message");
        assertEquals("Not valid response.", "Недостаточно данных для входа", loginResponseMessage);
    }
}
