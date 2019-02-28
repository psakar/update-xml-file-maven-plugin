package org.wildfly.maven.plugins.updateXmlFile;

import java.io.File;

public class PropertyReferenceFile implements IPropertyReference {
	private static final String DELIMITER = ":";
	private final String reference;

	public PropertyReferenceFile(String reference) {
		this.reference = reference;
	}

	public File getPomFile() {
		return new File(reference.split(DELIMITER)[0]);
	}

	public String getPropertyXPath() {
		return reference.split(DELIMITER)[1];
	}

	public String getVersion() {
		PomManager pm = new PomManager();
		pm.parsePomFile(getPomFile());
		return pm.findPropertyVersion(getPropertyXPath());
	}
}
