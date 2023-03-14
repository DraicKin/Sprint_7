package models;

public class PageInfo {
    private int page;
    private int total;
    private int limit;

    public PageInfo(int page, int total, int limit) {
        this.page = page;
        this.total = total;
        this.limit = limit;
    }

    public PageInfo() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
