package com.binaracademy.challenge4.Controller;

import com.binaracademy.challenge4.Model.*;
import com.binaracademy.challenge4.Service.MerchantService;
import com.binaracademy.challenge4.Service.OrdersService;
import com.binaracademy.challenge4.Service.ProductService;
import com.binaracademy.challenge4.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
public class ProductController {
    @PostConstruct
    public void init() throws ParseException {
        this.mainMenu();
    }

    private Scanner scanner = new Scanner(System.in);

    @Autowired
    private ProductService productService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserService userService;
    @Autowired
    private MerchantController merchantController;
    @Autowired
    private UserController userController;

    public static final String SEPARATOR = "==================";


    public void mainMenu() throws ParseException {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("Binarfud\n" +
                SEPARATOR + "\n" +
                "Silahkan pilih menu\n" +
                "1. Merchant\n" +
                "2. Produk\n" +
                "3. User\n" +
                "4. Order\n" +
                "0. Keluar");

        System.out.print("=> ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        switch (pilihan) {
            case 1:
                merchantController.mainMerchant();
                break;
            case 2:
                mainProduct();
                break;
            case 3:
                userController.mainUser();
                break;
            case 4:
                addNewOrder();
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Pilih dengan benar!!!");
                mainMenu();
        }
    }

    public void mainProduct() throws ParseException {
        log.info("Menampilkan Menu Utama");

        System.out.println(SEPARATOR);
        System.out.println("Binarfud Product\n" +
                SEPARATOR + "\n" +
                "Silahkan pilih menu\n" +
                "1. Lihat Product\n" +
                "2. Tambah Produk\n" +
                "3. Edit Produk\n" +
                "4. Hapus Product\n" +
                "99. Kembali ke Menu Utama");

        System.out.print("=> ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        switch (pilihan) {
            case 1:
                showProductWithPagination(1, 2);
                break;
            case 2:
                this.addNewProduct();
                break;
            case 3:
                this.updateProduct();
                break;
            case 4:
                showProduct(productService.getAllProducts());
                deleteProduct();
            case 99:
                this.mainMenu();
            default:
                System.out.println("Pilihan dengan benar\n!!!!!!!");
                this.mainProduct();
        }
        System.out.println();
    }

    public void showProduct(List<Product> products) {
        log.info("Menampilkan Product yang tersedia");
        System.out.println("No |\tProduct Name\t|\tPrice\t|\tMerchant");
        products.forEach(product -> {
            System.out.println(product.getProductCode() +
                    ". "+ product.getProductName() +
                    "\t|\t" + product.getPrice() +
                    "\t|\t" + product.getMerchant());
        });
    }

    public void showProductWithPagination(int page, int pageSize) throws ParseException {
        System.out.println("Daftar menu makanan - Halaman " + page);

        List<Product> products = productService.getAllProducts();

        int totalProducts = products.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        if (page < 1 || page > totalPages) {
            System.out.println("Halaman tidak valid.");
            return;
        }

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalProducts);

        List<Product> productsToDisplay = products.subList(startIndex, endIndex);

        showProduct(productsToDisplay);

