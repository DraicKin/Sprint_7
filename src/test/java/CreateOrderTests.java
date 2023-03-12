import clients.OrderClient;
import io.restassured.response.ValidatableResponse;
import models.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class CreateOrderTests {
    private Order order;
    OrderClient orderClient;

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {null}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    public CreateOrderTests(String[] color) {
        order = new Order("Kolorad", "Orlov", "Line2",
                "Shipilovskaye", "+79885641232", 5, "08/05/22",
                "vgsnb", color);
    }

    @Test
    public void createValidOrder() {

        ValidatableResponse orderResponse = orderClient.createOrder(order);
        int orderStatus = orderResponse.extract().statusCode();
        assertEquals("Not valid status code", HTTP_CREATED, orderStatus);
        int track = orderResponse.extract().path("track");
        assertNotEquals("track = 0", 0, track);
    }
}
