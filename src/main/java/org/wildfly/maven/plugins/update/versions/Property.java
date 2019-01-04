package org.wildfly.maven.plugins.update.versions;

public class Property {
	public static final String SOURCE_TYPE_VARIABLE = "VARIABLE";
	public static final String SOURCE_TYPE_FILE = "FILE";
	public final String targetPropertyXPath;
	public final String sourceType;
	private final String reference;

	public Property(String targetPropertyXPath, String sourceType, String reference) {
		this.targetPropertyXPath = targetPropertyXPath;
		this.sourceType = sourceType;
		this.reference = reference;
	}

	public IPropertyReference getReference() {
		if (sourceType.equals(SOURCE_TYPE_FILE)) {
			return new PropertyReferenceFile(reference);
		} else if (sourceType.equals(SOURCE_TYPE_VARIABLE)) {
			return new PropertyReferenceVariable(reference);
		} else {
			throw new IllegalStateException("Unknown property source type " + sourceType);
		}
	}
}
