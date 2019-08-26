package IDEAAlgorithm.commons;

import java.util.ArrayList;

public class Text {
	private String text;
	private ArrayList<String[]> textBlocks;

	public Text(String text, boolean isPlainText) {
		this.text = text;
		textBlocks = new ArrayList<>();
		if (isPlainText) {
			setText();
			formPlainTextBlocks();
		} else {
			formCipherTextBlocks();
		}
	}

	private void setText() {
		int length = text.length() % 8;
		for (int i = length; i < 8; i++) {
			text = text.concat(" ");
		}
	}

	private void formPlainTextBlocks() {
		Utilities utils = new Utilities();
		int i = 0;
		String[] temp;
		// LOGIC: the text blocks have to be divided into 2 each
		// LOGIC: hence, if the length is not divisible by 2, add an extra space to make it even
		while (i < text.length()) {
			temp = new String[4];
			temp[0] = utils.decimalToBinary8(text.charAt(i));
			temp[1] = utils.decimalToBinary8(text.charAt(i + 1));
			temp[2] = utils.decimalToBinary8(text.charAt(i + 2));
			temp[3] = utils.decimalToBinary8(text.charAt(i + 3));
//			System.out.println(temp.length());
			textBlocks.add(temp);
			i += 4;
		}
	}

	private void formCipherTextBlocks() {
		String str;
		int i = 0;
		while (i < text.length()) {
			str = text.substring(i, i + 32);
			textBlocks.add(
					new String[]{
							str.substring(0, 8),
							str.substring(8, 16),
							str.substring(16, 24),
							str.substring(24, 32)
					}
			);
			i += 32;
		}
	}

	public ArrayList<String[]> getTextBlocks() {
		return textBlocks;
	}
}
