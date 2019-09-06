package IDEAAlgorithm.IDEA_Parallel;

import IDEAAlgorithm.commons.Utilities;

import java.util.concurrent.Callable;

public class TextParallel implements Callable<String> {

	private char text;
	private Utilities utilities;

	TextParallel(char text, Utilities utilities) {
		this.text = text;
		this.utilities = utilities;
	}

	@Override
	public String call() {
		return utilities.decimalToBinary8(text);
	}
}
