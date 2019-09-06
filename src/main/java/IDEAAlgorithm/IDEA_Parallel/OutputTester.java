package IDEAAlgorithm.IDEA_Parallel;

// Testing purpose only

import java.util.ArrayList;

public class OutputTester {
	private int isEqual(String text1, String text2) {
		int counter = 0;
		for (int i = 0; i < text1.length(); i++) {
			if (text1.charAt(i) == text2.charAt(i)) {
				counter++;
			}
		}
		return counter;
	}

	public static void main(String[] args) {
	}

	public void ArrayListPrinter(ArrayList<String[]> list) {
		int i, j = 1;
		for (String[] y : list) {
			System.out.println("Round " + j);
			i = 1;
			for (String x : y) {
				System.out.println(i + ". " + x);
				i++;
			}
			System.out.println();
			j++;
		}
	}
}
