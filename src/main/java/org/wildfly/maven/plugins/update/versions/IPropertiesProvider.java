package org.wildfly.maven.plugins.update.versions;

import java.util.List;

public interface IPropertiesProvider {

	List<Property> getPropertiesFor(String stream);

}
