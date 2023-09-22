package org.example.service;

import lombok.Getter;
import lombok.Setter;
import org.example.model.MenuItem;
import org.example.model.OrderItem;
import org.example.repository.MenuRepository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MenuService {
    private MenuRepository menuRepository;
    //array order (menu yang dipilih/pesan)
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
            getMenu().stream()
                    .skip(menuItemIndex) //melewati menuItemIndex untuk langsung mengambil object order item(menu(nama harga) dan jumlah)
                    .findFirst()
                    .ifPresent(menuItem -> { //jika item sesuai maka akan diambil
                        order.add(new OrderItem(menuItem, qty));
                        System.out.println("Menu berhasil ditambahkan ke pesanan");
                    });
        } else {
            System.out.println();
        }
    }

    public int totalQuantity() {
        return order.stream()
                .mapToInt(OrderItem::getQty) //mapToInt untuk mengambil qty pada  masing2 order
                .sum(); //dijumlahkan
    }

    public double totalPrice() {
        return order.stream()
                .mapToDouble(OrderItem::getsubPrice) // Mengambil subPrice dari setiap OrderItem
                .sum(); // Menjumlahkan total harga
    }
}