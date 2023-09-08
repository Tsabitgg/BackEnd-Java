package org.example.repository;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.example.model.MenuItem;
@Getter
public class MenuRepository {
    //mengambil MenuItem dari model.MenuItem
    public List<MenuItem> menu;

    public MenuRepository() {
        menu = new ArrayList<>();
        // daftar menu, menggunakan variabel name dan price dari MenuItem
        menu.add(new MenuItem("Nasi Goreng ", 15000));
        menu.add(new MenuItem("Mie Goreng  ", 13000));
        menu.add(new MenuItem("Nasi + Ayam ", 18000));
        menu.add(new MenuItem("Es teh manis", 3000));
        menu.add(new MenuItem("Es Jeruk    ", 5000));
    }
}