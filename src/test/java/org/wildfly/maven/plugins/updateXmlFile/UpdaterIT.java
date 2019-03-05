package org.wildfly.maven.plugins.updateXmlFile;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.wildfly.maven.plugins.updateXmlFile.Property.SOURCE_TYPE_FILE;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.JAVAX_ENTERPRISE_PROPERTY;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.JAVAX_ENTERPRISE_XPATH;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.POM_REFERENCE_TEST_PATH;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.POM_TEST_PATH;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.XML_EXTENSION;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.checkFileContainsPropertyAndVersion;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.copyFile;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.createRandomString;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.maven.plugins.updateXmlFile.UpdateXmlFileMojo.MyLogger;

public class UpdaterIT {
	private static final String JAVAX_INJECT_PROPERTY = "version.javax.inject";
	private static final String JAVAX_INJECT_XPATH = "//properties/" + JAVAX_INJECT_PROPERTY;
	private static final String JAVAX_INJECT_REFERENCE_XPATH = "//properties/version.javax.inject.javax.inject";

	private File tmpPomFile;
	private File referencePom;
	private Updater updater;

	@Before
	public void before() {
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
	public void testUpdatePropertiesUpdatingFile() {
		String expectedJavaxEnterpriseVersion = "2.0.0.SP1-redhat-00002";
		String expectedJavaxInjectVersion = "2.0.0.SP2-redhat-00001";
		boolean update = true;

		updater.updateProperties(getValidProperties(), update, true);

		assertTrue(checkFileContainsPropertyAndVersion(JAVAX_ENTERPRISE_PROPERTY, expectedJavaxEnterpriseVersion, tmpPomFile));
		assertTrue(checkFileContainsPropertyAndVersion(JAVAX_INJECT_PROPERTY, expectedJavaxInjectVersion, tmpPomFile));
	}

	@Test
	public void testUpdatePropertiesNotUpdatingFile() {
		String expectedJavaxEnterpriseVersion = "2.0.0.SP1-redhat-00001";
		String expectedJavaxInjectVersion = "2.0.0.SP1-redhat-00001";
		boolean update = false;

		updater.updateProperties(getValidProperties(), update, true);

		assertTrue(checkFileContainsPropertyAndVersion(JAVAX_ENTERPRISE_PROPERTY, expectedJavaxEnterpriseVersion, tmpPomFile));
		assertTrue(checkFileContainsPropertyAndVersion(JAVAX_INJECT_PROPERTY, expectedJavaxInjectVersion, tmpPomFile));
	}

	@Test
	public void testUpdatePropertiesNotFoundRelaxedMode() {
		boolean strictMode = false;

		updater.updateProperties(getInvalidProperties(), true, strictMode);
	}

	@Test(expected = IllegalStateException.class)
	public void testUpdatePropertiesNotFoundStrictMode() {
		boolean strictMode = true;

		updater.updateProperties(getInvalidProperties(), true, strictMode);
	}

	private List<Property> getValidProperties() {
		return asList(
			new Property(JAVAX_ENTERPRISE_XPATH, SOURCE_TYPE_FILE, referencePom + ":" + JAVAX_ENTERPRISE_XPATH),
			new Property(JAVAX_INJECT_XPATH, SOURCE_TYPE_FILE, referencePom + ":" + JAVAX_INJECT_REFERENCE_XPATH)
		);
	}

	private List<Property> getInvalidProperties() {
		return asList(
			new Property(createRandomString(), SOURCE_TYPE_FILE, referencePom + ":" + createRandomString())
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
