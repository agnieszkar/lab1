import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class Worker implements Runnable {
    private static final byte[] possibleCharacters = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final byte[] characters = new byte[]{' ', 'a', 'i', 'o', 'e', 'z', 'n', 'r', 'w', 's', 't', 'c', 'y', 'k', '\'',
            'd', 'p', 'm', 'u', 'j', 'l', ',', '.', '?', '!', '"', 'b', 'g', 'h', 'f', '-', ':', 'A', 'I', 'O', 'E', 'Z', 'N',
            'R', 'W', 'S', 'T', 'C', 'Y', 'K', 'D', 'P', 'M', 'U', 'J', 'L', 'B', 'G', 'H', 'F', 'V', 'Q', 'X', 'v', 'x', 'q',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','&','$','%',};
    private static final byte[] sortedCharacters = initialize();

    private static byte[] initialize() {
        byte[] sortedExpectedCharacters = Arrays.copyOf(characters, characters.length);
        Arrays.sort(sortedExpectedCharacters);
        return sortedExpectedCharacters;
    }

    private final byte[] message;
    private final int start;
    private final int end;
    private final Result result;
    private byte[] key = new byte[]{'0', '0', '0', '0', '0', '0', '0', '0', 'a', '7', '0', '9', 'd', '5', 'c', 'e'};

    Worker(int start, int end, Result result, byte[] message) {
        this.start = start;
        this.end = end;
        this.result = result;
        this.message = message;
    }

    @Override
    public void run() {
        for (int i = start; !result.isDecoded() && i < end; i++) {
            key[0] = possibleCharacters[i];
            for (int j = 0; !result.isDecoded() && j < possibleCharacters.length; j++) {
                key[1] = possibleCharacters[j];
                //System.out.println((char)key[0]+ "" + (char)key[1]);
                for (int k = 0; !result.isDecoded() && k < possibleCharacters.length; k++) {
                    key[2] = possibleCharacters[k];
                    for (int l = 0; !result.isDecoded() && l < possibleCharacters.length; l++) {
                        key[3] = possibleCharacters[l];
                        for (int m = 0; !result.isDecoded() && m < possibleCharacters.length; m++) {
                            key[4] = possibleCharacters[m];
                            for (int n = 0; !result.isDecoded() && n < possibleCharacters.length; n++) {
                                key[5] = possibleCharacters[n];
                                for (int o = 0; !result.isDecoded() && o < possibleCharacters.length; o++) {
                                    key[6] = possibleCharacters[o];
                                    for (int p = 0; !result.isDecoded() && p < possibleCharacters.length; p++) {
                                        key[7] = possibleCharacters[p];
                                        byte[] output = decodeWithActualKey();
                                        if (haveReasonableCharacters(output)) {
                                            result.setDecoded();
                                            printBytes(output);
                                            printBytes(key);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private byte[] decodeWithActualKey() {
        byte[] output = new byte[message.length];
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "RC4");
        RC4Engine rc4Engine = new RC4Engine();
        CipherParameters params = new KeyParameter(secretKeySpec.getEncoded());
        rc4Engine.init(false, params);
        rc4Engine.processBytes(message, 0, message.length, output, 0);
        return output;
    }

    private void printBytes(byte[] bytes) {
        for (byte b : bytes) {
            System.out.print((char) b);
        }
        System.out.println("");
    }

    private boolean haveReasonableCharacters(byte[] result) {
        int i= 0;
        for (byte b : result) {
            if ( Arrays.binarySearch(sortedCharacters, b) < 0 ) {
                return false;
            }
        }
        return true;
    }
}
