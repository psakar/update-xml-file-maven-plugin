package org.wildfly.maven.plugins.update.versions;

import static org.wildfly.maven.plugins.update.versions.Constants.UPDATE_VERSIONS;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractVersionUpdateMojo extends AbstractMojo {

	@Parameter(property = UPDATE_VERSIONS + ".stream", required = true)
	protected String stream;

	@Parameter(property = UPDATE_VERSIONS + ".targetPom", required = true)
	protected File targetPom;

	@Parameter(property = UPDATE_VERSIONS + ".wildflyPom", required = true)
	protected File wildflyPom;

	@Parameter(property = UPDATE_VERSIONS + ".wildflyCorePom", required = true)
	protected File wildflyCorePom;

	@Parameter(property = UPDATE_VERSIONS + ".update", defaultValue = "true")
	protected boolean update;

	@Parameter(property = UPDATE_VERSIONS + ".strictMode", defaultValue = "false")
	protected boolean strictMode;

	public MyLogger getLogger() {
		return info -> getLog().info(info);
	}

	public static interface MyLogger {
		void info(String info);
	}
}
