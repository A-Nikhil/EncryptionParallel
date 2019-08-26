package IDEAAlgorithm.commons;

public class Utilities {

	String decimalToBinary8(int decimalNumber) {
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
}
