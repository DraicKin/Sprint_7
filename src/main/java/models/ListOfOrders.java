package models;

public class ListOfOrders {
    Order[] orders;
    PageInfo pageInfo;
    Station[] availableStations;

    public ListOfOrders(Order[] orders, PageInfo pageInfo, Station[] availableStations) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
    }

    public ListOfOrders() {
    }

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Station[] getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(Station[] availableStations) {
        this.availableStations = availableStations;
    }
}
