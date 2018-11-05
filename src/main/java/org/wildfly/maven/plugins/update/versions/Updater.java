package org.wildfly.maven.plugins.update.versions;

import java.io.File;
import java.text.MessageFormat;

import org.wildfly.maven.plugins.update.versions.AbstractVersionUpdateMojo.MyLogger;
import org.wildfly.maven.plugins.update.versions.Property.PropertyReference;

public class Updater {
	private final File targetPom;
	private final MyLogger log;
	private final PomManager pomManager;

	public Updater(File targetPom, MyLogger log) {
		this.targetPom = targetPom;
		this.log = log;
		this.pomManager = new PomManager();
	}

	public void initialize() {
		pomManager.parsePomFile(targetPom);
	}

	public void updateProperties(Iterable<Property> properties, boolean update, boolean strictMode) {
		for (Property property : properties) {
			String newVersion = getPropertyVersionFromPom(property.reference);
			if (newVersion == null) {
				handleVersionNotFound(property.reference, strictMode);
			} else {
				updateProperty(property.name, newVersion, update, strictMode);
			}
		}
	}

	public void updateProperty(String propertyName, String newVersion, boolean update, boolean strictMode) {
		String currentVersion = pomManager.findPropertyVersion(propertyName);
		if (currentVersion == null) {
			handleVersionNotFound(new PropertyReference(propertyName, targetPom), strictMode);
			return;
		}

		if (!currentVersion.equals(newVersion)) {
			log.info(createChangeVersionMessage(propertyName, newVersion, targetPom.getAbsolutePath()));
			if (update) {
				pomManager.updatePropertyVersion(propertyName, newVersion);
				pomManager.writeToPomFile();
			}
		}
	}

	private void handleVersionNotFound(PropertyReference reference, boolean strictMode) {
		String message = createPropertyNotFoundMessage(reference);
		if (strictMode) {
			throw new IllegalStateException(message);
		} else {
			log.info(message);
		}
	}

	private static String getPropertyVersionFromPom(PropertyReference reference) {
		PomManager pm = new PomManager();
		pm.parsePomFile(reference.pomFile);
		return pm.findPropertyVersion(reference.propertyName);
	}

	private static String createPropertyNotFoundMessage(PropertyReference reference) {
		return MessageFormat.format("Could not find property {0} in pom file {1}", reference.propertyName,
				reference.pomFile);
	}

	private static String createChangeVersionMessage(String propertyName, String expectedVersion, String targetPom) {
		return MessageFormat.format("Change version of property <{0}> to {1} in file {2}", propertyName,
				expectedVersion, targetPom);
	}
}
