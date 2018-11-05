file = new File(basedir, 'pom_javaee_specs.xml');
expectedFile = new File(basedir, 'expected_pom_javaee_specs.xml');
assert expectedFile.text.equals(file.text);
return true;