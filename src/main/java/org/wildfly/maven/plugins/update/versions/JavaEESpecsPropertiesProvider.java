package org.wildfly.maven.plugins.update.versions;

import static java.util.Arrays.asList;
import static org.wildfly.maven.plugins.update.versions.Constants.STREAM_72CDX;
import static org.wildfly.maven.plugins.update.versions.Constants.STREAM_72X;
import static org.wildfly.maven.plugins.update.versions.Constants.STREAM_73CDX;
import static org.wildfly.maven.plugins.update.versions.Constants.STREAM_73X;

import java.io.File;
import java.util.List;

public class JavaEESpecsPropertiesProvider implements IPropertiesProvider {
	private final File wildflyPom;
	private final File wildflyCorePom;

	public JavaEESpecsPropertiesProvider(File wildflyPom, File wildflyCorePom) {
		this.wildflyPom = wildflyPom;
		this.wildflyCorePom = wildflyCorePom;
	}

	public List<Property> getPropertiesFor(String stream) {
		switch (stream) {
			case STREAM_73CDX:
				return getPropertiesFrom73CDx();
			case STREAM_73X:
				return getPropertiesFrom73x();
			case STREAM_72CDX:
			case STREAM_72X:
				return getPropertiesFrom72xAnd72CDx();
			default:
				return getPropertiesFromOtherStreams();
		}
	}

	private List<Property> getPropertiesFrom73CDx() {
		return asList(
			new Property("version.javax.enterprise", "version.javax.enterprise", wildflyPom),
			new Property("version.javax.inject", "version.javax.inject.javax.inject", wildflyCorePom),
			new Property("version.javax.json", "version.javax.json.javax-json-api", wildflyCorePom),
			new Property("version.javax.json.bind", "version.javax.json.bind.api", wildflyPom),
			new Property("version.javax.jws", "version.javax.jws.jsr181-api", wildflyPom),
			new Property("version.javax.mail", "version.javax.mail", wildflyPom),
			new Property("version.javax.security.enterprise", "version.javax.security.enterprise", wildflyPom),
			new Property("version.javax.validation", "version.javax.validation", wildflyPom),
			new Property("version.org.apache.jstl", "version.org.apache.jstl", wildflyPom),
			new Property("version.javax.persistence", "version.javax.persistence", wildflyPom),
			new Property("version.org.jboss.spec.javax.annotation", "version.org.jboss.spec.javax.annotation.jboss-annotations-api_1.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.batch", "version.org.jboss.spec.javax.batch.jboss-batch-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.ejb", "version.org.jboss.spec.javax.ejb.jboss-ejb-api_3.2_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.el", "version.org.jboss.spec.javax.el.jboss-el-api_3.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.enterprise.concurrent", "version.org.jboss.spec.javax.enterprise.concurrent.jboss-concurrency-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.faces", "version.org.jboss.spec.javax.faces.jboss-jsf-api_2.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.interceptor", "version.org.jboss.spec.javax.interceptor.jboss-interceptors-api_1.2_spec", wildflyCorePom),
			new Property("version.org.jboss.spec.javax.jms", "version.org.jboss.spec.javax.jms.jboss-jms-api_2.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.management.j2ee", "version.org.jboss.spec.javax.management.j2ee.jboss-j2eemgmt-api_1.1_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.resource", "version.org.jboss.spec.javax.resource.jboss-connector-api_1.7_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.rmi", "version.org.jboss.spec.javax.rmi.jboss-rmi-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.security.auth.message", "version.org.jboss.spec.javax.security.auth.message.jboss-jaspi-api_1.1_spec", wildflyCorePom),
			new Property("version.org.jboss.spec.javax.security.jacc", "version.org.jboss.spec.javax.security.jacc.jboss-jacc-api_1.5_spec", wildflyCorePom),
			new Property("version.org.jboss.spec.javax.servlet", "version.org.jboss.spec.javax.servlet.jboss-servlet-api_4.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.servlet.jsp", "version.org.jboss.spec.javax.servlet.jsp.jboss-jsp-api_2.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.transaction", "version.org.jboss.spec.javax.transaction.jboss-transaction-api_1.2_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.websocket", "version.org.jboss.spec.javax.websockets", wildflyPom),
			new Property("version.org.jboss.spec.javax.ws.rs.jboss-jaxrs-api_2.1_spec", "version.org.jboss.spec.javax.ws.jboss-jaxrs-api_2.1_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.bind", "version.org.jboss.spec.javax.xml.bind.jboss-jaxb-api_2.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.soap", "version.org.jboss.spec.javax.xml.soap.jboss-saaj-api_1.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.ws", "version.org.jboss.spec.javax.xml.ws.jboss-jaxws-api_2.3_spec", wildflyPom)
		);
	}

