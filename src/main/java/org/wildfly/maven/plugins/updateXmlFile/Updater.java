package org.wildfly.maven.plugins.updateXmlFile;

import static java.text.MessageFormat.format;

import java.io.File;

import org.wildfly.maven.plugins.updateXmlFile.VersionUpdateMojo.MyLogger;

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
			String newVersion = property.getReference().getVersion();
			if (newVersion == null) {
				IPropertyReference propertyReference = property.getReference();
				handleVersionNotFound(propertyReference.getPropertyXPath(), propertyReference.getPomFile(), strictMode);
			} else {
				updateProperty(property.targetPropertyXPath, newVersion, update, strictMode);
			}
		}
	}

	private void updateProperty(String propertyXPath, String newVersion, boolean update, boolean strictMode) {
		String currentVersion = pomManager.findPropertyVersion(propertyXPath);
		if (currentVersion == null) {
			handleVersionNotFound(propertyXPath, targetPom, strictMode);
			return;
		}

		if (!currentVersion.equals(newVersion)) {
			log.info(createChangeVersionMessage(propertyXPath, newVersion, targetPom.getAbsolutePath()));
			if (update) {
				pomManager.updatePropertyVersion(propertyXPath, newVersion);
				pomManager.writeToPomFile();
			}
		}
	}

	private void handleVersionNotFound(String propertyName, File file, boolean strictMode) {
		String message = createPropertyNotFoundMessage(propertyName, file);
		if (strictMode) {
			throw new IllegalStateException(message);
		} else {
			log.info(message);
		}
	}

	private static String createPropertyNotFoundMessage(String propertyName, File file) {
		return format("Could not find {0} in file {1}", propertyName, file);
	}

	private static String createChangeVersionMessage(String propertyName, String expectedVersion, String targetPom) {
		return format("Change version of {0} to {1} in file {2}", propertyName, expectedVersion, targetPom);
	}
}
