import static org.junit.jupiter.api.Assertions.*;

import org.example.controller.MenuOrderController;
import org.example.model.MenuItem;
import org.example.model.OrderItem;
import org.example.repository.MenuRepository;
import org.example.service.MenuService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestMenuOrderController {

    private MenuService menuService;
    private ByteArrayOutputStream outputStream;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        menuService = new MenuService(new MenuRepository());
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    public void testAddOrderValidInput() {
        int initialOrderSize = menuService.getOrder().size(); // Ukuran awal daftar pesanan
        menuService.addOrder(0, 2); // Contoh input yang valid
        assertEquals(initialOrderSize + 1, menuService.getOrder().size()); // Memastikan pesanan telah ditambahkan
    }

    @Test
    public void testAddOrderInvalidInput() {
        int initialOrderSize = menuService.getOrder().size(); // Ukuran awal daftar pesanan
        menuService.addOrder(-1, 3); // Contoh input yang tidak valid
        assertEquals(initialOrderSize, menuService.getOrder().size());
    }

    @Test
    public void testTotalQuantityWithNonEmptyOrder() {
        // Pengujian saat daftar pesanan (order) tidak kosong
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(new MenuItem("Menu1", 10), 2));
        orderItems.add(new OrderItem(new MenuItem("Menu2", 20), 3));

        menuService.setOrder(orderItems);

        int totalQty = menuService.totalQuantity();
        assertEquals(5, totalQty); // Menyesuaikan dengan hasil yang diharapkan
    }

    @Test
    public void testTotalPriceWithNonEmptyOrder() {
        // Pengujian saat daftar pesanan (order) tidak kosong
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(new MenuItem("Nasi Goreng", 15000), 1));
        orderItems.add(new OrderItem(new MenuItem("Es Jeruk", 5000), 2));

        menuService.setOrder(orderItems);

        double price = menuService.totalPrice();
        assertEquals(25000, price); // Menyesuaikan dengan hasil yang diharapkan
    }

    @Test
    public void testKonfirmasiPembayaran() {
        // Simulasi input
        String simulatedInput = "1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        MenuOrderController.konfirmasipembayaran();

        String expectedOutput = "===========================\n"+
                " Konfirmasi dan Pembayaran \n"+
                "===========================\n";

        assertEquals(expectedOutput, outContent.toString());
    }


    @Test
    public void testStruktext() {
        // Persiapan objek pesanan
        menuService.getOrder().add(new OrderItem(new MenuItem("Nasi Goreng", 15000), 2));
        menuService.getOrder().add(new OrderItem(new MenuItem("Mie Goreng", 13000), 4));

        System.setOut(new PrintStream(outputStream));

        MenuOrderController.setMenuService(menuService);
        MenuOrderController.struktext();

        System.setOut(originalOut);

        String expectedOutput = "=====================================\r\n" +
                "BinarFud\r\n" +
                "=====================================\r\n" +
                "Terimakasih sudah memesan di BinarFud\r\n" +
                "\r\n" +
                "Dibawah ini adalah pesanan anda\r\n" +
                "\r\n" +
                "Nasi Goreng    2 Rp30000.0\r\n" +
                "Mie Goreng    4 Rp52000.0\r\n" +
                "----------------------------- +\r\n" +
                "Total           6 Rp82000.0\r\n" +
                "\r\n" +
                "Pembayaran : BinarCash\r\n" +
                "=========================================\r\n" +
                "Simpan Struk Ini Sebagai Bukti Pembayaran\r\n" +
                "=========================================\r\n";

        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testReadstrukpembayaran() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        MenuOrderController.readstrukpembayaran();

        // Assert
        String actualOutput = outContent.toString().trim();
        if (actualOutput.isEmpty()) {
            fail("Actual output is empty.");
        } else {
            String expectedOutput = "=====================================\r\n" +
                    "BinarFud\r\n" +
                    "=====================================\r\n" +
                    "Terimakasih sudah memesan di BinarFud\r\n" +
                    "\r\n" +
                    "Dibawah ini adalah pesanan anda\r\n" +
                    "\r\n" +
                    "Nasi Goreng    2 Rp30000.0\r\n" +
                    "Mie Goreng    4 Rp52000.0\r\n" +
                    "----------------------------- +\r\n" +
                    "Total           6 Rp82000.0\r\n" +
                    "\r\n" +
                    "Pembayaran : BinarCash\r\n" +
                    "=========================================\r\n" +
                    "Simpan Struk Ini Sebagai Bukti Pembayaran\r\n" +
                    "=========================================\r\n";
            assertEquals(expectedOutput.trim(), actualOutput);
            File file = new File("D:\\Kuliah\\sib binar\\Chapter 2\\challenge2\\struk-pembayaran.txt");
            assertTrue(file.exists());
        }
        System.setOut(System.out);
    }
}