package pl.com.bottega.dms.infrastructure;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class CesarCipherTest {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter text to cipher:");
        String text = scanner.nextLine();
        OutputStream os = new CesarOutputStream(new FileOutputStream("c:/java/ciphered.txt"), 1);
        PrintWriter pw = new PrintWriter(os);
        pw.println(text);
        pw.close();
    }

}
