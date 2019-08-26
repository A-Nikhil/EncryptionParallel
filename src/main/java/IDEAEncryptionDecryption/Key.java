package IDEAEncryptionDecryption;

import java.util.ArrayList;

class Key {
	private ArrayList<String[]> keyList;

	Key() {
		keyList = new ArrayList<>();
	}

	Key(ArrayList<String[]> keyList) {
		this.keyList = keyList;
	}

	ArrayList<String[]> getKeyList() {
		return keyList;
	}

	void setKeyList(ArrayList<String[]> keyList) {
		this.keyList = keyList;
	}

	String getOriginalKeyString() {
		return "10010111011111000000111001100010101111100000111100111000100110101110001110111001101010000001000010101000010100100001101111110101";
	}
}
