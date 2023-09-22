package org.example.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.service.MenuService;
import org.example.repository.MenuRepository;
import org.example.model.MenuItem;

import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MenuOrderController {
    @Setter
    @Getter
    private static MenuService menuService;
    public static Scanner input = new Scanner(System.in);

    public MenuOrderController(MenuService menuService) {
        MenuOrderController.setMenuService(menuService);
    }

    //method showmenu, menggunakan lambda ekspresion untuk looping menampilkan menu dari repository
    public static void showMenu() {
        List<MenuItem> menu = getMenuService().getMenu();
        System.out.println("Daftar Menu Makanan:");

        IntStream.range(0, menu.size()).forEach(index -> { //membuat stream bilangan bulat dengan rentang indeks dari 0 hingga menu.size()
            MenuItem item = menu.get(index); //mengambil menu sesuai index
            System.out.println(index+1+". "+ item.getName()+"  |  "+"Rp"+item.getPrice());
        });

        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. untuk keluar aplikasi");
        processOrder();
    }

    //method processOrder, untuk melakukan proses menambahkan menu yang dipilih ke order
    public static void processOrder() {
        List<MenuItem> menu = getMenuService().getMenu();
        Scanner input = new Scanner(System.in);

        try {
            while (true) {
                System.out.println();
                System.out.print("Pilih =>");
                int choice = input.nextInt();
                if (choice == 0) {
                    System.exit(0);
                } else if (choice > 0 && choice <= menu.size()) {
                    try {
                        System.out.println("=====================");
                        System.out.println("Berapa pesanan anda");
                        System.out.println("=====================");
                        MenuItem menuItem = menu.get(choice - 1);
                        System.out.println(menuItem.getName() + " " + menuItem.getPrice());
                        System.out.print("qty=> ");
                        int qty = input.nextInt();
                        Optional<MenuItem> selectedMenuItem = Optional.ofNullable(menuItem);//Jika menuItem tidak null, maka selectedMenuItem akan berisi nilai menuItem. Jika menuItem null, maka selectedMenuItem akan berisi Optional.empty().
                        selectedMenuItem.ifPresent(item -> { //jika tidak null maka akan menjalankan :
                            if (qty > 0) {
                                getMenuService().addOrder(choice - 1, qty);
                            } else {
                                System.out.println("===========================");
                                System.out.println("Minimal 1 Jumlah Pesanan!!!");
                                System.out.println("===========================");
                            }
                        });
                    } catch (Exception e) {
                        System.out.println("===========================");
                        System.out.println("Terjadi kesalahan saat menambahkan pesanan!");
                        System.out.println("===========================");
                    }
                } else if (choice == 99) {
                    konfirmasipembayaran();
                } else {
                    System.out.println("Pilihan anda : " + choice + " belum ada di menu, pilih menu lain!");
                }
            }
        } catch (InputMismatchException ime) {
            System.out.println("============================");
            System.out.println("Mohon masukkan pilihan anda!");
            System.out.println("============================");
        } catch (Exception e) {
            throw new RuntimeException(e);
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


    // Metode getMenuService() dan konfirmasipembayaran() harus diimplementasikan.
    public static void konfirmasipembayaran() {
        System.out.println("===========================");
        System.out.println(" Konfirmasi dan Pembayaran ");
        System.out.println("===========================");

        getMenuService().getOrder().forEach(order -> {
            System.out.println(order.getMenuItem().getName() + "    " + order.getQty() + " Rp" + order.getsubPrice());
        });

        System.out.println("----------------------------- +");
        System.out.println("Total          " + getMenuService().totalQuantity() + " Rp" + getMenuService().totalPrice());
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");
        System.out.print("Pilihan=> ");

        Scanner input = new Scanner(System.in);
        int choice2 = input.nextInt();

        if (choice2 == 1) {
            System.out.println();
            System.out.println("Struk Pembayaran kamu sudah disimpan dalam struk-pembayaran.txt");
            struktext();
            readstrukpembayaran();
        } else if (choice2 == 2) {
            showMenu();
        } else if (choice2 == 0) {
            System.exit(0);
        }
    }

    public static void struktext(){
        try {
            FileWriter struk = new FileWriter("struk-pembayaran.txt");
            struk.write("=====================================\n");
            struk.write("BinarFud\n");
            struk.write("=====================================\n");
            struk.write("Terimakasih sudah memesan di BinarFud\n");
            struk.write("\n");
            struk.write("Dibawah ini adalah pesanan anda\n");
            struk.write("\n");

            // Menggunakan ekspresi lambda untuk menulis pesanan ke file
            getMenuService().getOrder().forEach(order -> {
                try {
                    struk.write(order.getMenuItem().getName() + "    " + order.getQty() + " Rp" + order.getsubPrice() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

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
}