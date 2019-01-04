package org.wildfly.maven.plugins.update.versions;

import static com.google.common.io.Files.copy;
import static java.io.File.createTempFile;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.MessageFormat.format;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.lang3.RandomStringUtils.random;

import java.io.File;
import java.io.IOException;

public class TestUtils {
	public static final String TEST_RESOURCES_FOLDER = "src/test/resources";
	public static final String POM_TEST_PATH = TEST_RESOURCES_FOLDER + "/pom_test.xml";
	public static final String POM_REFERENCE_TEST_PATH = TEST_RESOURCES_FOLDER + "/pom_reference_test.xml";
	public static final String PROPERTIES_EXAMPLE_PATH = TEST_RESOURCES_FOLDER + "/properties_example";
	public static final String MAVEN_PARAMETER_WILDFLYCORE_POM = "wildflyCorePom";

	public static final String JAVAX_ENTERPRISE_PROPERTY = "version.javax.enterprise";
	public static final String JAVAX_ENTERPRISE_XPATH = "//properties/" + JAVAX_ENTERPRISE_PROPERTY;
	public static final String JAVAX_ENTERPRISE_VALUE = "2.0.0.SP1-redhat-00001";

	public static final String XML_EXTENSION = ".xml";

	private TestUtils() {
	}

	public static String createRandomString() {
		return random(20, true, true);
	}

	public static File copyFile(File file, String extension) {
		try {
			File newFile = createTempFile(createRandomString(), extension);
			copy(file, newFile);
			return newFile;
		} catch (IOException e) {
			throw new IllegalStateException("Error copying file " + file + " - " + e.getMessage(), e);
		}
	}

	public static boolean checkFileContainsPropertyAndVersion(String propertyName, String version, File file) {
		String line = format("<{0}>{1}</{0}", propertyName, version);
		try {
			return readFileToString(file, UTF_8).contains(line);
		} catch (IOException e) {
			throw new IllegalStateException("Error checking file " + file + " - " + e.getMessage(), e);
		}
	}
}
