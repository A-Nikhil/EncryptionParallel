package IDEAAlgorithm.commons;

import java.util.ArrayList;

public class Utilities {

	public String decimalToBinary8(int decimalNumber) {
		String decimal = Integer.toBinaryString(decimalNumber);
		int l = decimal.length();
		if (l < 8) {
			for (int i = 0; i <= 7 - l; i++) {
//				System.out.println("adding 0");
				decimal = "0".concat(decimal);
			}
		}
		return decimal;
	}

	public String decimalToBinary16(long decimalNumber) {
		String decimal = Long.toBinaryString(decimalNumber);
		int l = decimal.length();
		if (l < 16) {
			for (int i = 0; i <= 15 - l; i++) {
				decimal = "0".concat(decimal);
			}
		}
		return decimal;
	}

	public int binaryToDecimal16(String binaryString) {
		int decimal = 0;
		for (int i = 15; i >= 0; i--) {
			decimal += ((int) (binaryString.charAt(15 - i)) - 48) * (int) Math.pow(2, i);
		}
		return decimal;
	}

	public int binaryToDecimal8(String binaryString) {
		int decimal = 0;
		for (int i = 7; i >= 0; i--) {
			decimal += ((int) (binaryString.charAt(7 - i)) - 48) * (int) Math.pow(2, i);
		}
		return decimal;
	}

	public String MultiplicationModulo(String x, String y) {
		long n1 = binaryToDecimal16(x);
		long n2 = binaryToDecimal16(y);
		return decimalToBinary16(((n1 * n2) % 65537));

	}

	public String AdditionModulo(String x, String y) {
		int n1 = binaryToDecimal16(x);
		int n2 = binaryToDecimal16(y);
		return decimalToBinary16(((n1 + n2) % 65536));
	}

	public String XOR(String x, String y) {
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

	public String getDecryptedString(String cipher) {
		String x, temp = "";
		for (int i = 0; i < cipher.length(); i += 8) {
			x = cipher.substring(i, i + 8);
			temp = temp.concat(Character.toString((char)binaryToDecimal8(x)));
		}
		return temp;
	}

	public String doIDEASequenceLoop(String p1, String p2, String p3, String p4, Utilities utils, ArrayList<String[]> keySet) {

		String result1, result2, result3, result4, result5, result6, result7, result8, result9, result10,
				result11, result12, result13, result14;
		String[] currentKeySet;
		for (int j=1; j<=8; j++) {
			currentKeySet = keySet.get(j - 1);

			// step 1
			result1 = utils.MultiplicationModulo(p1, currentKeySet[0]);

			// step 2
			result2 = utils.AdditionModulo(p2, currentKeySet[1]);

			// step 3
			result3 = utils.AdditionModulo(p3, currentKeySet[2]);

			// step 4
			result4 = utils.MultiplicationModulo(p4, currentKeySet[3]);

			// step 5
			result5 = utils.XOR(result1, result3);

			// step 6
			result6 = utils.XOR(result2, result4);

			// step 7
			result7 = utils.MultiplicationModulo(result5, currentKeySet[4]);

			// step 8
			result8 = utils.AdditionModulo(result6, result7);

			// step 9
			result9 = utils.MultiplicationModulo(result8, currentKeySet[5]);

			// step 10
			result10 = utils.AdditionModulo(result7, result9);

			// step 11
			result11 = utils.XOR(result1, result9);

			// step 12
			result12 = utils.XOR(result3, result9);

			// step 13
			result13 = utils.XOR(result2, result10);

			// step 14
			result14 = utils.XOR(result4, result10);

			p1 = result11;
			p2 = result13;
			p3 = result12;
			p4 = result14;
		}

		currentKeySet = keySet.get(8);
		p1 = utils.MultiplicationModulo(p1, currentKeySet[0]);
		p2 = utils.AdditionModulo(p2, currentKeySet[1]);
		p3 = utils.AdditionModulo(p3, currentKeySet[2]);
		p4 = utils.MultiplicationModulo(p4, currentKeySet[3]);
		System.out.println(p1 + " - " + p2 + " - " + p3 + " - " + p4);
		// converting back to decimal and adding the corresponding ASCII to  cipher text
		// keep the text as binary only
		return p1 + p2 + p3 + p4;
	}

	public void ArrayListPrinter(ArrayList<String[]> myList) {
		for(String[] y : myList) {
			for (String x : y) {
				System.out.println(x);
			}
		}
	}
}
