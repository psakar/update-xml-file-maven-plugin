package org.wildfly.maven.plugins.update.versions;

import static org.junit.Assert.assertEquals;
import static org.wildfly.maven.plugins.update.versions.TestUtils.JAVAX_ENTERPRISE_PROPERTY;
import static org.wildfly.maven.plugins.update.versions.TestUtils.TEST_RESOURCES_FOLDER;
import static org.wildfly.maven.plugins.update.versions.TestUtils.XML_EXTENSION;
import static org.wildfly.maven.plugins.update.versions.TestUtils.createRandomString;
import static org.wildfly.maven.plugins.update.versions.Utils.getFilesContaining;

import java.io.File;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class UtilsTest {
	private String xmlReggex;

	@Before
	public void before() throws Exception {
		xmlReggex = "*" + XML_EXTENSION;
	}

	@Test
	public void testFilesFoundContaining() {
		Collection<File> files = getFilesContaining(JAVAX_ENTERPRISE_PROPERTY, TEST_RESOURCES_FOLDER, xmlReggex);

		assertEquals(2, files.size());
	}

	@Test
	public void testNoFilesFoundContaining() {
		Collection<File> files = getFilesContaining(createRandomString(), TEST_RESOURCES_FOLDER, xmlReggex);

		assertEquals(0, files.size());
	}
}
