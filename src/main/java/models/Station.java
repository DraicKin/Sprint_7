package models;

public class Station {
    private String name;
    private String number;
    private String color;

    public Station(String name, String number, String color) {
        this.name = name;
        this.number = number;
        this.color = color;
    }

    public Station(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
