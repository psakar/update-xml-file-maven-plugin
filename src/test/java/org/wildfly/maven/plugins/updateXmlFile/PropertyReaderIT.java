package org.wildfly.maven.plugins.updateXmlFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.wildfly.maven.plugins.updateXmlFile.Property.SOURCE_TYPE_FILE;
import static org.wildfly.maven.plugins.updateXmlFile.Property.SOURCE_TYPE_VARIABLE;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.JAVAX_ENTERPRISE_VALUE;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.JAVAX_ENTERPRISE_XPATH;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.MAVEN_PARAMETER_WILDFLYCORE_POM;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.POM_TEST_PATH;
import static org.wildfly.maven.plugins.updateXmlFile.TestUtils.PROPERTIES_EXAMPLE_PATH;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class PropertyReaderIT {
	private static final int VARIABLE_TYPE_INDEX = 0;
	private static final int FILE_TYPE_INDEX = 1;

	private static final String PRODUCT_VERSION_XPATH = "//properties/product.version";
	private static final String PRODUCT_VERSION_PROPERTY_NAME = "eapVersion";
	private static final String PRODUCT_VERSION_VALUE = "7.3.x";
	private PropertyReader propertyReader;
	private File propertiesFile;
	private File pomFile;

	@Before
	public void before() {
		pomFile = new File(POM_TEST_PATH);
		propertiesFile = new File(PROPERTIES_EXAMPLE_PATH);
		propertyReader = new PropertyReader(propertiesFile, new Properties());
	}

	@Test
	public void testReaderWithSourceTypeFileUsingSystemProperties() {
		setupSystemProperties();

		List<Property> properties = propertyReader.readProperties();
		Property property = properties.get(FILE_TYPE_INDEX);

		assertEquals(JAVAX_ENTERPRISE_XPATH, property.targetPropertyXPath);
		assertEquals(SOURCE_TYPE_FILE, property.sourceType);
		assertEquals(JAVAX_ENTERPRISE_VALUE, property.getReference().getVersion());
		assertEquals(pomFile.getAbsolutePath(), property.getReference().getPomFile().getAbsolutePath());
		assertEquals(JAVAX_ENTERPRISE_XPATH, property.getReference().getPropertyXPath());
	}

	@Test
	public void testReaderWithSourceTypeVariableUsingSystemProperties() {
		setupSystemProperties();

		List<Property> properties = propertyReader.readProperties();
		Property property = properties.get(VARIABLE_TYPE_INDEX);

		assertEquals(PRODUCT_VERSION_XPATH, property.targetPropertyXPath);
		assertEquals(SOURCE_TYPE_VARIABLE, property.sourceType);
		assertEquals(PRODUCT_VERSION_VALUE, property.getReference().getVersion());
		assertNull(property.getReference().getPomFile());
		assertEquals("", property.getReference().getPropertyXPath());
	}

	@Test
	public void testReaderWithSourceTypeFileUsingProperties() {
		clearDefinedSystemProperties();
		propertyReader = new PropertyReader(propertiesFile, createProperties());

		List<Property> properties = propertyReader.readProperties();
		Property property = properties.get(FILE_TYPE_INDEX);

		assertEquals(JAVAX_ENTERPRISE_XPATH, property.targetPropertyXPath);
		assertEquals(SOURCE_TYPE_FILE, property.sourceType);
		assertEquals(JAVAX_ENTERPRISE_VALUE, property.getReference().getVersion());
		assertEquals(pomFile.getAbsolutePath(), property.getReference().getPomFile().getAbsolutePath());
		assertEquals(JAVAX_ENTERPRISE_XPATH, property.getReference().getPropertyXPath());
	}

	@Test(expected = IllegalStateException.class)
	public void testReaderMissingDefinitions() {
		clearDefinedSystemProperties();

		propertyReader.readProperties();
	}

	private void setupSystemProperties() {
		System.setProperty(MAVEN_PARAMETER_WILDFLYCORE_POM, pomFile.getAbsolutePath());
		System.setProperty(PRODUCT_VERSION_PROPERTY_NAME, PRODUCT_VERSION_VALUE);
	}

	private void clearDefinedSystemProperties() {
		System.clearProperty(MAVEN_PARAMETER_WILDFLYCORE_POM);
		System.clearProperty(PRODUCT_VERSION_PROPERTY_NAME);
	}

	private Properties createProperties() {
		Properties properties = new Properties();
		properties.setProperty(MAVEN_PARAMETER_WILDFLYCORE_POM, pomFile.getAbsolutePath());
		properties.setProperty(PRODUCT_VERSION_PROPERTY_NAME, PRODUCT_VERSION_VALUE);
		return properties;
	}
}
