package IDEAAlgorithm.IDEA_Serial;

import IDEAAlgorithm.commons.IDEASequence;
import IDEAAlgorithm.commons.Key;
import IDEAAlgorithm.commons.Utilities;

public class Serial_IDEA {

	private final Utilities utils = new Utilities();

//	@SuppressWarnings("Duplicates")
	public static void main(String[] args) {
		Serial_IDEA object = new Serial_IDEA();
		String text = "Hello World";
		// decrypt
		System.out.println("Starting Encryption Process with Plain Text = " + text);
		long init = System.currentTimeMillis();
//		String cipher = object.doIDEAEncryption(text);
		long encrypt = System.currentTimeMillis();
//		System.out.println(cipher);
//		String returnString = object.doIDEADecryption(cipher);
		long decrypt = System.currentTimeMillis();
//		System.out.println(returnString);
		System.out.println();
		// execution information
		System.out.println("Time Taken to encrypt = " + (encrypt - init) + " ms");
		System.out.println("Time Taken to decrypt = " + (decrypt - encrypt) + " ms");
		System.out.println("           Total Time = " + (decrypt - init) + " ms");
	}

	public String[] doIDEAEncryption(String PlainText) {
		KeyGenerator generator = new KeyGenerator();
		long start = System.currentTimeMillis();
		Key key = generator.generateKeySet(new Key().getOriginalKeyString(), true);
		Text text = new Text(PlainText, true);
		return new String[]{
				new IDEASequence(key, text).performIDEASequence().trim(),
				Long.toString(System.currentTimeMillis() - start)
		};
	}

	public String[] doIDEADecryption(String CipherText) {
		long start = System.currentTimeMillis();
		Text text = new Text(CipherText, false);
		Key key = new Key();
		KeyGenerator generator = new KeyGenerator();
		key = generator.generateKeySet(key.getOriginalKeyString(), false);
		return new String[] {
				utils.getDecryptedString(new IDEASequence(key, text).performIDEASequence()),
				Long.toString(System.currentTimeMillis() - start)
		};
	}
}