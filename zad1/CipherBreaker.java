import common.Message;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class CipherBreaker {
    private static final byte ASCII_a = 97;
    private static final byte[] characters = new byte[]{' ','a','i','o','e','z','n', 'r', 'w', 's', 't', 'c', 'y', 'k', 'd', 'p', 'm', 'u', 'j', 'l', ',', '.', '?', '!', '"', 'b', 'g', 'h', 'f'};
    private static final byte[] sortedCharacters = initializeSorted(characters);
    private static final byte[] additionalCharacters = new byte[]{'-',':','A', 'I', 'O', 'E', 'Z', 'N', 'R', 'W', 'S', 'T', 'C', 'Y', 'K', 'D', 'P', 'M', 'U', 'J', 'L', 'B', 'G', 'H', 'F', 'v', 'x', 'q', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final byte[] sortedAdditionalCharacters = initializeSorted(additionalCharacters);

    private static byte[] initializeSorted(byte[] array) {
        byte[] sortedCharacters = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedCharacters);
        return sortedCharacters;
    }

    public List<List<String>> breakCipher(Message messageToRead, List<Message> otherMessages) {
        List<Message> xorredMessages = Lists.newArrayListWithExpectedSize(otherMessages.size());
        for (Message message : otherMessages) {
            xorredMessages.add(xorMessages(messageToRead, message));
        }
        List<List<String>> characterProposals = Lists.newArrayListWithExpectedSize(messageToRead.length());
        for ( int j = 0; j < messageToRead.length(); j++) {
            characterProposals.add(fetchPossibleCharacters(j, xorredMessages));
        }
        return characterProposals;
    }

    private List<String> fetchPossibleCharacters(int characterPosition, List<Message> xorredMessages) {
        List<String> actualCharacterProposals = Lists.newArrayList();
        actualCharacterProposals.addAll(checkCharactersFromTable(characterPosition, xorredMessages, characters, false));
        if (actualCharacterProposals.isEmpty()) {
            actualCharacterProposals.addAll(checkCharactersFromTable(characterPosition, xorredMessages, additionalCharacters, false));
        }
        if (actualCharacterProposals.isEmpty()){
            actualCharacterProposals.addAll(checkCharactersFromTable(characterPosition, xorredMessages, characters, true));
        }
        if (actualCharacterProposals.isEmpty()){
            actualCharacterProposals.addAll(checkCharactersFromTable(characterPosition, xorredMessages, additionalCharacters, true));
        }
        return actualCharacterProposals;
    }

    private List<String> checkCharactersFromTable(int characterPosition, List<Message> xorredMessages, byte[] proposals, boolean withAdditionals) {
        List<String> actualCharacterProposals = Lists.newArrayListWithExpectedSize(1);
        for (int i = 0; i < proposals.length; i++) {
            byte[] decodedCharacters = new byte[xorredMessages.size()];
            for (int k = 0; k < xorredMessages.size(); k++) {
                decodedCharacters[k] = (byte) ((byte) xorredMessages.get(k).get(characterPosition) ^ proposals[i]);
            }
            if (haveReasonableCharacters(decodedCharacters, withAdditionals)) {
                actualCharacterProposals.add(String.valueOf((char)proposals[i]));
            }
        }
        return actualCharacterProposals;
    }

    private boolean haveReasonableCharacters(byte[] characters, boolean withAdditionals) {
        int reasonable = 0;
        byte lastChanceCharacter = ' ';
        for (byte c : characters) {
            if ('A' <= c && c <= 'Z') c += 32;
            if (Arrays.binarySearch(sortedCharacters, c) >= 0) {
                reasonable++;
            } else {
                lastChanceCharacter = c;
            }
        }
        if(withAdditionals && reasonable == characters.length-1){
            return (Arrays.binarySearch(sortedAdditionalCharacters, lastChanceCharacter) >= 0);
        }
        return (reasonable == characters.length);
    }

    private Message xorMessages(Message message1, Message message2) {
        Message shorterMessage = chooseShorterMessage(message1, message2);
        byte[] xorBytes = new byte[shorterMessage.length()];
        for (int i = 0; i < shorterMessage.length(); i++) {
            xorBytes[i] = (byte) (message1.get(i) ^ message2.get(i));
        }
        return new Message(xorBytes);
    }

    private Message chooseShorterMessage(Message message1, Message message2) {
        return message1.length() < message2.length() ? message1 : message2;
    }
}
