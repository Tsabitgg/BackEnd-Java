package Challenge1; //package

import java.io.*;
import java.util.Scanner;

public class Challenge1 {
    //deklarasi variabel global, agar bisa digunakan diluar method main
    //deklarasi array dengan variabel menu
    private static String[] menu = {"Nasi Goreng ", "Mie Goreng  ", "Nasi + Ayam ", "Es teh manis", "Es Jeruk    "};
    //deklarasi array dengan variabel harga
    private static double[] harga = {15000, 13000, 18000, 3000, 5000};
    //deklarasi array dengan variabel jumlah
    private static int[] jumlah = new int[menu.length];
    //untuk input
    private static Scanner input = new Scanner(System.in);

    //class main
    public static void main(String[] args) {
        //inisialisasi identitas
        System.out.println("Nama : Tsabit Gholib");
        System.out.println("Kelas : BEJ-1");
        System.out.println("============================");
        System.out.println(" Selamat Datang di BinarFud");
        System.out.println("============================");
        System.out.println("Silahkan pilih Makanan:");
        showmenu();
    }
    //method showmenu, untuk menampilkan daftar menu menggunakan array dengan variabel menu dan harga
    private static void showmenu() {
        for (int i = 0; i < menu.length; i++) { //i = 0, i kurang dari panjang array variabel menu, i increment
            System.out.println((i + 1) + ". " + menu[i] + "  |" + " Rp" + harga[i]); //menampilkan data array menu dan harga sesuai angka i++
        }
        System.out.println("99.Pesan dan Bayar");
        System.out.println("0. Untuk keluar aplikasi");

        while (true) { //menggunakan perulangan while karena jumlah loopingnya tidak ditentukan pasti, tergantung user
            System.out.println();
            System.out.print("Pilih=> ");
            int pilih = input.nextInt(); //input pilih untuk memilih menu yang akan dipesan
            if (pilih == 0) {
                System.exit(0);  //jika input 0 maka program berhenti dan akan keluar dari program
            } else if (pilih == 99) {
                konfirmasipembayaran(); //jika input 99 maka akan masuk ke method konfirmasipembayaran()
            } else if (pilih > 0 && pilih <= menu.length) { //jika input 1 <= panjang array maka akan menampilkan :
                System.out.println("=======================");
                System.out.println("Berapa pesanan anda");
                System.out.println("=======================");
                System.out.println(menu[pilih - 1] +"  Rp"+ harga[pilih - 1]); //menampilkan menu dan harga array ke [pilih -1], di -1 karena diatas di +1
                System.out.print("qty=>  ");
                int quantity = input.nextInt(); //input quantity
                jumlah[pilih - 1] += quantity; //jumlah[pilih-1] = 0, kemudian di + dengan inputan quantity
            } else {
                System.out.println("Pilhan tidak valid");
            }
        }
    }
    //method konfirmasipembayaran, untuk menampilkan detail menu, jumlah dan harga yang dipilih
    public static void konfirmasipembayaran() {
        System.out.println("=============================");
        System.out.println(" Konfirmasi dan Pembayaran ");
        System.out.println("=============================");
        double totalharga = 0;
        int totaljumlah = 0;
        for (int i = 0; i < menu.length; i++) {
            if (jumlah[i] > 0) { //jika jumlah lebih dari 0
                double subtotal = harga[i] * jumlah[i]; //harga awal akan dikali dengan inputan jumlah dan dimasukkan variabel subtotal
                totalharga += subtotal; //totalharga = totalharga(0)+subtotal
                totaljumlah += jumlah[i]; //totaljumlah = totaljumlah(0)+semua jumlah yang sudah diinputkan
                System.out.println(i+1 +". "+ menu[i] +" "+ jumlah[i] + " Rp" + subtotal); //menampilkan nomor, menu pilihan, jumlah, dan subtotal(harga)
            }
        }
        System.out.println("----------------------------- +");
        System.out.println("Total           " + totaljumlah + " Rp" + totalharga); //mengambil total jumlah dan harga
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");
        System.out.print("Pilihan=> "); //memasukkan inputan baru
        int pilihan = input.nextInt();

        if (pilihan == 1) { //jika pilihan 1 maka akan memanggil method struktext dan readtrukpembayaran
            System.out.println();
            System.out.println("Struk Pembayaran kamu sudah disimpan dalam struk-pembayaran.txt");
            createstrukpembayaran(); //membuat struk sesuai pesanan pada konfirmasipembayaran
            readstrukpembayaran();//menampilkan struk yang sudah dibuat
        } else if (pilihan == 2) {
            showmenu(); //jika pilihan 2 maka akan kembali ke menu utama
        } else if (pilihan == 0) {
            System.exit(0); //jika piliihan 0 maka akan keluar dari program
        }
    }
    //method createstrukpembayaran, membuat struk-pembayaran.txt dengan menggunakan filewriter
    private static void createstrukpembayaran(){
        try{
            FileWriter struk = new FileWriter("struk-pembayaran.txt"); //menggunakan standar class Filewriter
            struk.write("=======================================\n");
            struk.write("BinarFud\n");
            struk.write("=======================================\n");
            struk.write("Terimakasih sudah memesan di BinarFud\n");
            struk.write("\n");
            struk.write("Dibawah ini adalah pesanan anda\n");
            struk.write("\n");
            double totalharga = 0;
            int totaljumlah = 0;
            for (int i = 0; i < menu.length; i++) {
                if (jumlah[i] > 0) { //jika jumlah lebih dari 0
                    double subtotal = harga[i] * jumlah[i]; //harga awal akan dikali dengan inputan jumlah dan dimasukkan variabel subtotal
                    totalharga += subtotal; //totalharga = totalharga(0)+subtotal
                    totaljumlah += jumlah[i]; //totaljumlah = totaljumlah(0)+semua jumlah yang sudah diinputkan
                    struk.write(i+1 +". "+ menu[i] +" "+ jumlah[i] + " Rp" + subtotal+"\n"); //menampilkan nomor, menu pilihan, jumlah, dan subtotal(harga)
                }
            }
            struk.write("----------------------------- +\n");
            struk.write("Total           " + totaljumlah + " Rp" + totalharga); //mengambil total jumlah dan harga
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
            throw new RuntimeException(e);
        }
    }
    //method readstrukpembayaran, membaca file struk-pembayaran.txt dengan menggunakan filereader
    public static void readstrukpembayaran(){
        try{
            File file = new File("D:\\Kuliah\\sib binar\\Chapter 1\\Challenge1\\struk-pembayaran.txt"); //lokasi file
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            String[] tempArr;
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                for(String tempStr : tempArr) {
                    System.out.println(tempStr);
                }
            }
            br.close();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}