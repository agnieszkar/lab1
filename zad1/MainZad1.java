import common.Message;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

public class MainZad1 {
    private static final String FILE_NAME = "ciphertext";
    private static final String FILE_EXTENSION = ".txt";
    private static final int NUMBER_OF_CRYPTOGRAMS = 21;
    private static List<Message> cryptograms  = Lists.newArrayListWithExpectedSize(NUMBER_OF_CRYPTOGRAMS);
    private static CipherBreaker cipherBreaker = new CipherBreaker();

    public static void main(String[] args) throws IOException {
        Message message = new Message(getPathToCryptogram(0));
        for (int i = 1; i < NUMBER_OF_CRYPTOGRAMS; i++) {
            cryptograms.add(new Message(getPathToCryptogram(i)));
        }
        cipherBreaker = new CipherBreaker();
        List<List<String>> result = cipherBreaker.breakCipher(message, cryptograms);
        for(List<String> characterProposals : result){
            System.out.print("\n>");
            characterProposals.forEach(System.out::print);
            System.out.println("<");
        }
    }

    private static Path getPathToCryptogram(int number) {
        return FileSystems.getDefault().getPath("src/zad1/resources", FILE_NAME + number + FILE_EXTENSION);
    }
}
