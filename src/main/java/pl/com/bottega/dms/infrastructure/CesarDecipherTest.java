package pl.com.bottega.dms.infrastructure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class CesarDecipherTest {

    public static void main(String[] args) throws FileNotFoundException {

        InputStream is = new CesarInputStream(new FileInputStream("c:/java/ciphered.txt"), 1);
        Scanner scanner = new Scanner(is);
        String deciphered = scanner.nextLine();
        System.out.println("Deciphered: " + deciphered);
    }

}
