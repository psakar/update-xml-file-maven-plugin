package org.wildfly.maven.plugins.updateXmlFile;

import static java.io.File.createTempFile;
import static java.nio.file.Files.readAllLines;
import static org.apache.commons.io.FileUtils.copyURLToFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.shared.utils.io.DirectoryScanner;

public final class FileUtils {
	public static final String WILDCARD = "*";

	private FileUtils() {
	}

	public static List<String> readTextFileToList(File file) {
		try {
			return readAllLines(file.toPath());
		} catch (IOException e) {
			throw new IllegalArgumentException("Can not read text file " + file + " - " + e.getMessage(), e);
		}
	}

	public static List<File> getFilesInPath(String path) {
		List<File> files = new ArrayList<>();
		if (!path.contains(WILDCARD)) {
			files.add(new File(path));
		} else {
			String[] parts = path.split("\\" + WILDCARD, 2);
			String baseDir = parts[0];
			String pathToFiles = WILDCARD + parts[1];

			DirectoryScanner scanner = new DirectoryScanner();
			scanner.setBasedir(baseDir);
			scanner.setIncludes(pathToFiles);
			scanner.scan();
			for (String fileRelativePath : scanner.getIncludedFiles()) {
				files.add(new File(baseDir + fileRelativePath));
			}
		}
		return files;
	}

	public static File convertToFile(String path) {
		if (path.startsWith("http")) {
			return downloadPomToTempFile(path);
		}
		File file = new File(path);
		if (file.exists()) {
			return file;
		} else {
			throw new IllegalStateException("Invalid file at path: " + path);
		}
	}

	static boolean isBlank(String path) {
		return (path == null || path.isEmpty());
	}

	private static File downloadPomToTempFile(String url) {
		try {
			File file = createTempFile("pom", ".xml");
			copyURLToFile(new URL(url), file);
			return file;
		} catch (IOException e) {
			throw new IllegalStateException("Error downloading file from URL: " + url + " - " + e.getMessage());
		}
	}
}
