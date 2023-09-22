package org.example.repository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import org.example.model.MenuItem;
@Getter
public class MenuRepository {
    //mengambil MenuItem dari model.MenuItem
    public List<MenuItem> menu;
    // daftar menu, menggunakan variabel name dan price dari MenuItem
    public MenuRepository() {
        menu = Arrays.asList(
                new MenuItem("Nasi Goreng ", 15000),
                new MenuItem("Mie Goreng  ", 13000),
                new MenuItem("Nasi + Ayam ", 18000),
                new MenuItem("Es teh manis", 3000),
                new MenuItem("Es Jeruk    ", 5000)
        );
    }
}