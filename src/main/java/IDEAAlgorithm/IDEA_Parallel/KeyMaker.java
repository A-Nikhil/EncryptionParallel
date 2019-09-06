package IDEAAlgorithm.IDEA_Parallel;

import IDEAAlgorithm.commons.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class KeyMaker {
	Key makeKey(Key key) {
		try {
			int numberOfCores = Runtime.getRuntime().availableProcessors(); // returns 8
			ExecutorService service = Executors.newFixedThreadPool(numberOfCores);
			String keyString = key.getOriginalKeyString();
			HashMap<Integer, Future<String>> taskMap = new HashMap<>();
			for (int i = 0; i < 7; i++) {
				taskMap.put(i, service.submit(new KeyMakerTask(keyString, 25 * i)));
			}
			String megaKey = "";
			for (int i = 0; i < 7; i++) {
				megaKey = megaKey.concat(taskMap.get(i).get());
			}

			ArrayList<String[]> keyList = new ArrayList<>();
			String[] temp;
			int index = 0, limit;
			for (int i = 0; i < 9; i++) {
				limit = i == 8 ? 4 : 6;
				temp = new String[limit];
				for (int j = 0; j < limit; j++) {
					temp[j] = megaKey.substring(index, index + 16);
					index += 16;
				}
				keyList.add(temp);
			}
			service.shutdown();
			key = new Key(keyList);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return key;
	}
}
