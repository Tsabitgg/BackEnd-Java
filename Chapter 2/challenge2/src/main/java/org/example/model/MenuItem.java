package org.example.model;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuItem {
    private String name;
    private double price;

    //object MenuItem yang akan dijadikan arraylist pada repository
    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}