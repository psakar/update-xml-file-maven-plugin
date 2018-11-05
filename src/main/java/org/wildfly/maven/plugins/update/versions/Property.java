package org.wildfly.maven.plugins.update.versions;

import java.io.File;

public class Property {
	public final String name;
	public final PropertyReference reference;

	public Property(String name, String nameInSourcePomFile, File sourcePomFile) {
		this.name = name;
		this.reference = new PropertyReference(nameInSourcePomFile, sourcePomFile);
	}

	public static class PropertyReference {
		public final String propertyName;
		public final File pomFile;

		public PropertyReference(String propertyName, File pomFile) {
			this.propertyName = propertyName;
			this.pomFile = pomFile;
		}
	}
}
