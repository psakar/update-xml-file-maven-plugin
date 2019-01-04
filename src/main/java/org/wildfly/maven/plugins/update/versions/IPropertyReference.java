package org.wildfly.maven.plugins.update.versions;

import java.io.File;

public interface IPropertyReference {
	File getPomFile();

	String getPropertyXPath();

	String getVersion();
}