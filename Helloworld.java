import java.util.Scanner;

public class Helloworld {
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.print("Masukkan nama => ");
		String nama = input.nextLine();

		//array
		String[] Hewan = {"Kuda", "Banteng", "Kerbau", "Ayam"};
		for (int i=0; i < Hewan.length; i++){
			System.out.println("Ini adalah Hewan " + Hewan[i]);
		}

		//if else
		int angka1 = 5;
		int angka2 = 2;
		for (int i = 0; i < angka1; i++) {
			System.out.print(angka2 + " ");
			angka2 *= 2;
		}
		System.out.println();

		//ef else array
		int angka3 = 5;
		int[] angka4 = {2, 4, 8, 32, 64};
		for (int i = 0; i < angka3; i++) {
			System.out.print(angka4[i] + " ");
		}
	}
}