import clients.OrderClient;
import models.ListOfOrders;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class ListOfOrderTests {

    OrderClient orderClient;
    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void returnsListOfOrders() {
        ListOfOrders listOfOrders = orderClient.getList();
        assertThat(listOfOrders.getOrders().length, greaterThan(0) );
    }
}
