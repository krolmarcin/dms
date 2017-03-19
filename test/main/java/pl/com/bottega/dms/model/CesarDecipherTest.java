package pl.com.bottega.dms.model;

import pl.com.bottega.dms.infrastructure.CesarInputStream;

import java.io.*;

public class CesarDecipherTest {

    public static void main(String[] args) {


        String pathIn = "c:/testCipherIn.txt";
        String pathOut = "c:/testCipherOut.txt";
        String pathDecrypt = "c:/testCipherDecrypt.txt";
        int movement = 1;
        int i;


        try (
                InputStream is = new FileInputStream(pathOut);
                OutputStream os = new FileOutputStream(pathDecrypt))

        {
            {
                while ((i = is.read()) != -1) {
                    CesarInputStream cesarInputStream = new CesarInputStream(is, movement);
                    int j = cesarInputStream.read();
                    os.write(j);
                }
            }
        } catch (
                FileNotFoundException e)

        {
            e.printStackTrace();
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }
    }

}

