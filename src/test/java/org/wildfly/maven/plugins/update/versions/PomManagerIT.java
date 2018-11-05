package org.wildfly.maven.plugins.update.versions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.wildfly.maven.plugins.update.versions.TestUtils.JAVAX_ENTERPRISE_PROPERTY;
import static org.wildfly.maven.plugins.update.versions.TestUtils.POM_TEST_PATH;
import static org.wildfly.maven.plugins.update.versions.TestUtils.XML_EXTENSION;
import static org.wildfly.maven.plugins.update.versions.TestUtils.checkFileContainsPropertyAndVersion;
import static org.wildfly.maven.plugins.update.versions.TestUtils.copyFile;
import static org.wildfly.maven.plugins.update.versions.TestUtils.createRandomString;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PomManagerIT {
	private PomManager pomManager;
	private File tmpPomFile;

	@Before
	public void before() throws Exception {
		File pomFile = new File(POM_TEST_PATH);
		tmpPomFile = copyFile(pomFile, XML_EXTENSION);

		pomManager = new PomManager();
		pomManager.parsePomFile(pomFile);
	}

	@After
	public void afterClass() {
		tmpPomFile.delete();
	}

	@Test
	public void testPropertyVersionFound() {
		String expectedVersion = "2.0.0.SP1-redhat-00001";

		String version = pomManager.findPropertyVersion(JAVAX_ENTERPRISE_PROPERTY);

		assertEquals(expectedVersion, version);
	}

	@Test
	public void testPropertyVersionNotFound() {
		String invalidPropertyName = createRandomString();

		String version = pomManager.findPropertyVersion(invalidPropertyName);

		assertNull(version);
	}

	@Test
	public void testUpdatePropertyVersion() {
		String newVersion = createRandomString();

		boolean result = pomManager.updatePropertyVersion(JAVAX_ENTERPRISE_PROPERTY, newVersion);

		assertTrue(result);
	}

	@Test
	public void testUpdatePropertyVersionNotFound() {
		String invalidPropertyName = createRandomString();
		String newVersion = createRandomString();

		boolean result = pomManager.updatePropertyVersion(invalidPropertyName, newVersion);

		assertFalse(result);
	}

	@Test
	public void testChangesAreWrittenToFile() {
		String newVersion = "2.0.0.SP2-redhat-00001";
		pomManager.parsePomFile(tmpPomFile);

		pomManager.updatePropertyVersion(JAVAX_ENTERPRISE_PROPERTY, newVersion);
		pomManager.writeToPomFile();

		assertTrue(checkFileContainsPropertyAndVersion(JAVAX_ENTERPRISE_PROPERTY, newVersion, tmpPomFile));
	}
}
