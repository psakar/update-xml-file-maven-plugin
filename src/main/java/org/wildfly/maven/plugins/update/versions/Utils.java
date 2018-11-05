package org.wildfly.maven.plugins.update.versions;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.filefilter.DirectoryFileFilter.DIRECTORY;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.filefilter.WildcardFileFilter;

public final class Utils {

	private Utils() {
	}

	public static Collection<File> getFilesContaining(String content, String directoryPath, String fileNameFilter) {
		Collection<File> expectedFiles = new ArrayList<>();
		Collection<File> files = listFiles(new File(directoryPath), new WildcardFileFilter(fileNameFilter), DIRECTORY);
		for (File file : files) {
			try {
				if (readFileToString(file, UTF_8).contains(content)) {
					expectedFiles.add(file);
				}
			} catch (IOException e) {
				throw new IllegalArgumentException("Error accessing file: " + file + " - " + e.getMessage());
			}
		}
		return expectedFiles;
	}
}
