package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CryptogramReader {
    final static Charset ENCODING = StandardCharsets.US_ASCII;

    public byte[] parse(Path path) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(path, ENCODING);
        String line;
        String all = "";
        while ((line = bufferedReader.readLine()) != null) {
            all = all.concat(line);
        }
        String[] bytesInStrings = all.split(" ");
        byte[] bytes = new byte[bytesInStrings.length];
        for (int i = 0; i < bytesInStrings.length; i++) {
            bytes[i] = (byte) Integer.parseInt(bytesInStrings[i], 2);
        }
        return bytes;
    }
}
