import common.Message;

import java.io.IOException;
import java.nio.file.FileSystems;

public class MainZad2 {
    private static final String FILE_NAME = "cryptogram.txt";
    private static final int NUMBER_OF_THREADS = 8;
    private static final int NUMBER_OF_CHARACTERS_TO_CHECK = 16;

    public static void main(String[] args) throws IOException {
        Message message = new Message(FileSystems.getDefault().getPath("src/zad2/resources", FILE_NAME));
        Result isDecoded = new Result();
        solveZad2(message, isDecoded);

        int indexesPerThread = NUMBER_OF_CHARACTERS_TO_CHECK / NUMBER_OF_THREADS;
        int rest = NUMBER_OF_CHARACTERS_TO_CHECK % NUMBER_OF_THREADS;
        for (int i = 0; i < NUMBER_OF_CHARACTERS_TO_CHECK; i += indexesPerThread) {
            new Thread(new Worker(i, i + indexesPerThread, isDecoded, message.getLetters())).start();
        }
        if (rest != 0) {
            new Thread(new Worker(NUMBER_OF_CHARACTERS_TO_CHECK - rest, NUMBER_OF_CHARACTERS_TO_CHECK, isDecoded, message.getLetters())).start();
        }
    }

    private static void solveZad2(Message message, Result isDecoded) {
        Cheater cheater = new Cheater(isDecoded, message.getLetters());
        new Thread(cheater).start();
    }
}
