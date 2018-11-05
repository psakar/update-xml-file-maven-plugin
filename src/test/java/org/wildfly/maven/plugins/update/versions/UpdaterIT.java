package org.wildfly.maven.plugins.update.versions;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.wildfly.maven.plugins.update.versions.TestUtils.JAVAX_ENTERPRISE_PROPERTY;
import static org.wildfly.maven.plugins.update.versions.TestUtils.POM_REFERENCE_TEST_PATH;
import static org.wildfly.maven.plugins.update.versions.TestUtils.POM_TEST_PATH;
import static org.wildfly.maven.plugins.update.versions.TestUtils.XML_EXTENSION;
import static org.wildfly.maven.plugins.update.versions.TestUtils.checkFileContainsPropertyAndVersion;
import static org.wildfly.maven.plugins.update.versions.TestUtils.copyFile;
import static org.wildfly.maven.plugins.update.versions.TestUtils.createRandomString;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.maven.plugins.update.versions.AbstractVersionUpdateMojo.MyLogger;

public class UpdaterIT {
	private static final String JAVAX_INJECT_PROPERTY = "version.javax.inject";
	private static final String JAVAX_INJECT_PROPERTY_REFERENCE = "version.javax.inject.javax.inject";
	private File tmpPomFile;
	private File referencePom;
	private Updater updater;

	@Before
	public void before() throws Exception {
		tmpPomFile = copyFile(new File(POM_TEST_PATH), XML_EXTENSION);
		referencePom = new File(POM_REFERENCE_TEST_PATH);
		updater = new Updater(tmpPomFile, getLogger());
		updater.initialize();
	}

	@After
	public void afterClass() {
		tmpPomFile.delete();
	}

	@Test
	public void testUpdatePropertyWrittingToFile() {
		String newVersion = createRandomString();
		boolean update = true;

		updater.updateProperty(JAVAX_ENTERPRISE_PROPERTY, newVersion, update, true);

		assertTrue(checkFileContainsPropertyAndVersion(JAVAX_ENTERPRISE_PROPERTY, newVersion, tmpPomFile));
	}

	@Test
	public void testUpdatePropertyNotWrittingToFile() {
		String currentVersion = "2.0.0.SP1-redhat-00001";
		String newVersion = createRandomString();
		boolean update = false;

		updater.updateProperty(JAVAX_ENTERPRISE_PROPERTY, newVersion, update, true);

		assertTrue(checkFileContainsPropertyAndVersion(JAVAX_ENTERPRISE_PROPERTY, currentVersion, tmpPomFile));
	}

	@Test(expected = IllegalStateException.class)
	public void testUpdatePropertyNotFoundStrictMode() {
		String property = createRandomString();
		String version = createRandomString();
		boolean strictMode = true;

		updater.updateProperty(property, version, true, strictMode);
	}

	@Test
	public void testUpdatePropertyNotFoundRelaxedMode() {
		String property = createRandomString();
		String version = createRandomString();
		boolean strictMode = false;

		updater.updateProperty(property, version, true, strictMode);
	}

	@Test
	public void testUpdatePropertiesWrittingToFile() {
		String expectedJavaxEnterpriseVersion = "2.0.0.SP1-redhat-00002";
		String expectedJavaxInjectVersion = "2.0.0.SP2-redhat-00001";
		boolean update = true;

		updater.updateProperties(getValidProperties(), update, true);

		assertTrue(checkFileContainsPropertyAndVersion(JAVAX_ENTERPRISE_PROPERTY, expectedJavaxEnterpriseVersion, tmpPomFile));
		assertTrue(checkFileContainsPropertyAndVersion(JAVAX_INJECT_PROPERTY, expectedJavaxInjectVersion, tmpPomFile));
	}

	@Test(expected = IllegalStateException.class)
	public void testUpdatePropertiesNotFoundStrictMode() {
		boolean strictMode = true;

		updater.updateProperties(getInvalidProperties(), true, strictMode);
	}


	private List<Property> getValidProperties() {
		return asList(
			new Property(JAVAX_ENTERPRISE_PROPERTY, JAVAX_ENTERPRISE_PROPERTY, referencePom),
			new Property(JAVAX_INJECT_PROPERTY, JAVAX_INJECT_PROPERTY_REFERENCE, referencePom)
		);
	}

	private List<Property> getInvalidProperties() {
		return asList(
			new Property(createRandomString(), createRandomString(), referencePom)
		);
	}

	private MyLogger getLogger() {
		return new MyLogger() {
			@Override
			public void info(String info) {
				return;
			}
		};
	}
}
