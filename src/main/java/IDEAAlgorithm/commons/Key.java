package IDEAAlgorithm.commons;

import java.util.ArrayList;

public class Key {
	private ArrayList<String[]> keyList;

	public Key() {
		keyList = new ArrayList<>();
	}

	public Key(ArrayList<String[]> keyList) {
		this.keyList = keyList;
	}

	public ArrayList<String[]> getKeyList() {
		return keyList;
	}

	public void setKeyList(ArrayList<String[]> keyList) {
		this.keyList = keyList;
	}

	public String getOriginalKeyString() {
		return "10010111011111000000111001100010101111100000111100111000100110101110001110111001101010000001000010101000010100100001101111110101";
	}
}
