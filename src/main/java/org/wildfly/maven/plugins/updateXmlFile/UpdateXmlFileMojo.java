package org.wildfly.maven.plugins.updateXmlFile;

import static java.util.Arrays.asList;
import static org.apache.maven.plugins.annotations.LifecyclePhase.VALIDATE;
import static org.wildfly.maven.plugins.updateXmlFile.Constants.UPDATE_GOAL;
import static org.wildfly.maven.plugins.updateXmlFile.FileUtils.convertToFile;
import static org.wildfly.maven.plugins.updateXmlFile.FileUtils.getFilesInPath;
import static org.wildfly.maven.plugins.updateXmlFile.FileUtils.readTextFileToList;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = UPDATE_GOAL, defaultPhase = VALIDATE, requiresProject = false)
public class UpdateXmlFileMojo extends AbstractMojo {

	@Parameter(property = "configFile", required = false)
	private String configFilePath;

	@Parameter(property = "updatedFile", required = true)
	private String updatedFile;

	@Parameter(property = "update", defaultValue = "true")
	private boolean update;

	@Parameter(property = "strictMode", defaultValue = "false")
	private boolean strictMode;

	@Parameter(property = "config", required = false)
	private String config;

	@Inject
	private MavenProject project;

	public static interface MyLogger {
		void info(String info);
	}

	public void execute() throws MojoExecutionException {
		PropertyReader propertyReader = new PropertyReader(project.getProperties());
		List<String> lines = readConfigLines();
		List<Property> properties = propertyReader.readProperties(lines);

		List<File> pomFiles = getFilesInPath(updatedFile);
		for (File pomFile : pomFiles) {
			Updater updater = new Updater(pomFile, getLogger());
			updater.initialize();
			updater.updateProperties(properties, update, strictMode);
		}
	}

	List<String> readConfigLines() throws MojoExecutionException {
		if (FileUtils.isBlank(configFilePath) && FileUtils.isBlank(config)) {
			throw new MojoExecutionException("Please specify either configFilePath or config property in plugin configuration or by -Dconfig or -DconfigFile");
		}
		File configFile = convertToFile(configFilePath);
		return configFile == null ? asList(config) : readTextFileToList(configFile);
	}

	private MyLogger getLogger() {
		return info -> getLog().info(info);
	}
}
