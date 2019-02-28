package org.wildfly.maven.plugins.updateXmlFile;

import java.io.File;

public class PropertyReferenceVariable implements IPropertyReference {
	private final String version;

	public PropertyReferenceVariable(String version) {
		this.version = version;
	}

	public File getPomFile() {
		return null;
	}

	public String getPropertyXPath() {
		return "";
	}


	public String getVersion() {
		return version;
	}
}
