package IDEAAlgorithm.IDEA_Serial;

import IDEAAlgorithm.commons.IDEASequence;
import IDEAAlgorithm.commons.Key;
import IDEAAlgorithm.commons.KeyGenerator;
import IDEAAlgorithm.commons.Text;
import IDEAAlgorithm.commons.Utilities;

public class Serial_IDEA {

	private final Utilities utils = new Utilities();

	public static void main(String[] args) {
		Serial_IDEA object = new Serial_IDEA();
		String text = "Hello World";
		System.out.println("Starting Encryption Process with Plain Text = " + text);
		long millis = System.currentTimeMillis();
		String cipher = object.doIDEAEncryption(text);
		System.out.println(cipher);
		System.out.println();
		System.out.println(System.currentTimeMillis() - millis);
//		// decrypt
//		millis = System.currentTimeMillis();
		String returnString = object.doIDEADecryption(cipher);
		System.out.println(returnString);
//		System.out.println(System.currentTimeMillis() - millis);
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