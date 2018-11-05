package org.wildfly.maven.plugins.update.versions;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.wildfly.maven.plugins.update.versions.Constants.STREAM_72CDX;
import static org.wildfly.maven.plugins.update.versions.Constants.STREAM_72X;
import static org.wildfly.maven.plugins.update.versions.Constants.STREAM_73CDX;
import static org.wildfly.maven.plugins.update.versions.Constants.STREAM_73X;

import java.io.File;
import java.util.List;

public class QuickstartsPropertiesProvider implements IPropertiesProvider {
	private final File wildflyPom;
	private final File wildflyCorePom;

	public QuickstartsPropertiesProvider(File wildflyPom, File wildflyCorePom) {
		this.wildflyPom = wildflyPom;
		this.wildflyCorePom = wildflyCorePom;
	}

	public List<Property> getPropertiesFor(String stream) {
		switch (stream) {
			case STREAM_72CDX:
				return getPropertiesFrom72CDx();
			case STREAM_72X:
			case STREAM_73CDX:
			case STREAM_73X:
				return getPropertiesFrom72xAnd73();
			default:
				return emptyList();
		}		
	}

	private List<Property> getPropertiesFrom72xAnd73() {
		return asList(
			new Property("version.commons-io", "version.commons-io", wildflyCorePom),
			new Property("version.com.fasterxml.jackson", "version.com.fasterxml.jackson", wildflyPom),
			new Property("version.com.h2database", "version.com.h2database", wildflyPom),
			new Property("version.org.apache.httpcomponents.httpclient", "version.org.apache.httpcomponents.httpclient", wildflyCorePom),
			new Property("version.org.jboss.msc.jboss-msc", "version.org.jboss.msc.jboss-msc", wildflyCorePom),
			new Property("version.org.slf4j", "version.org.slf4j", wildflyCorePom),
			new Property("version.org.bouncycastle", "version.org.bouncycastle", wildflyPom),
			new Property("version.org.jboss.ws.cxf", "version.org.jboss.ws.cxf", wildflyPom)
		);
	}

	private List<Property> getPropertiesFrom72CDx() {
		return asList(
			new Property("version.commons-io", "version.commons-io", wildflyCorePom),
			new Property("version.org.apache.httpcomponents.httpclient", "version.org.apache.httpcomponents.httpclient", wildflyCorePom),
			new Property("version.com.fasterxml.jackson", "version.com.fasterxml.jackson", wildflyPom),
			new Property("version.com.h2database", "version.com.h2database", wildflyPom),
			new Property("version.commons-lang3", "version.commons-lang3", wildflyPom)
		);
	}
}