	private List<Property> getPropertiesFrom73x() {
		return asList(
			new Property("version.javax.enterprise", "version.javax.enterprise", wildflyPom),
			new Property("version.javax.inject", "version.javax.inject.javax.inject", wildflyCorePom),
			new Property("version.javax.json", "version.javax.json.api", wildflyCorePom),
			new Property("version.javax.json.bind", "version.javax.json.bind.api", wildflyPom),
			new Property("version.javax.jws", "version.javax.jws.jsr181-api", wildflyPom),
			new Property("version.javax.mail", "version.javax.mail", wildflyPom),
			new Property("version.javax.security.enterprise", "version.javax.security.enterprise", wildflyPom),
			new Property("version.javax.validation", "version.javax.validation", wildflyPom),
			new Property("version.org.apache.jstl", "version.org.apache.jstl", wildflyPom),
			new Property("version.javax.persistence", "version.javax.persistence", wildflyPom),
			new Property("version.org.jboss.spec.javax.annotation", "version.org.jboss.spec.javax.annotation.jboss-annotations-api_1.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.batch", "version.org.jboss.spec.javax.batch.jboss-batch-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.ejb", "version.org.jboss.spec.javax.ejb.jboss-ejb-api_3.2_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.el", "version.org.jboss.spec.javax.el.jboss-el-api_3.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.enterprise.concurrent", "version.org.jboss.spec.javax.enterprise.concurrent.jboss-concurrency-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.faces", "version.org.jboss.spec.javax.faces.jboss-jsf-api_2.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.interceptor", "version.org.jboss.spec.javax.interceptor.jboss-interceptors-api_1.2_spec", wildflyCorePom),
			new Property("version.org.jboss.spec.javax.jms", "version.org.jboss.spec.javax.jms.jboss-jms-api_2.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.management.j2ee", "version.org.jboss.spec.javax.management.j2ee.jboss-j2eemgmt-api_1.1_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.resource", "version.org.jboss.spec.javax.resource.jboss-connector-api_1.7_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.rmi", "version.org.jboss.spec.javax.rmi.jboss-rmi-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.security.auth.message", "version.org.jboss.spec.javax.security.auth.message.jboss-jaspi-api_1.1_spec", wildflyCorePom),
			new Property("version.org.jboss.spec.javax.security.jacc", "version.org.jboss.spec.javax.security.jacc.jboss-jacc-api_1.5_spec", wildflyCorePom),
			new Property("version.org.jboss.spec.javax.servlet", "version.org.jboss.spec.javax.servlet.jboss-servlet-api_4.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.servlet.jsp", "version.org.jboss.spec.javax.servlet.jsp.jboss-jsp-api_2.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.transaction", "version.org.jboss.spec.javax.transaction.jboss-transaction-api_1.2_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.websocket", "version.org.jboss.spec.javax.websockets", wildflyPom),
			new Property("version.org.jboss.spec.javax.ws.rs.jboss-jaxrs-api_2.1_spec", "version.org.jboss.spec.javax.ws.jboss-jaxrs-api_2.1_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.bind", "version.org.jboss.spec.javax.xml.bind.jboss-jaxb-api_2.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.soap", "version.org.jboss.spec.javax.xml.soap.jboss-saaj-api_1.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.ws", "version.org.jboss.spec.javax.xml.ws.jboss-jaxws-api_2.3_spec", wildflyPom)
		);
	}

	private List<Property> getPropertiesFrom72xAnd72CDx() {
		return asList(
			new Property("version.javax.enterprise", "version.javax.enterprise", wildflyPom),
			new Property("version.javax.inject", "version.javax.inject.javax.inject", wildflyCorePom),
			new Property("version.javax.json", "version.javax.json.api", wildflyPom),
			new Property("version.javax.json.bind", "version.javax.json.bind.api", wildflyPom),
			new Property("version.javax.jws", "version.javax.jws.jsr181-api", wildflyPom),
			new Property("version.javax.mail", "version.javax.mail", wildflyPom),
			new Property("version.javax.security.enterprise", "version.javax.security.enterprise", wildflyPom),
			new Property("version.javax.validation", "version.javax.validation", wildflyPom),
			new Property("version.org.apache.jstl", "version.org.apache.jstl", wildflyPom),
			new Property("version.javax.persistence", "version.javax.persistence", wildflyPom),
			new Property("version.org.jboss.spec.javax.annotation", "version.org.jboss.spec.javax.annotation.jboss-annotations-api_1.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.batch", "version.org.jboss.spec.javax.batch.jboss-batch-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.ejb", "version.org.jboss.spec.javax.ejb.jboss-ejb-api_3.2_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.el", "version.org.jboss.spec.javax.el.jboss-el-api_3.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.enterprise.concurrent", "version.org.jboss.spec.javax.enterprise.concurrent.jboss-concurrency-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.faces", "version.org.jboss.spec.javax.faces.jboss-jsf-api_2.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.interceptor", "version.org.jboss.spec.javax.interceptor.jboss-interceptors-api_1.2_spec", wildflyCorePom),
			new Property("version.org.jboss.spec.javax.jms", "version.org.jboss.spec.javax.jms.jboss-jms-api_2.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.management.j2ee", "version.org.jboss.spec.javax.management.j2ee.jboss-j2eemgmt-api_1.1_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.resource", "version.org.jboss.spec.javax.resource.jboss-connector-api_1.7_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.rmi", "version.org.jboss.spec.javax.rmi.jboss-rmi-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.security.auth.message", "version.org.jboss.spec.javax.security.auth.message.jboss-jaspi-api_1.1_spec", wildflyCorePom),
			new Property("version.org.jboss.spec.javax.security.jacc", "version.org.jboss.spec.javax.security.jacc.jboss-jacc-api_1.5_spec", wildflyCorePom),
			new Property("version.org.jboss.spec.javax.servlet", "version.org.jboss.spec.javax.servlet.jboss-servlet-api_4.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.servlet.jsp", "version.org.jboss.spec.javax.servlet.jsp.jboss-jsp-api_2.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.transaction", "version.org.jboss.spec.javax.transaction.jboss-transaction-api_1.2_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.websocket", "version.org.jboss.spec.javax.websockets", wildflyPom),
			new Property("version.org.jboss.spec.javax.ws.rs.jboss-jaxrs-api_2.1_spec", "version.org.jboss.spec.javax.ws.jboss-jaxrs-api_2.1_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.bind", "version.org.jboss.spec.javax.xml.bind.jboss-jaxb-api_2.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.soap", "version.org.jboss.spec.javax.xml.soap.jboss-saaj-api_1.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.ws", "version.org.jboss.spec.javax.xml.ws.jboss-jaxws-api_2.3_spec", wildflyPom)
		);
	}

