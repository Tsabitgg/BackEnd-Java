package org.example.model;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {
    public MenuItem menuItem;
    private int qty;

    //object menuItem yang akan dipilih dan jumlah
    public OrderItem(MenuItem menuItem, int qty){
        this.menuItem = menuItem;
        this.qty = qty;
    }

    //subprice, mengambil harga menu yg dipilih * jumlah
    public double getsubPrice(){
        return menuItem.getPrice()*qty;
    }
}