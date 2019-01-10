package org.wildfly.maven.plugins.update.pom;

import java.io.File;

public interface IPropertyReference {
	File getPomFile();

	String getPropertyXPath();

	String getVersion();
}