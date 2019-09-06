package IDEAAlgorithm.IDEA_Parallel;

import IDEAAlgorithm.commons.Utilities;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Parallel_IDEA_Task implements Callable<String> {

	private String[] topArray;
	private String[] bottomArray;
	private ArrayList<String[]> keySet;
	private Utilities utilities;

	Parallel_IDEA_Task(String[] topArray, String[] bottomArray, ArrayList<String[]> keySet, Utilities utilities) {
		this.topArray = topArray;
		this.bottomArray = bottomArray;
		this.keySet = keySet;
		this.utilities = utilities;
	}

	@Override
	public String call() {
		String p1, p2, p3, p4;
		p1 = topArray[0] + topArray[1];
		p2 = topArray[2] + topArray[3];
		p3 = bottomArray[0] + bottomArray[1];
		p4 = bottomArray[2] + bottomArray[3];
		return utilities.doIDEASequenceLoop(p1, p2, p3, p4, utilities, keySet);
	}
}
