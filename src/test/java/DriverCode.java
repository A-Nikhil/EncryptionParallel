/*
 * This code runs various test cases.
 * Not related to the core components of the project
*/

import IDEAAlgorithm.IDEA_Parallel.Parallel_IDEA;
import IDEAAlgorithm.IDEA_Serial.Serial_IDEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DriverCode {

	public static void main(String[] args) {
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String test = "";
//			System.out.print("Enter your text : ");
//			String test = reader.readLine();
//			System.out.println("Your Text : " + test);

			String[] returnData, returnData1;
			Parallel_IDEA parallel = new Parallel_IDEA();
			Serial_IDEA serial = new Serial_IDEA();
			long encrypt, decrypt;

//			String test = new DataClass().getData();
//			returnData = serial.doIDEAEncryption(test);
			returnData = parallel.doEncryptionParallel(test);
//			encrypt = Long.parseLong(returnData[1]);
//			System.out.println("Cipher Text : " + returnData[0]);
//			returnData1 = serial.doIDEADecryption(returnData[0]);
			returnData1 = parallel.doDecryptionParallel(returnData[0]);
//			decrypt = Long.parseLong(returnData1[1]);
//			System.out.println("Decrypted Text : " + returnData1[0]);
//			Encryption Decryption Total
//			System.out.println(encrypt + " " + decrypt + " " + (encrypt + decrypt));

			test = new DataClass().getData();
//			returnData = serial.doIDEAEncryption(test);
			returnData = parallel.doEncryptionParallel(test);
			encrypt = Long.parseLong(returnData[1]);
			System.out.println("Cipher Text : " + returnData[0]);
//			returnData1 = serial.doIDEADecryption(returnData[0]);
			returnData1 = parallel.doDecryptionParallel(returnData[0]);
			decrypt = Long.parseLong(returnData1[1]);
			System.out.println("Decrypted Text : " + returnData1[0]);
//			Encryption Decryption Total
			System.out.println(encrypt + " " + decrypt + " " + (encrypt + decrypt));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
}
