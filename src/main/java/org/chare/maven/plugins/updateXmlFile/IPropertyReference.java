package org.chare.maven.plugins.updateXmlFile;

import java.io.File;

public interface IPropertyReference {
	File getPomFile();

	String getPropertyXPath();

	String getVersion();
}