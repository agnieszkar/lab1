import java.util.Arrays;

public class Test {

    private static final int MAX_INDEX = Integer.parseInt("1111111111111111111111111111", 2);
    private static final byte[] possibleCharacters = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void main(String[] args){

        char[] key = new char[8];
        for ( int i = MAX_INDEX ; i <= MAX_INDEX; i++){
            for( int j = 0; j < 8 ; j++){
                key[7-j]= (char) possibleCharacters[(byte) 15 & Integer.rotateRight(i,j*4 )];
            }
            System.out.println(Arrays.toString(key));
        }

    }
}
