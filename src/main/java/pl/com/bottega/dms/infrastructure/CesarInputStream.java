package pl.com.bottega.dms.infrastructure;

import java.io.IOException;
import java.io.InputStream;

public class CesarInputStream extends InputStream {

    private final InputStream inputStream;
    private int key;

    public CesarInputStream(InputStream inputStream, int key) {

        this.inputStream = inputStream;
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int b = inputStream.read();
        if (b == -1)
            return -1;
        return (b - key) % 255;
    }

}
