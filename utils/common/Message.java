package common;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class Message {
    private byte[] letters;
    private CryptogramReader cryptogramReader = new CryptogramReader();

    public Message(Path path) throws IOException {
        this.letters = cryptogramReader.parse(path);
    }

    public Message(byte[] letters) {
        this.letters = Arrays.copyOf(letters, letters.length);
    }

    public byte get(int i) {
        return letters[i];
    }

    public int length() {
        return letters.length;
    }

    public byte[] getLetters(){
        return letters;
    }
}
