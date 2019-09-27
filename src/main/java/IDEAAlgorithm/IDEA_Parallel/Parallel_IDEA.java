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
	private final int numberOfCores = Runtime.getRuntime().availableProcessors();
	private Key key = new Key();

	@SuppressWarnings("Duplicates")
	public static void main(String[] args) {
//		Parallel_IDEA object = new Parallel_IDEA();
//		String plainText = "Hello World";
//		long init = System.currentTimeMillis();
//		String cipher = object.doEncryptionParallel(plainText);
//		System.out.println(cipher);
//		long encrypt = System.currentTimeMillis();
//		String plain = object.doDecryptionParallel(cipher).trim();
//		long decrypt = System.currentTimeMillis();
//		System.out.println(plain);
//		System.out.println("Time Taken to encrypt = " + (encrypt - init) + " ms");
//		System.out.println("Time Taken to decrypt = " + (decrypt - encrypt) + " ms");
//		System.out.println("           Total Time = " + (decrypt - init) + " ms");
//		if (plain.equals(plainText)) {
//			System.out.println("Successful Encryption and Decryption");
//			System.exit(0);
//		}
	}

	public String[] doEncryptionParallel(String plainText) {
		try {
			// form key
			long init = System.currentTimeMillis();
			ExecutorService service = Executors.newFixedThreadPool(numberOfCores);
			key = new KeyMaker().makeKey(key);
			int length = plainText.length() % 8;
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
			long time = System.currentTimeMillis() - init;
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
			long checkPoint1 = System.currentTimeMillis();
			service = Executors.newFixedThreadPool(numberOfCores);
			ArrayList<String[]> keySet = key.getKeyList(); // getting keys
			HashMap<Integer, Future<String>> CipherTextMap = new HashMap<>();
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
			time += System.currentTimeMillis() - checkPoint1;
			return new String[]{
					cipher,
					Long.toString(time)
			};
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return new String[]{};
	}

	public String[] doDecryptionParallel(String cipherText) {
		try {
			ExecutorService service = Executors.newFixedThreadPool(numberOfCores);
			String str;
			String[] topArray, bottomArray;
			HashMap<Integer, Future<String>> TextMap = new HashMap<>();
			long init = System.currentTimeMillis();
			key = new KeyMaker().invertKey(key.getKeyList());
			for (int i = 0; i < cipherText.length(); i += 64) {
				str = cipherText.substring(i, i + 64);
				topArray = new String[]{
						str.substring(0, 8),
						str.substring(8, 16),
						str.substring(16, 24),
						str.substring(24, 32)
				};
				bottomArray = new String[]{
						str.substring(32, 40),
						str.substring(40, 48),
						str.substring(48, 56),
						str.substring(56, 64)
				};
				TextMap.put(i, service.submit(new Parallel_IDEA_Task(
						topArray,
						bottomArray,
						key.getKeyList(),
						utils
				)));
			}
			str = "";
			for (int i = 0; i < cipherText.length(); i += 64) {
				str = str.concat(TextMap.get(i).get());
			}
			long time = System.currentTimeMillis() - init;
			service.shutdown();
			return new String[]{
					utils.getDecryptedString(str),
					Long.toString(time)
			};
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return new String[]{};
	}
}