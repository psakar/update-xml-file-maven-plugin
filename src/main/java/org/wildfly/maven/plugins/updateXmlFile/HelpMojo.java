package org.wildfly.maven.plugins.updateXmlFile;

import static org.apache.maven.plugins.annotations.LifecyclePhase.INITIALIZE;
import static org.wildfly.maven.plugins.updateXmlFile.Constants.HELP_GOAL;

import javax.inject.Inject;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

@Mojo(name = HELP_GOAL, defaultPhase = INITIALIZE, requiresProject = false)
public class HelpMojo extends AbstractMojo {

	@Inject
	private MavenProject project;

	public void execute() {
		String version = project.getVersion();
		String scm = project.getProperties().getProperty("scm.url");
		getLog().info("To update xml file:");
		getLog().info("   org.wildfly.maven.plugins:update-xml-file-maven-plugin:" + version + ":update");
		getLog().info("The README with all options can be found at: " + scm);
	}
}
