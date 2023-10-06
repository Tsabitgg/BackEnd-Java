package com.binaracademy.challenge4.Controller;

import com.binaracademy.challenge4.Model.Users;
import com.binaracademy.challenge4.Service.UserService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class UserController {
    private Scanner scanner = new Scanner(System.in);
    private UserService userService;
    private ProductController productController;
    public static final String SEPARATOR = "==================";
    public UserController(UserService userService){
        this.userService = userService;
    }

    public void mainUser() throws ParseException {
        System.out.println(SEPARATOR);
        System.out.println("Binarfud User\n"+
                SEPARATOR+"\n"+
                "1. Lihat User\n" +
                "2. Tambah User\n" +
                "3. Edit User\n"+
                "4. Hapus User\n" +
                "99. Kembali ke Menu Utama");

        System.out.print("=> ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        switch(pilihan) {
            case 1:
                showUser(1,2);
                break;
            case 2:
                addNewUser();
                break;
            case 3:
                updateUser();
                break;
            case 4:
                deleteUser();
            case 99:
                productController.mainMenu();
            default:
                System.out.println("Pilihan dengan benar\n!!!!!!!");
                mainUser();
        }
        System.out.println();
    }

    public void showUser(int page, int pageSize) throws ParseException {
        System.out.println("Daftar User - Halaman " + page);

        List<Users> users = userService.getAllUsers();

        int totalUsers = users.size();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        if (page < 1 || page > totalPages) {
            System.out.println("Halaman tidak valid.");
            return;
        }

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalUsers);

        List<Users> usersToDisplay = users.subList(startIndex, endIndex);
        System.out.println("No |\tUsername\t|\tEmail\t|\tPassword");
        usersToDisplay.forEach(user -> {
            System.out.println(user.getUsersId() +
                    " . " + user.getUsersname() +
                    "\t|\t" + user.getEmailAddress() +
                    "\t|\t" + user.getPassword());
        });

        System.out.println("Halaman " + page + " dari " + totalPages);

        // Tambahkan logika untuk berpindah halaman di sini
        System.out.println("Pilih aksi:");
        System.out.println("1. Halaman berikutnya");
        System.out.println("2. Halaman sebelumnya");
        System.out.println("0. Keluar");
        System.out.print("=> ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                if (page < totalPages) {
                    showUser(page + 1, pageSize);
                } else {
                    System.out.println("Anda sudah berada di halaman terakhir.");
                    showUser(page, pageSize);
                }
                break;
            case 2:
                if (page > 1) {
                    showUser(page - 1, pageSize);
                } else {
                    System.out.println("Anda sudah berada di halaman pertama.");
                    showUser(page, pageSize);
                }
                break;
            case 0:
                mainUser();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                showUser(page, pageSize);
                break;
        }
    }


    public void addNewUser() throws ParseException{
        System.out.print("Id User : ");
        Long userId = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Username : ");
        String username = scanner.nextLine();
        System.out.print("Email Address : ");
        String emailAddress = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();

        Users newUsers = Users.builder()
                .usersId(userId)
                .usersname(username)
                .emailAddress(emailAddress)
                .password(password)
                .build();
        userService.addUser(newUsers);
        System.out.println("User baru berhasil ditambahkan :)");
        mainUser();
    }

    public void updateUser() throws ParseException{
        System.out.println("Masukkan kode User : ");
        Long userId = scanner.nextLong();
        Optional<Users> existingUsers = Optional.of(userService.getUserById(userId).get());

        Users existing = existingUsers.get();

        scanner.nextLine();
        System.out.print("Username : ");
        String newUsername = scanner.nextLine();
        System.out.print("Email Address : ");
        String newEmailAddress = scanner.nextLine();
        System.out.print("Password : ");
        String newPassword = scanner.nextLine();

        existing.setUsersname(newUsername);
        existing.setEmailAddress(newEmailAddress);
        existing.setPassword(newPassword);

        userService.updateUser(existing);

        System.out.println("Users berhasil di edit");
        System.out.println();
        mainUser();
    }

    public void deleteUser() throws ParseException{
        System.out.println("Masukkan User Id yang akan dihapus : ");
        Long userId = scanner.nextLong();
        Optional<Users> existingUsers = Optional.of(userService.getUserById(userId).get());

        Users existing = existingUsers.get();

        userService.deleteUser(existing.getUsersId());
        System.out.println("User berhasil dihapus");
        System.out.println();
        mainUser();
    }
}