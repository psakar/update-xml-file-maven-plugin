package org.wildfly.maven.plugins.update.versions;

import static org.wildfly.maven.plugins.update.versions.Constants.UPDATE_VERSIONS;
import static org.wildfly.maven.plugins.update.versions.Constants.UPDATE_VERSIONS_QUICKSTARTS;
import static org.wildfly.maven.plugins.update.versions.Utils.getFilesContaining;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = UPDATE_VERSIONS_QUICKSTARTS, defaultPhase = LifecyclePhase.VALIDATE, requiresProject = false)
public class QuickstartsVersionUpdateMojo extends AbstractVersionUpdateMojo {

	private static final String POM_XML = "pom.xml";
	private static final String SERVER_BOM_VERSION_PROPERTY = "version.server.bom";
	private static final String PRODUCT_VERSION_PROPERTY = "product.version";
	private static final String SPEC_JAVAEE7_VERSION_PROPERTY = "version.jboss.spec.javaee.7.0";

	@Parameter(property = UPDATE_VERSIONS + ".eapVersion", required = true)
	private String eapVersion;

	@Parameter(property = UPDATE_VERSIONS + ".javaEE7SpecsVersion", required = true)
	private String javaEE7SpecsVersion;

	@Override
	public void execute() {
		Updater updater = new Updater(targetPom, getLogger());
		updater.initialize();
		updater.updateProperty(SERVER_BOM_VERSION_PROPERTY, eapVersion, update, strictMode);

		QuickstartsPropertiesProvider propertiesProvider = new QuickstartsPropertiesProvider(wildflyPom, wildflyCorePom);
		List<Property> properties = propertiesProvider.getPropertiesFor(stream);
		if (!properties.isEmpty()) {
			updater.updateProperty(PRODUCT_VERSION_PROPERTY, eapVersion, update, strictMode);
			updater.updateProperties(properties, update, strictMode);
		} else {
			String quickstartsPath = targetPom.getParent();
			Collection<File> pomFiles = getFilesContaining(SPEC_JAVAEE7_VERSION_PROPERTY, quickstartsPath, POM_XML);
			for (File pom : pomFiles) {
				updater = new Updater(pom, getLogger());
				updater.initialize();
				updater.updateProperty(SPEC_JAVAEE7_VERSION_PROPERTY, javaEE7SpecsVersion, update, strictMode);
			}
		}
	}
}