	private List<Property> getPropertiesFromOtherStreams() {
		return asList(
			new Property("version.javax.enterprise", "version.javax.enterprise", wildflyPom),
			new Property("version.javax.inject", "version.javax.inject.javax.inject", wildflyCorePom),
			new Property("version.javax.jws", "version.javax.jws.jsr181-api", wildflyPom),
			new Property("version.javax.mail", "version.javax.mail", wildflyPom),
			new Property("version.javax.validation", "version.javax.validation", wildflyPom),
			new Property("version.org.apache.jstl", "version.org.apache.jstl", wildflyPom),
			new Property("version.org.hibernate.javax.persistence", "version.org.hibernate.javax.persistence.hibernate-jpa-2.1-api", wildflyPom),
			new Property("version.org.jboss.spec.javax.annotation", "version.org.jboss.spec.javax.annotation.jboss-annotations-api_1.2_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.batch", "version.org.jboss.spec.javax.batch.jboss-batch-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.ejb", "version.org.jboss.spec.javax.ejb.jboss-ejb-api_3.2_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.el", "version.org.jboss.spec.javax.el.jboss-el-api_3.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.enterprise.concurrent", "version.org.jboss.spec.javax.enterprise.concurrent.jboss-concurrency-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.faces", "version.org.jboss.spec.javax.faces.jboss-jsf-api_2.2_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.interceptor", "version.org.jboss.spec.javax.interceptor.jboss-interceptors-api_1.2_spec", wildflyCorePom),
			new Property("version.org.jboss.spec.javax.jms", "version.org.jboss.spec.javax.jms.jboss-jms-api_2.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.management.j2ee", "version.org.jboss.spec.javax.management.j2ee.jboss-j2eemgmt-api_1.1_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.resource", "version.org.jboss.spec.javax.resource.jboss-connector-api_1.7_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.rmi", "version.org.jboss.spec.javax.rmi.jboss-rmi-api_1.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.security.auth.message", "version.org.jboss.spec.javax.security.auth.message.jboss-jaspi-api_1.1_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.security.jacc", "version.org.jboss.spec.javax.security.jacc.jboss-jacc-api_1.5_spec", wildflyCorePom),
			new Property("version.org.jboss.spec.javax.servlet", "version.org.jboss.spec.javax.servlet.jboss-servlet-api_3.1_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.servlet.jsp", "version.org.jboss.spec.javax.servlet.jsp.jboss-jsp-api_2.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.transaction", "version.org.jboss.spec.javax.transaction.jboss-transaction-api_1.2_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.websocket", "version.org.jboss.spec.javax.websockets", wildflyPom),
			new Property("version.org.jboss.spec.javax.ws.jboss-jaxrs-api_2.0_spec", "version.org.jboss.spec.javax.ws.jboss-jaxrs-api_2.0_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.bind", "version.org.jboss.spec.javax.xml.bind.jboss-jaxb-api_2.2_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.soap", "version.org.jboss.spec.javax.xml.soap.jboss-saaj-api_1.3_spec", wildflyPom),
			new Property("version.org.jboss.spec.javax.xml.ws", "version.org.jboss.spec.javax.xml.ws.jboss-jaxws-api_2.2_spec", wildflyPom)
		);
	}
}
