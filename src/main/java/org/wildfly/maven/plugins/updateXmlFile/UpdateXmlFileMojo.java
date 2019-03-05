package org.wildfly.maven.plugins.updateXmlFile;

import static org.apache.maven.plugins.annotations.LifecyclePhase.VALIDATE;
import static org.wildfly.maven.plugins.updateXmlFile.Constants.UPDATE_GOAL;
import static org.wildfly.maven.plugins.updateXmlFile.FileUtils.convertToFile;
import static org.wildfly.maven.plugins.updateXmlFile.FileUtils.getFilesInPath;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = UPDATE_GOAL, defaultPhase = VALIDATE, requiresProject = false)
public class UpdateXmlFileMojo extends AbstractMojo {

	@Parameter(property = "configFile", required = true)
	private String configFilePath;

	@Parameter(property = "updatedFile", required = true)
	private String targetPomPath;

	@Parameter(property = "update", defaultValue = "true")
	private boolean update;

	@Parameter(property = "strictMode", defaultValue = "false")
	private boolean strictMode;

	@Inject
	private MavenProject project;

	public static interface MyLogger {
		void info(String info);
	}

	public void execute() {
		File configFile = convertToFile(configFilePath);
		PropertyReader propertyReader = new PropertyReader(configFile, project.getProperties());
		List<Property> properties = propertyReader.readProperties();

		List<File> pomFiles = getFilesInPath(targetPomPath);
		for (File pomFile : pomFiles) {
			Updater updater = new Updater(pomFile, getLogger());
			updater.initialize();
			updater.updateProperties(properties, update, strictMode);
		}
	}

	private MyLogger getLogger() {
		return info -> getLog().info(info);
	}
}
