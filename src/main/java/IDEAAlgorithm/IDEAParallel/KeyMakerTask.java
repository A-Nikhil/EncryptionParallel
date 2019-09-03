package IDEAAlgorithm.IDEAParallel;

import java.util.concurrent.Callable;

public class KeyMakerTask implements Callable<String[]> {

	private String data;
	private int times;
	private static String remainder = "";

	KeyMakerTask(String data, int times) {
		this.data = data;
		this.times = times;
	}

	KeyMakerTask() {
	}

	@Override
	public String[] call() {
		if (times == 0) {
			return disintegrate(data);
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
		return disintegrate(finalNumber);
	}

	String[] disintegrate(String data) {
		String[] arr = new String[6];
		int i = 0, v = 0, n = 6;
		while (i < KeyMakerTask.remainder.length()) {
			arr[v] = KeyMakerTask.remainder.substring(i, i + 16);
			v++;
			n--;
			i += 16;
		}
		i=0;
		while (n > 0) {
			arr[v] = data.substring(i, i+16);
			v++;
			n--;
			i+=16;
		}
		if (data.substring(i).length() > 0 && i!=0) {
			KeyMakerTask.remainder = data.substring(i);
		}
		return arr;
	}
}
