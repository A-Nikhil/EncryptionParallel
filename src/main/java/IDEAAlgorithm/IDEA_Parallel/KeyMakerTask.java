package IDEAAlgorithm.IDEA_Parallel;

import java.util.concurrent.Callable;

public class KeyMakerTask implements Callable<String> {

	private String data;
	private int times;

	KeyMakerTask(String data, int times) {
		this.data = data;
		this.times = times;
	}

	@Override
	public String call() {
		if (times == 0) {
			return data;
		}
		if (times > 128) {
			times = times % 128;
		}
		String finalNumber = "";
		int i;
		for (i = 0; i < times; i++) {
			finalNumber = finalNumber.concat(Character.toString(data.charAt(i)));
		}
		finalNumber = data.substring(times).concat(finalNumber);
		return finalNumber;
	}
}
