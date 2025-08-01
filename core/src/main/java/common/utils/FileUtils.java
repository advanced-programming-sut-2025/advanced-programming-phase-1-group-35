package common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileUtils {

	public static Map<String, String> listFilesInFolder(String folderPath) {
		// 1. Create folder object
		// 2. Get list of files
		// 3. Calculate MD5 hash for each file
		// 4. Return map of filename to hash
		throw new UnsupportedOperationException("List files in folder not implemented yet");
	}

	public static String getSortedFileList(Map<String, String> files) {
		List<Map.Entry<String, String>> sortedEntries = new ArrayList<>(files.entrySet());
		sortedEntries.sort(Map.Entry.comparingByKey());

		// Format each entry as "filename hash"
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> entry : sortedEntries) {
			result.append(entry.getKey())
					.append(" ")
					.append(entry.getValue())
					.append("\n");
		}

		// Remove the last newline if there are entries
		if (result.length() > 0) {
			result.setLength(result.length() - 1);
		}

		return result.toString();
	}
}
