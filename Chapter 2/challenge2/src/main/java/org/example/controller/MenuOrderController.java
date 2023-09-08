package org.example.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.model.OrderItem;
import org.example.service.MenuService;
import org.example.repository.MenuRepository;
import org.example.model.MenuItem;

import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuOrderController {
    @Setter
    @Getter
    private static MenuService menuService;
    public static Scanner input = new Scanner(System.in);

    public MenuOrderController(MenuService menuService) {
        MenuOrderController.setMenuService(menuService);
    }

    //method showmenu, untuk menampilkan menu menggunakan array, memanggil object Menu dari class MenuItem
    public static void showMenu() {
        List<MenuItem> menu = getMenuService().getMenu();
        System.out.println("Daftar Menu Makanan:");

        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.get(i);
            System.out.println((i + 1) + ". " + menuItem.getName() + " | Rp " + menuItem.getPrice());
        }
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. untuk keluar aplikasi");
        processOrder();
    }

    private static void konfirmasipembayaran() {
        System.out.println("===========================");
        System.out.println(" Konfirmasi dan Pembayaran ");
        System.out.println("===========================");
        for (OrderItem order : getMenuService().getOrder()) { //menampilkan pesanan yang dipilih berupa nama menu, jumlah, dan harganya
            System.out.println(order.getMenuItem().getName() + "    " + order.getQty() + " Rp" + order.getsubPrice());
        }
        System.out.println("----------------------------- +");
        System.out.println("Total          " + getMenuService().totalQuantity() + " Rp" + getMenuService().totalPrice()); //mengambil total jumlah dan harga
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");
        System.out.print("Pilihan=> "); //memasukkan inputan baru
        int choice2 = input.nextInt();

        if (choice2 == 1) { //jika pilihan 1 maka akan memanggil method struktext dan readtrukpembayaran
            System.out.println();
            System.out.println("Struk Pembayaran kamu sudah disimpan dalam struk-pembayaran.txt");
            struktext(); //membuat struk sesuai pesanan
            readstrukpembayaran(); //menampilkan struk yang sudah dibuat
        } else if (choice2 == 2) { //jika pilihan 2 maka akan kembali ke menu utama
            showMenu();
        } else if (choice2 == 0) { //jika piliihan 0 maka akan keluar dari program
            System.exit(0);
        }
    }

    private static void struktext() {
        try {
            FileWriter struk = new FileWriter("struk-pembayaran.txt");
            struk.write("=====================================\n");
            struk.write("BinarFud\n");
            struk.write("=====================================\n");
            struk.write("Terimakasih sudah memesan di BinarFud\n");
            struk.write("\n");
            struk.write("Dibawah ini adalah pesanan anda\n");
            struk.write("\n");
            for (OrderItem order : getMenuService().getOrder()) { //menampilkan pesanan yang dipilih berupa nama menu, jumlah, dan harganya
                struk.write(order.getMenuItem().getName() + "    " + order.getQty() + " Rp" + order.getsubPrice() + "\n");
            }
            struk.write("----------------------------- +\n");
            struk.write("Total           " + getMenuService().totalQuantity() + " Rp" + getMenuService().totalPrice()); //mengambil total jumlah dan harga
            struk.write("\n");
            struk.write("\n");
            struk.write("Pembayaran : BinarCash");
            struk.write("\n");
            struk.write("=========================================\n");
            struk.write("Simpan Struk Ini Sebagai Bukti Pembayaran\n");
            struk.write("=========================================\n");
            struk.flush();
            struk.close();
        } catch (IOException e) {
            System.out.println("Struk gagal dicetak" + e.getMessage());
        }
    }

    //membaca file struk-pembayaran.txt dengan menggunakan filereader
    public static void readstrukpembayaran() {
        try {
            File file = new File("D:\\Kuliah\\sib binar\\Chapter 2\\challenge2\\struk-pembayaran.txt"); //lokasi file
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(",");
                for (String tempStr : tempArr) {
                    System.out.println(tempStr);
                }
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    public static void main(String[] args) {
        MenuRepository foodRepository = new MenuRepository();
        MenuService foodService = new MenuService(foodRepository);
        MenuOrderController controller = new MenuOrderController(foodService);

        System.out.println("Nama : Tsabit Gholib");
        System.out.println("Kelas : BEJ-1");
        System.out.println("==========================");
        System.out.println("Selamat Datang di BinarFud");
        System.out.println("==========================");
        showMenu();
        processOrder();
    }

    //method processOrder, untuk melakukan proses menambahkan menu yang dipilih ke order
    public static void processOrder(){
        List<MenuItem> menu = getMenuService().getMenu();
        try {
            while (true) {
                System.out.println();
                System.out.print("Pilih =>");
                int choice = input.nextInt();
                if (choice == 0) { //jika pilihan 0 maka akan mengakhiri program dan keluar dari program
                    System.exit(0);
                } else if (choice > 0 && choice <= menu.size()) { //jika pilihan 1-jumlah menu, maka akan menampilkan detail menu dan harganya
                    try { //exception handling
                        System.out.println("=====================");
                        System.out.println("Berapa pesanan anda");
                        System.out.println("=====================");
                        System.out.println(menu.get(choice - 1).getName() + " " + menu.get(choice - 1).getPrice());  //menu dan harga
                        System.out.print("qty=> ");
                        int qty = input.nextInt();
                        getMenuService().addOrder(choice - 1, qty);//menambahkan menu yang dipilih dan jumlahnya kedalam class pesanan
                        if (qty <= 0) {
                            throw new Exception(); //jika jumlah <=0 maka akan error dan dilempar ke exception
                        }
                    } catch (Exception e) { //menangkap exception
                        System.out.println("===========================");
                        System.out.println("Minimal 1 Jumlah Pesanan!!!");
                        System.out.println("===========================");
                    }
                } else if (choice == 99) { //jika pilihan 99 maka akan memanggil method konfirmasi pembayaran
                    konfirmasipembayaran();
                } else { //input diatas angka 5
                    System.out.println("Pilihan anda : "+choice+" belum ada di menu, pilih menu lain!");
                }
            }
        } catch (InputMismatchException ime) { //menangkap exception jika inputan bukan nilai positif atau integer
            System.out.println("============================");
            System.out.println("Mohon masukkan pilihan anda!");
            System.out.println("============================");
        }
        System.out.println("(y) Untuk lanjut");
        System.out.println("(n) Untuk keluar");
        input.nextLine();
            System.out.print("=> ");
            String choice3 = input.nextLine();
            if (choice3.equals("y")) {
                showMenu();
            } else if (choice3.equals("n")) {
                System.exit(0);
            } else {
                System.out.println();
            }
        }

}