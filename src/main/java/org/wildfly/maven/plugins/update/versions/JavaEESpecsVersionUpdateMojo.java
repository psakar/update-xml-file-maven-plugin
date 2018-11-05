package org.wildfly.maven.plugins.update.versions;

import static org.wildfly.maven.plugins.update.versions.Constants.UPDATE_VERSIONS_JAVAEE_SPECS;

import java.util.List;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = UPDATE_VERSIONS_JAVAEE_SPECS, defaultPhase = LifecyclePhase.VALIDATE, requiresProject = false)
public class JavaEESpecsVersionUpdateMojo extends AbstractVersionUpdateMojo {

	public void execute() {
		JavaEESpecsPropertiesProvider propertiesProvider = new JavaEESpecsPropertiesProvider(wildflyPom, wildflyCorePom);
		List<Property> properties = propertiesProvider.getPropertiesFor(stream);

		Updater updater = new Updater(targetPom, getLogger());
		updater.initialize();
		updater.updateProperties(properties, update, strictMode);
	}
}