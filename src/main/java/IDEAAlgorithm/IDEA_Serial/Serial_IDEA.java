/*
main() is for testing purposes only.
The main execution will be carried out as API calls
 */

package IDEAAlgorithm.IDEA_Serial;

import IDEAAlgorithm.commons.IDEASequence;
import IDEAAlgorithm.commons.Key;
import IDEAAlgorithm.commons.Utilities;

public class Serial_IDEA {

	private final Utilities utils = new Utilities();

	public static void main(String[] args) {
		Serial_IDEA object = new Serial_IDEA();
		String text = "Hello World"; // sample text

		System.out.println("Starting Encryption Process with Plain Text = " + text);

		/*
		* Array containing the cipher text and time taken
		* [0] => cipher text
		* [1] => Time taken
		 */
		String[] returnedCipher  = object.doIDEAEncryption(text);
		String cipher = returnedCipher[0];
		long encrypt = Long.parseLong(returnedCipher[1]);
		System.out.printf("Cipher text : %s\n Time taken to encrypt %d\n", cipher, encrypt);

		/*
		 * Array containing the plain text and time taken
		 * [0] => plain text
		 * [1] => Time taken
		 */
		String[] returnedPlain = object.doIDEADecryption(cipher);
		String pt = returnedPlain[0];
		long decrypt = Long.parseLong(returnedPlain[1]);
		System.out.printf("Decrypted text : %s\n Time taken to decrypt %d\n", pt, decrypt);
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