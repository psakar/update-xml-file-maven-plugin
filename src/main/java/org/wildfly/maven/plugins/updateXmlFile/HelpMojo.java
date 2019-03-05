package org.wildfly.maven.plugins.updateXmlFile;

import static org.apache.maven.plugins.annotations.LifecyclePhase.INITIALIZE;
import static org.wildfly.maven.plugins.updateXmlFile.Constants.HELP_GOAL;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = HELP_GOAL, defaultPhase = INITIALIZE, requiresProject = false)
public class HelpMojo extends AbstractMojo {


	public void execute() {
		//FIXME replace version
		//FIXME show readme ?
		getLog().info("To update xml file:");
		getLog().info("   org.wildfly.maven.plugins:update-xml-file-maven-plugin:0.0.1.Final:update");
	}

}
