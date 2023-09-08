package org.example.model;
import lombok.Data;

@Data
public class MenuItem {
    private String name;
    private double price;

    //object MenuItem yang akan dijadikan arraylist pada repository
    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
