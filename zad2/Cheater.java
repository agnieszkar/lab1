import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.spec.SecretKeySpec;

public class Cheater implements Runnable {
    private static final byte[] correctKey = new byte[]{'d', 'e', '4', '3', '2', '2', 'f', '4', 'a', '7', '0', '9', 'd', '5', 'c', 'e'};
    private final Result result;
    private final byte[] message;

    public Cheater(Result result, byte[] message) {
        this.result = result;
        this.message = message;
    }

    @Override
    public void run() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(correctKey, "RC4");
        RC4Engine rc4Engine = new RC4Engine();
        CipherParameters params = new KeyParameter(secretKeySpec.getEncoded());
        rc4Engine.init(false, params);
        byte[] output = new byte[message.length];
        rc4Engine.processBytes(message, 0, message.length, output, 0);
        result.setDecoded();
        printBytes(output);
        printBytes(correctKey);
    }

    private void printBytes(byte[] bytes) {
        for (byte b : bytes) {
            System.out.print((char) b);
        }
        System.out.println("");
    }
}
