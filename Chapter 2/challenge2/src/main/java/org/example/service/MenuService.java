package org.example.service;

import lombok.Getter;
import org.example.model.MenuItem;
import org.example.model.OrderItem;
import org.example.repository.MenuRepository;
import java.util.ArrayList;
import java.util.List;

public class MenuService {
    private MenuRepository menuRepository;
    @Getter
    //array order (menu yang dipilih/pesan
    private List<OrderItem> order;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
        this.order = new ArrayList<>();
    }

    public List<MenuItem> getMenu() {
        return menuRepository.getMenu();
    }

    //menambahkan menu yg MenuItem yang dipilih ke variabel order
    public void addOrder(int menuItemIndex, int qty) {
        if (menuItemIndex >= 0 && menuItemIndex < getMenu().size() && qty > 0) {
            MenuItem menuItem = getMenu().get(menuItemIndex);
            order.add(new OrderItem(menuItem, qty));
            System.out.println("Menu berhasil ditambahkan ke pesanan");
        } else {
            System.out.println();
        }
    }

    public int totalQuantity(){
        int totalQuantity = 0;
        for (OrderItem orders : order){
            totalQuantity = totalQuantity+ orders.getQty(); //menjumlahkan total semua jumlah pesanan pada masing2 menu
        }
        return totalQuantity;
    }

    public double totalPrice() {
        double totalPrice = 0;
        for (OrderItem item : order) {
            totalPrice += item.getsubPrice(); //menjumlahkan total semua harga dari masing2 sub harga menu yang dipilih
        }
        return totalPrice;
    }
}
