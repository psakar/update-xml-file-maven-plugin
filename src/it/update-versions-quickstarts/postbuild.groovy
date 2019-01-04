file = new File(basedir, 'resources/pom_quickstarts.xml');
expectedFile = new File(basedir, 'resources/expected_pom_quickstarts.xml');
assert expectedFile.text.equals(file.text);
return true;