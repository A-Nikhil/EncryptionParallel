package IDEAAlgorithm.IDEA_Parallel;

import IDEAAlgorithm.commons.Utilities;

import java.util.concurrent.Callable;

public class KeyInverterTask implements Callable<String[]> {
	private int i;
	private final Utilities utilities = new Utilities();
	static String[][] store = new String[9][6];

	KeyInverterTask(int i) {
		this.i = i;
	}

	@Override
	public String[] call() {
		if (i != 8) {
			return new String[]{
					utilities.findInverse(KeyInverterTask.store[8 - i][0]),
					utilities.findNegative(KeyInverterTask.store[8 - i][1]),
					utilities.findNegative(KeyInverterTask.store[8 - i][2]),
					utilities.findInverse(KeyInverterTask.store[8 - i][3]),
					KeyInverterTask.store[8 - i - 1][4],
					KeyInverterTask.store[8 - i - 1][5]
			};
		} else {
			return new String[]{
					utilities.findInverse(KeyInverterTask.store[8 - i][0]),
					utilities.findNegative(KeyInverterTask.store[8 - i][1]),
					utilities.findNegative(KeyInverterTask.store[8 - i][2]),
					utilities.findInverse(KeyInverterTask.store[8 - i][3]),
			};
		}
	}
}
