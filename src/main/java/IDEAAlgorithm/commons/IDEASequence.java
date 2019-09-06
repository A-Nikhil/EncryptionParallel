package IDEAAlgorithm.commons;

import IDEAAlgorithm.IDEA_Serial.Text;

import java.util.ArrayList;

public class IDEASequence {
	private final Utilities utils = new Utilities();
	private Key key;
	private Text text;

	public IDEASequence(Key key, Text text) {
		this.key = key;
		this.text = text;
	}

	public String performIDEASequence() {
		String cipherText = "";
		ArrayList<String[]> keySet = key.getKeyList();
		ArrayList<String[]> plain = text.getTextBlocks();
		String p1, p2, p3, p4;
		String resultBack;
		for (int i = 0; i < plain.size(); i += 2) {
			p1 = plain.get(i)[0] + plain.get(i)[1];
			p2 = plain.get(i)[2] + plain.get(i)[3];
			p3 = plain.get(i + 1)[0] + plain.get(i + 1)[1];
			p4 = plain.get(i + 1)[2] + plain.get(i + 1)[3];
//			System.out.println(p1 + " - " + p2 + " - " + p3 + " - " + p4);
			resultBack = utils.doIDEASequenceLoop(p1, p2, p3, p4, utils, keySet);
//			System.out.println("Before half round : ");
//			System.out.println(p1 + " - " + p2 + " - " + p3 + " - " + p4);
			// half round
			cipherText = cipherText.concat(resultBack);
		}
		return cipherText;
	}
}
