package common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {
	/**
	 * Calculates the MD5 hash of a file
	 *
	 * @param filePath Path to the file to be hashed
	 * @return MD5 hash as a hexadecimal string
	 * @throws RuntimeException if there's an error during hash calculation
	 */
	public static String HashFile(String filePath) {
		FileInputStream fis = null;
		try {
			// 1. Open file input stream
			File file = new File(filePath);
			fis = new FileInputStream(file);

			// 2. Create message digest
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 3. Read file in chunks and update digest
			byte[] buffer = new byte[8192]; // 8KB buffer
			int bytesRead;

			while ((bytesRead = fis.read(buffer)) != -1) {
				md.update(buffer, 0, bytesRead);
			}

			// 4. Convert digest to hex string
			byte[] digest = md.digest();
			return bytesToHex(digest);

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 algorithm not available", e);
		} catch (IOException e) {
			throw new RuntimeException("Error reading file: " + filePath, e);
		} finally {
			// 5. Handle errors and close stream
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					System.err.println("Warning: Error closing file stream: " + e.getMessage());
				}
			}
		}
	}

	/**
	 * Converts a byte array to a hexadecimal string
	 *
	 * @param bytes Byte array to convert
	 * @return Hexadecimal string representation
	 */
	private static String bytesToHex(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
