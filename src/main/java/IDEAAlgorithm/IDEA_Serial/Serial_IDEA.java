package IDEAAlgorithm.IDEA_Serial;

import IDEAAlgorithm.commons.IDEASequence;
import IDEAAlgorithm.commons.Key;
import IDEAAlgorithm.commons.Utilities;

public class Serial_IDEA {

	private final Utilities utils = new Utilities();

	@SuppressWarnings("Duplicates")
	public static void main(String[] args) {
		Serial_IDEA object = new Serial_IDEA();
		String text = "Hello World";
		System.out.println("Starting Encryption Process with Plain Text = " + text);
		long init = System.currentTimeMillis();
		String cipher = object.doIDEAEncryption(text);
		System.out.println(cipher);
		long encrypt = System.currentTimeMillis();
		System.out.println();
		// decrypt
		String returnString = object.doIDEADecryption(cipher);
		long decrypt = System.currentTimeMillis();
		System.out.println(returnString);
		System.out.println("Time Taken to encrypt = " + (encrypt - init) + " ms");
		System.out.println("Time Taken to decrypt = " + (decrypt - encrypt) + " ms");
		System.out.println("           Total Time = " + (decrypt - init) + " ms");
	}

	private String doIDEAEncryption(String PlainText) {
		KeyGenerator generator = new KeyGenerator();
		Key key = generator.generateKeySet(new Key().getOriginalKeyString(), true);
		Text text = new Text(PlainText, true);
		return new IDEASequence(key, text).performIDEASequence().trim();
	}

	private String doIDEADecryption(String CipherText) {
		Text text = new Text(CipherText, false);
		Key key = new Key();
		KeyGenerator generator = new KeyGenerator();
		key = generator.generateKeySet(key.getOriginalKeyString(), false);
		return utils.getDecryptedString(new IDEASequence(key, text).performIDEASequence());
	}
}