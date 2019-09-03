package IDEAAlgorithm.IDEAParallel;

import IDEAAlgorithm.commons.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class KeyMaker {
	Key makeKey(Key key) {
		try {
			KeyMakerTask[] object = new KeyMakerTask[7];
			int numberOfCores = Runtime.getRuntime().availableProcessors(); // returns 8
			ExecutorService service = Executors.newFixedThreadPool(numberOfCores);
			String keyString = key.getOriginalKeyString();
			long start = System.currentTimeMillis();
			HashMap<Integer, Future<String[]>> taskMap = new HashMap<>();
			for (int i = 0; i < 7; i++) {
				object[i] = new KeyMakerTask(keyString, 25 * i);
				taskMap.put(i, service.submit(object[i]));
			}

			ArrayList<String[]> keyList = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				keyList.add(taskMap.get(i).get());
			}
			System.out.println(System.currentTimeMillis() - start);

			service.shutdown();
			key = new Key(keyList);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return key;
	}
}
