package IDEAAlgorithm.commons;

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
}
