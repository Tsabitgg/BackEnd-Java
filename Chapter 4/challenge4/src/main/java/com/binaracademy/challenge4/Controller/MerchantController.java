package com.binaracademy.challenge4.Controller;

import com.binaracademy.challenge4.Model.Merchant;
import com.binaracademy.challenge4.Model.Product;
import com.binaracademy.challenge4.Model.Users;
import com.binaracademy.challenge4.Service.MerchantService;
import com.binaracademy.challenge4.Service.ProductService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class MerchantController {
    private Scanner scanner = new Scanner(System.in);
    private MerchantService merchantService;
    private ProductController productController;
    public static final String SEPARATOR = "==================";
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    public void mainMerchant() throws ParseException{
        System.out.println(SEPARATOR);
        System.out.println("Binarfud Merchant\n"+
                SEPARATOR+"\n"+
                "1. Lihat semua Merchant\n"+
                "2. Lihat Merchant yang sedang buka\n"+
                "3. Tambah Merchant\n"+
                "4. Edit Merchant\n"+
                "99. Kembali ke Menu Utama");

        System.out.print("=> ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        switch (pilihan) {
            case 1:
                showAllMerchantWithPagination(1, 2);
            case 2:
                showMerchantOpen();
                break;
            case 3:
                addNewMerchant();
                break;
            case 4:
                updateMerchantStatus();
                break;
            case 99:
                productController.mainMenu();
                break;
            default:
                System.out.println("Pilih dengan benar!!!");
                mainMerchant();
        }
    }

    public void showAllMerchantWithPagination(int page, int pageSize) throws ParseException {
        System.out.println("Daftar Semua Merchant - Halaman " + page);

        List<Merchant> merchants = merchantService.getAllMerchants();
        int totalMerchants = merchants.size();

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalMerchants);

        List<Merchant> merchantToDisplay = merchants.subList(startIndex, endIndex);
        System.out.println("No |\tMerchant Name\t|\tLocation\t|\tStatus");
        merchantToDisplay.forEach(merchant -> {
            System.out.println(
                    merchant.getMerchantCode() +
                            ". " + merchant.getMerchantName() +
                            "\t|\t" + merchant.getMerchantLocation() +
                            "\t|\t" + merchant.isOpen());
        });

        // untuk berpindah halaman
        int totalPages = (int) Math.ceil((double) totalMerchants / pageSize);

        System.out.println("Pilih aksi:");
        System.out.println("1. Halaman berikutnya");
        System.out.println("2. Halaman sebelumnya");
        System.out.println("0. Keluar");
        System.out.print("=> ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                if (page < totalPages) {
                    showAllMerchantWithPagination(page + 1, pageSize);
                } else {
                    System.out.println("Anda sudah berada di halaman terakhir.");
                    showAllMerchantWithPagination(page, pageSize);
                }
                break;
            case 2:
                if (page > 1) {
                    showAllMerchantWithPagination(page - 1, pageSize);
                } else {
                    System.out.println("Anda sudah berada di halaman pertama.");
                    showAllMerchantWithPagination(page, pageSize);
                }
                break;
            case 0:
                mainMerchant();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                showAllMerchantWithPagination(page, pageSize);
                break;
        }
    }


    public void showMerchantOpen() throws ParseException {

        System.out.println("Daftar Merchant : ");
        List<Merchant> merchants = merchantService.getOpenMerchants();
        merchants.forEach(merchant ->{
            System.out.println(merchant.getMerchantCode()+"\t|\t"+merchant.getMerchantName()+"\t|\t"+merchant.getMerchantLocation()+"\t|\t"+merchant.isOpen());
        });
        System.out.println("99. Kembali");
        System.out.println("0. untuk keluar aplikasi");
        System.out.println();
        System.out.print("=> ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();

        switch (pilihan){
            case 99:
                mainMerchant();
                break;
            case 0:
                System.exit(0);
        }
    }

    public void addNewMerchant() throws ParseException{
        System.out.print("Nama Merchant :");
        String merchantName = scanner.nextLine();
        System.out.print("Lokasi Merchant : ");
        String merchantLocation = scanner.nextLine();
        System.out.print("Status buka (true/false) : ");
        boolean statusOpen = scanner.nextBoolean();

        Merchant newMerchant = Merchant.builder()
                .merchantName(merchantName)
                .merchantLocation(merchantLocation)
                .open(statusOpen)
                .build();
        merchantService.addMerchant(newMerchant);
        System.out.println("Produk baru berhasil ditambahkan :)");
        mainMerchant();
    }

    public void updateMerchantStatus() throws ParseException{
        System.out.print("Masukkan kode Merchant : ");
        Long merchantCode = scanner.nextLong();
        Optional<Merchant> existingMerchant = Optional.of(merchantService.getMerchantByCode(merchantCode).get());

        Merchant existing = existingMerchant.get();

        System.out.println("Nama Merchant : "+ existing.getMerchantName());
        System.out.println("Lokasi Merchant : "+existing.getMerchantLocation());
        System.out.println("Status Buka : "+existing.isOpen());
        scanner.nextLine();
        System.out.print("Edit Status : ");
        boolean newStatusOpen = scanner.nextBoolean();

        existing.setOpen(newStatusOpen);
        merchantService.updateMerchantStatus(existing);

        System.out.println("Status berhasil di edit");
        System.out.println();
        mainMerchant();
    }
}
