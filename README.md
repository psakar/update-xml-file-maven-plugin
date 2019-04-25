# Update xml file
Update xml file is a Maven plugin that updates the text values in xml file based on provided values or values in anothers xml files.
What should be updated from where can be stored in configuration file


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
* -DupdateXmlFile.updatedFile  - xml file that will be updated
* -DupdateXmlFile.configFile - configuration file (local file or HTTP URL)

An example of how to run:
```
mvn org.chare.maven.plugins:update-xml-file-maven-plugin:update \
	-DupdateXmlFile.updatedFile=/path/to/target/pom.xml \
	-DupdateXmlFile.configFile=configurationFile
```

It is possible to update multiple xml files at the same time using * in the Pom path, like:
```
-DupdateXmlFile.updatedFile=/tmp/repo/*/pom.xml
```

### Configuration file
The configuration file defines the properties to be updated. The file is in CSV format and has 3 fields:
* XPath to the property that will be updated
* Source Type of the reference used for the update (VARIABLE or FILE)
* Reference Property (reference xml file name + property XPath) or Variable name.

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

Value can be path to a local file or HTTP URL.


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
* -DupdateXmlFile.update - Automatically update versions in target file. (-DupdateXmlFile.update=false is dry-run mode)
* -DupdateXmlFile.strictMode - Execution fails if a property defined in the configuration file is not found. (-DupdateXmlFile.strictMode=true is fail-fast mode)

Default value for -DupdateXmlFile.update is true and -DupdateXmlFile.stricMode is false


## License
This project is licensed under the Apache License 2.0
