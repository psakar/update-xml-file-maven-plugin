# Update Pom
Update Pom is a Maven plugin that updates the properties of a Pom file based on others Pom files. 


## Installation
Prerequisites:
* JDK 8 or a newer version
* Maven 3.3.9 or a newer version

Run the build command:
```
mvn clean install
```

You can also build skipping tests:
```
mvn clean install -DskipITs
```


## Usage
The required Maven options are:
* -DupdatePom  - Pom file that will be updated
* -DconfigFile - Configuration file (local file or HTTP URL)

An example of how to run:
```
mvn org.wildfly.maven.plugins:update-pom-maven-plugin:update-versions \
	-DupdatePom=/path/to/target/pom.xml \
	-DconfigFile=configurationFile
```

It is possible to update multiple Pom files at the same time using * in the Pom path, like:
```
-DupdatePom=/tmp/repo/*/pom.xml
```

### Configuration file
The configuration file defines the properties to be updated. The file is in CSV format and has 3 fields:
* XPath to the property that will be updated
* Source Type of the reference used for the update (VARIABLE or FILE)
* Reference Property (Reference Pom name + property XPath) or Variable name.
 
There are some configurations files as part of this project in "configs" folder.


#### Example of FILE source type
```
//properties/version.commons-io;FILE;wildflyCorePom://properties/version.commons-io
```
Where "wildflyCorePom" is the reference Pom which contains the version for the update and "//properties/version.commons-io" is the property XPath.

To define "wildflyCorePom" value, add it as a Maven option:
```
-DwildflyCorePom=/path/to/file/pom.xml
```

The Pom path accepts both a path to a local file or a HTTP URL.


#### Example of VARIABLE source type
```
//properties/version.server.bom;VARIABLE;serverBomVersion
```
Where "serverBomVersion" is the name of the option that will be given to Maven.

To define "serverBomVersion" value, add it as a Maven option:
```
-DserverBomVersion=14.0.1.Final
```


### Extra options
The following Maven options are optional
* -Dupdate - Automatically update versions in target Pom. (-Dupdate=false is dry-run mode)
* -DstrictMode - Execution fails if a property defined in the configuration file is not found. (-DstrictMode=true is fail-fast mode)

Default value for -Dupdate is true and -DstricMode is false


## License
This project is licensed under the Apache License 2.0
