package IDEAAlgorithm.IDEA_Serial;

import IDEAAlgorithm.commons.Key;
import IDEAAlgorithm.commons.Utilities;

import java.util.ArrayList;

class KeyGenerator {

	Key generateKeySet(String originalKey, boolean isEncryption) {
		originalKey = rounder(originalKey);
		String eightRounds = originalKey.substring(0, 768);
		String halfRound = originalKey.substring(768);

		ArrayList<String[]> myList = new ArrayList<>();
		String[] temp;
		int v = 0;
		for (int i = 0; i < 8; i++) {
			temp = new String[6];
			for (int j = 0; j < 6; j++) {
				temp[j] = eightRounds.substring(v, v + 16);
				v += 16;
			}
			myList.add(temp);
		}

		temp = new String[4];
		v = 0;
		for (int i = 0; i < 4; i++) {
			temp[i] = halfRound.substring(v, v + 16);
			v += 16;
		}
		myList.add(temp);

		if (!isEncryption) {
			myList = performKeyReversal(myList);
		}

		return new Key(myList);
	}

	private String rounder(String originalKey) {
		String tempString = originalKey;
		for (int i = 1; i <= 6; i++) {
			originalKey = circularLeftShiftBy25(originalKey);
			if (i != 6) {
				tempString = tempString.concat(originalKey);
			} else {
				tempString = tempString.concat(originalKey.substring(0, 64));
			}
		}
		return tempString;
	}

	private String circularLeftShiftBy25(String number) {
		String finalNumber = "";
		for (int i = 0; i < 25; i++) {
			finalNumber = finalNumber.concat(Character.toString(number.charAt(i)));
		}
		finalNumber = number.substring(25).concat(finalNumber);
		return finalNumber;
	}

	private final Utilities utilities = new Utilities();

	private ArrayList<String[]> performKeyReversal(ArrayList<String[]> myList) {
		ArrayList<String[]> newList = new ArrayList<>();
		String[][] store = new String[9][6];
		for (int i = 0; i < 9; i++) {
			System.arraycopy(myList.get(i), 0, store[i], 0, myList.get(i).length);
		}

		for (int i = 0; i < 9; i++) {
			if (i != 8) {
				newList.add(
						new String[]{
								utilities.findInverse(store[8 - i][0]),
								utilities.findNegative(store[8 - i][1]),
								utilities.findNegative(store[8 - i][2]),
								utilities.findInverse(store[8 - i][3]),
								store[8 - i - 1][4],
								store[8 - i - 1][5]
						}
				);
			} else {
				newList.add(
						new String[]{
								utilities.findInverse(store[8 - i][0]),
								utilities.findNegative(store[8 - i][1]),
								utilities.findNegative(store[8 - i][2]),
								utilities.findInverse(store[8 - i][3]),
						}
				);
			}
		}

		return newList;
	}
}
