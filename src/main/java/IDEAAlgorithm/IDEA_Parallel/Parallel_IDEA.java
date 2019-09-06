// TODO: 04-09-2019 Decryption Key generation
// TODO: 04-09-2019 Decryption process

package IDEAAlgorithm.IDEA_Parallel;

import IDEAAlgorithm.commons.Key;
import IDEAAlgorithm.commons.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Parallel_IDEA {

	private final static Utilities utils = new Utilities();

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		int numberOfCores = Runtime.getRuntime().availableProcessors();
		ExecutorService service = Executors.newFixedThreadPool(numberOfCores);

		// form key
		Key key = new Key();
		key = new KeyMaker().makeKey(key);

		String plainText = "Hello World";
		int length = plainText.length() % 8;
		//noinspection ConstantConditions
		if (length != 0) {
			for (int i = length; i < 8; i++) {
				plainText = plainText.concat(" ");
			}
		}
		HashMap<Integer, Future<String>> PlainTextMap = new HashMap<>();
		int i;
		for (i = 0; i < plainText.length(); i += 8) {
			for (int j = 0; j < 8; j++) {
				PlainTextMap.put((j + i), service.submit(new TextParallel(plainText.charAt(i + j), utils)));
			}
		}
		ArrayList<String[]> textBlocks = new ArrayList<>();
		for (i = 0; i < plainText.length(); i += 4) {
			textBlocks.add(
					new String[]{
							PlainTextMap.get(i).get(),
							PlainTextMap.get(i + 1).get(),
							PlainTextMap.get(i + 2).get(),
							PlainTextMap.get(i + 3).get()
					}
			);
		}
		service.shutdown();
		PlainTextMap.clear(); // for garbage collection
		System.gc();
		ArrayList<String[]> keySet = key.getKeyList(); // getting keys
		HashMap<Integer, Future<String>> CipherTextMap = new HashMap<>();
		service = Executors.newFixedThreadPool(numberOfCores);
		for (i = 0; i < textBlocks.size(); i += 2) {
			CipherTextMap.put(i, service.submit(new Parallel_IDEA_Task(
					textBlocks.get(i),
					textBlocks.get(i + 1),
					keySet,
					utils
			)));
		}
		String cipher = "";
		for (i = 0; i < textBlocks.size(); i += 2) {
			cipher = cipher.concat(CipherTextMap.get(i).get());
		}
		System.out.println(cipher);
		service.shutdown();
	}
}