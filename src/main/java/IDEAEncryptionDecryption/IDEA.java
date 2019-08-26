package IDEAEncryptionDecryption;

import java.util.ArrayList;

public class IDEA {

	private final Utilities utils = new Utilities();
	src\main\java\IDEAEncryptionDecryption

	public static void main(String[] args) {
		IDEA object = new IDEA();
		String text = "Hello World";
		System.out.println("Starting Encryption Process with Plain Text = " + text);
		long millis = System.currentTimeMillis();
		String cipher = object.doIDEAEncryption(text);
		System.out.println(cipher);
		System.out.println();
		System.out.println(System.currentTimeMillis() - millis);
		// decrypt
		millis = System.currentTimeMillis();
		String returnString = object.doIDEADecryption(cipher);
		System.out.println(object.getDecryptedString(returnString).trim());
		System.out.println(System.currentTimeMillis() - millis);
	}

	private String doIDEAEncryption(String PlainText) {
		KeyGenerator generator = new KeyGenerator();
		Key key = generator.generateKeySet(new Key().getOriginalKeyString(), true);
		Text text = new Text(PlainText, true);
		return performIDEASequence(key, text);
	}

	private String doIDEADecryption(String CipherText) {
		Text text = new Text(CipherText, false);
		Key key = new Key();
		KeyGenerator generator = new KeyGenerator();
		key = generator.generateKeySet(key.getOriginalKeyString(), false);
		return performIDEASequence(key, text);
	}

	private String performIDEASequence(Key key, Text text) {
		IDEA idea = new IDEA();
		String cipherText = "";
		String[] currentKeySet;
		ArrayList<String[]> keySet = key.getKeyList();
		ArrayList<String[]> plain = text.getTextBlocks();
		String p1, p2, p3, p4;
		// these are the results obtained in each step
		String result1, result2, result3, result4, result5, result6, result7, result8, result9, result10,
				result11, result12, result13, result14;
		for (int i = 0; i < plain.size(); i += 2) {

			p1 = plain.get(i)[0] + plain.get(i)[1];
			p2 = plain.get(i)[2] + plain.get(i)[3];
			p3 = plain.get(i + 1)[0] + plain.get(i + 1)[1];
			p4 = plain.get(i + 1)[2] + plain.get(i + 1)[3];

//				System.out.println(p1 + " - " + p2 + " - " + p3 + " - " + p4);

			for (int j = 1; j <= 8; j++) {
				currentKeySet = keySet.get(j - 1);

				// step 1
				result1 = idea.MultiplicationModulo(p1, currentKeySet[0]);

				// step 2
				result2 = idea.AdditionModulo(p2, currentKeySet[1]);

				// step 3
				result3 = idea.AdditionModulo(p3, currentKeySet[2]);

				// step 4
				result4 = idea.MultiplicationModulo(p4, currentKeySet[3]);

				// step 5
				result5 = idea.XOR(result1, result3);

				// step 6
				result6 = idea.XOR(result2, result4);

				// step 7
				result7 = idea.MultiplicationModulo(result5, currentKeySet[4]);

				// step 8
				result8 = idea.AdditionModulo(result6, result7);

				// step 9
				result9 = idea.MultiplicationModulo(result8, currentKeySet[5]);

				// step 10
				result10 = idea.AdditionModulo(result7, result9);

				// step 11
				result11 = idea.XOR(result1, result9);

				// step 12
				result12 = idea.XOR(result3, result9);

				// step 13
				result13 = idea.XOR(result2, result10);

				// step 14
				result14 = idea.XOR(result4, result10);

				p1 = result11;
				p2 = result13;
				p3 = result12;
				p4 = result14;
			}

//				System.out.println("Before half round : ");
//				System.out.println(p1 + " - " + p2 + " - " + p3 + " - " + p4);

			// half round
			currentKeySet = keySet.get(8);
			p1 = idea.MultiplicationModulo(p1, currentKeySet[0]);
			p2 = idea.AdditionModulo(p2, currentKeySet[1]);
			p3 = idea.AdditionModulo(p3, currentKeySet[2]);
			p4 = idea.MultiplicationModulo(p4, currentKeySet[3]);

//				System.out.println(p1 + " - " + p2 + " - " + p3 + " - " + p4);

			// converting back to decimal and adding the corresponding ASCII to  cipher text
			// keep the text as binary only
			cipherText = cipherText.concat(p1 + p2 + p3 + p4);
		}
		return cipherText;
	}

	private String MultiplicationModulo(String x, String y) {
		long n1 = utils.binaryToDecimal16(x);
		long n2 = utils.binaryToDecimal16(y);
		return utils.decimalToBinary16(((n1 * n2) % 65537));

	}

	private String AdditionModulo(String x, String y) {
		int n1 = utils.binaryToDecimal16(x);
		int n2 = utils.binaryToDecimal16(y);
		return utils.decimalToBinary16(((n1 + n2) % 65536));
	}

	private String XOR(String x, String y) {
		String xor = "";
		for (int i = 0; i < 16; i++) {
			if (x.charAt(i) != y.charAt(i)) {
				xor = xor.concat("1");
			} else {
				xor = xor.concat("0");
			}
		}
		return xor;
	}

	private String getDecryptedString(String cipher) {
		String x, temp = "";
		for (int i = 0; i < cipher.length(); i += 8) {
			x = cipher.substring(i, i + 8);
			temp = temp.concat(Character.toString((char)utils.binaryToDecimal8(x)));
		}
		return temp;
	}
}