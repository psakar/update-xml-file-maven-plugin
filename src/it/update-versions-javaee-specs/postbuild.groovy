file = new File(basedir, 'resources/pom_javaee_specs.xml');
expectedFile = new File(basedir, 'resources/expected_pom_javaee_specs.xml');
assert expectedFile.text.equals(file.text);
return true;