        System.out.println("Halaman " + page + " dari " + totalPages);

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
                    log.info("Beralih ke halaman selanjutnya");
                    showProductWithPagination(page + 1, pageSize);
                } else {
                    System.out.println("Anda sudah berada di halaman terakhir.");
                    showProductWithPagination(page, pageSize);
                }
                break;
            case 2:
                if (page > 1) {
                    log.info("Kembali ke halaman sebelumnya");
                    showProductWithPagination(page - 1, pageSize);
                } else {
                    System.out.println("Anda sudah berada di halaman pertama.");
                    showProductWithPagination(page, pageSize);
                }
                break;
            case 0:
                mainProduct();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                showProductWithPagination(page, pageSize);
                break;
        }
    }


    public void addNewProduct() throws ParseException {
        log.info("Memulai proses penambahan produk baru.");

        System.out.print("Product Name :");
        String productName = scanner.nextLine();
        System.out.print(("Product Price : "));
        Double price = scanner.nextDouble();
        System.out.print("Merchant Code : ");
        int merchantCode = scanner.nextInt();

        List<Merchant> merchantList = merchantService.getAllMerchants();
        Merchant merchant = merchantList.get(merchantCode -1);

        Product newProduct = Product.builder()
                .productName(productName)
                .price(price)
                .merchant(merchant)
                .build();
        productService.addProduct(newProduct);
        System.out.println("Produk baru berhasil ditambahkan ");
        log.info("Produk baru berhasil ditambahkan");
        System.out.println();
        this.mainProduct();
    }

    public void updateProduct() throws ParseException {
        log.info("Melakukan Update Product");

        showProduct(productService.getAllProducts());
        System.out.print("Masukkan kode produk : ");
        Long productCode = scanner.nextLong();
        Optional<Product> existingProduct = Optional.ofNullable(productService.getProductByCode(productCode));

        if (existingProduct.isPresent()) {
            Product existing = existingProduct.get();

            scanner.nextLine();
            System.out.print("Nama Product : ");
            String newProductName = scanner.nextLine();
            System.out.print("Harga Produk : ");
            Double newPrice = scanner.nextDouble();

            // Update produk yang ada dengan data baru
            existing.setProductName(newProductName);
            existing.setPrice(newPrice);

            // Simpan perubahan (jika diperlukan)
            productService.updateProduct(existing);

            System.out.println("Produk berhasil diperbarui.");
        } else {
            System.out.println("Produk dengan kode " + productCode + " tidak ditemukan.");
        }
        log.info("Berhasil mengupdate product");
        System.out.println();
        mainProduct();
    }

    public void deleteProduct() throws ParseException {
        System.out.print("Masukkan kode produk yang akan dihapus: ");
        Long productCode = scanner.nextLong();
        Optional<Product> existingProduct = Optional.ofNullable(productService.getProductByCode(productCode));

        if (existingProduct.isPresent()) {
            Product existing = existingProduct.get();

            productService.deleteProduct(existing.getProductCode());

            System.out.println("Produk berhasil dihapus.");
        } else {
            System.out.println("Produk dengan kode " + productCode + " tidak ditemukan.");
        }
        log.info("Berhasil Menghapus Product yang dipilih");
        System.out.println();
        mainProduct();
    }


    //ORDER CLASS, gk dipisah soalnya bingung kena dependency circular

    public void addNewOrder() throws ParseException {
        log.info("Menambahkan orderan baru");

        System.out.print("Order Time : ");
        String newOrderTime = scanner.nextLine();
        System.out.print("Destination Address : ");
        String newDestinationAddress = scanner.nextLine();
        System.out.print("Users Id : ");
        int usersId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Completed (true/false): ");
        boolean newCompleted = scanner.nextBoolean();

        List<Users> usersList = userService.getAllUsers();
        Users users = usersList.get(usersId - 1);

        Orders newOrders = Orders.builder()
                .orderTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(newOrderTime))
                .destinationAddress(newDestinationAddress)
                .users(users)
                .completed(newCompleted)
                .build();

        ordersService.addNewOrders(newOrders);
        System.out.println();
        addOrderDetails();
    }

    public void addOrderDetails() throws ParseException {
        System.out.println("Silahkan pilih menu pesanan : ");
        List<Product> productList = productService.getAllProducts();
        showProduct(productList);

        try {
            while (true) {
                System.out.print("Masukkan kode produk: ");
                int productCode = scanner.nextInt();
                if (productCode == 0) {
                    System.exit(0);
                } else if (productCode > 0 && productCode <= productList.size()) {
                    try {
                        Product product = productList.get(productCode - 1);
                        System.out.println("=====================");
                        System.out.println("Berapa pesanan anda");
                        System.out.println("=====================");
                        System.out.print("qty=> ");
                        int qty = scanner.nextInt();
                        Optional<Product> selectedProduct = Optional.ofNullable(product);
                        selectedProduct.ifPresent(item -> {

                            if (qty > 0) {
                                double subtotalPrice = product.getPrice() * qty;

                                OrdersDetail newOrdersDetail = OrdersDetail.builder()
                                        .product(product)
                                        .quantity(qty)
                                        .totalPrice(subtotalPrice)
                                        .build();

                                ordersService.addnewOrdersDetail(newOrdersDetail);
                                System.out.println("Pesanan Berhasil ditambahkan");
                            } else {
                                System.out.println("===========================");
                                System.out.println("Minimal 1 Jumlah Pesanan!!!");
                                System.out.println("===========================");
                            }
                        });
                    } catch (Exception e) {
                        log.error("Terjadi kesalahan saat menambahkan pesanan!", e);
                        System.out.println("===========================");
                        System.out.println("Terjadi kesalahan saat menambahkan pesanan!");
                        System.out.println("===========================");
                    }
                } else if (productCode == 99) {
                    confirmPayment();
                } else {
                    System.out.println("Pilihan anda : " + productCode + " belum ada di menu!!!");
                }
            }
        } catch (InputMismatchException ime) {
            log.warn("Terjadi kesalahan : "+ ime.getMessage());
            System.out.println(SEPARATOR);
            System.out.println("Mohon masukkan pilihan anda!");
            System.out.println(SEPARATOR);
        } catch (Exception e) {
            log.error("Terjadi kesalahan saat melakukan order"+ e);
            throw new RuntimeException(e);
        }
        System.out.println("(y) untuk lanjut");
        System.out.println("(n) Untuk keluar");
        scanner.nextLine();
        System.out.print("=> ");
        String choice3 = scanner.nextLine();
        if (choice3.equals("y")) {
            addNewOrder();
        } else if (choice3.equals("n")) {
            System.exit(0);
        } else {
            System.out.println();
        }
    }

    public void confirmPayment() throws ParseException {
        log.info("Melakukan konfirmasi pembayaran");
        System.out.println("===========================");
        System.out.println(" Konfirmasi dan Pembayaran ");
        System.out.println("===========================");

        List<OrdersDetail> orderDetails = ordersService.getAllOrdersDetail();

        if (orderDetails.isEmpty()) {
            System.out.println("Belum ada pesanan yang ditambahkan.");
        } else {
            System.out.println("Detail Pesanan:");

            double total = orderDetails.stream()
                    .mapToDouble(orderDetail -> {
                        System.out.println(orderDetail.getProduct()+ "    " + orderDetail.getQuantity() + " Rp" + orderDetail.getTotalPrice());
                        return orderDetail.getTotalPrice();
                    })
                    .sum();

            System.out.println("----------------------------- +");
            System.out.println("Total          Rp" + total);
            System.out.println("1. Konfirmasi dan Bayar");
            System.out.println("2. Kembali ke menu utama");
            System.out.println("0. Keluar aplikasi");
            System.out.print("Pilihan=> ");

            Scanner input = new Scanner(System.in);
            int choice2 = input.nextInt();

            if (choice2 == 1) {
                System.out.println();
                System.out.println("Struk Pembayaran kamu sudah disimpan dalam struk-pembayaran.txt");
                strukText();
                readStrukPembayaran();
            } else if (choice2 == 2) {
                mainMenu();
            } else if (choice2 == 0) {
                System.exit(0);
            }
        }
    }


    public void strukText(){
        List<OrdersDetail> orderDetails = ordersService.getAllOrdersDetail();
        try {
            FileWriter struk = new FileWriter("struk-pembayaran.txt");
            struk.write("=====================================\n");
            struk.write("BinarFud\n");
            struk.write("=====================================\n");
            struk.write("Terimakasih sudah memesan di BinarFud\n");
            struk.write("\n");
            struk.write("Dibawah ini adalah pesanan anda\n");
            struk.write("\n");

            AtomicReference<Double> total = new AtomicReference<>((double) 0);
            orderDetails.forEach(orderDetail -> {
                System.out.println(orderDetail.getProduct() + "    " + orderDetail.getQuantity() + " Rp" + orderDetail.getTotalPrice());
                total.updateAndGet(v -> new Double((double) (v + orderDetail.getTotalPrice())));
            });

            struk.write("----------------------------- +");
            struk.write("Total          Rp" + total);
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

    public static void readStrukPembayaran() {
        try {
            File file = new File("D:\\Kuliah\\sib binar\\Chapter 4\\challenge4\\struk-pembayaran.txt"); //lokasi file
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
}