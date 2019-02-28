package org.wildfly.maven.plugins.updateXmlFile;

import static org.apache.commons.io.FileUtils.deleteDirectory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.wildfly.maven.plugins.updateXmlFile.FileUtils.WILDCARD;
import static org.wildfly.maven.plugins.updateXmlFile.FileUtils.convertToFile;
import static org.wildfly.maven.plugins.updateXmlFile.FileUtils.getFilesInPath;
import static org.wildfly.maven.plugins.updateXmlFile.FileUtils.readTextFileToList;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.POM_TEST_PATH;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.PROPERTIES_EXAMPLE_PATH;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.createRandomString;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Files;

public class FileUtilsIT {
	private static final String POM_XML = "pom.xml";
	private File parentTempDir;

	@Before
	public void before() throws Exception {
		parentTempDir = Files.createTempDir();
		createFileStructure(parentTempDir);
	}

	@After
	public void afterClass() throws Exception {
		deleteDirectory(parentTempDir);
	}

	@Test
	public void testReadTextFileToList() {
		int expectedSize = 2;

		List<String> result = readTextFileToList(new File(PROPERTIES_EXAMPLE_PATH));

		assertEquals(expectedSize, result.size());
	}

	@Test
	public void testConvertToFileUsingValidFile() {
		File file = convertToFile(POM_TEST_PATH);

		assertTrue(file.length() != 0);
	}

	@Test(expected = IllegalStateException.class)
	public void testConvertToFileUsingInvalidFile() {
		convertToFile(createRandomString());
	}

	@Test
	public void testConvertToFileUsingValidURL() {
		String widflyPomURL = "https://raw.githubusercontent.com/wildfly/wildfly/master/pom.xml";

		File file = convertToFile(widflyPomURL);

		assertTrue(file.length() != 0);
		file.delete();
	}

	@Test(expected = IllegalStateException.class)
	public void testConvertToFileUsingInvalidURL() {
		String widflyPomURL = "https://" + createRandomString();

		convertToFile(widflyPomURL);
	}

	@Test
	public void testGetFilesInPath() {
		int expectedSize = 1;

		List<File> files = getFilesInPath(POM_TEST_PATH);

		assertEquals(expectedSize, files.size());
	}

	@Test
	public void testGetMultipleFilesInPath() {
		int expectedSize = 2;

		List<File> files = getFilesInPath(parentTempDir + WILDCARD + "/" + POM_XML);

		assertEquals(expectedSize, files.size());
	}

	private void createFileStructure(File parentDir) throws Exception {
		File folder1 = new File(parentDir, createRandomString());
		File folder2 = new File(parentDir, createRandomString());
		folder1.mkdir();
		folder2.mkdir();
		new File(folder1, POM_XML).createNewFile();
		new File(folder2, POM_XML).createNewFile();
	}
}
