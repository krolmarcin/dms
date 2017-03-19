package pl.com.bottega.dms.model;

import pl.com.bottega.dms.infrastructure.CesarInputStream;
import pl.com.bottega.dms.infrastructure.CesarOutputStream;

import java.io.*;

public class CesarCipherTest {

    public static void main(String[] args) {

        String pathIn = "c:/testCipherIn.txt";
        String pathOut = "c:/testCipherOut.txt";
        String pathDecrypt = "c:/testCipherDecrypt.txt";
        int movement = 1;
        int i;

        try (
                InputStream is = new FileInputStream(pathIn);
                OutputStream os = new FileOutputStream(pathOut)
        ) {
            while ((i = is.read()) != -1) {
                CesarOutputStream cesarOutputStream = new CesarOutputStream(os, movement);
                cesarOutputStream.write(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}