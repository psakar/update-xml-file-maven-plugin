package org.chare.maven.plugins.updateXmlFile;

import static java.nio.file.Files.readAllBytes;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.ximpleware.AutoPilot;
import com.ximpleware.ModifyException;
import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XMLModifier;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

public class PomManager {
	private File pomFile;
	private VTDNav vtdNav;
	private final XMLModifier xmlModifier;

	public PomManager() {
		xmlModifier = new XMLModifier();
	}

	public void parsePomFile(File pomFile) {
		this.pomFile = pomFile;
		vtdNav = parse();
	}

	public String findPropertyVersion(String propertyXPath) {
		int index = getTokenIndex(propertyXPath, vtdNav.cloneNav());
		if (index > 0) {
			try {
				return vtdNav.toNormalizedString(index);
			} catch (NavException e) {
				throw new IllegalStateException("Failed to find property " + propertyXPath + " - " + e.getMessage());
			}
		}
		return null;
	}

	public boolean updatePropertyVersion(String propertyXPath, String newVersion) {
		int index = getTokenIndex(propertyXPath, vtdNav.cloneNav());
		if (index > 0) {
			try {
				xmlModifier.bind(vtdNav);
				xmlModifier.updateToken(index, newVersion);
				return true;
			} catch (ModifyException | UnsupportedEncodingException e) {
				throw new IllegalStateException("Failed to update property " + propertyXPath + " - " + e.getMessage());
			}
		}
		return false;
	}

	public void writeToPomFile() {
		try {
			xmlModifier.output(pomFile.getAbsolutePath());
			vtdNav = xmlModifier.outputAndReparse();
		} catch (Exception e) {
			throw new IllegalStateException("Could not write to " + pomFile + " - " + e.getMessage(), e);
		}
	}

	private VTDNav parse() {
		try {
			VTDGen vg = new VTDGen();
			byte[] input = readAllBytes(pomFile.toPath());
			vg.setDoc(input);
			vg.parse(false);
			return vg.getNav();
		} catch (ParseException | IOException e) {
			throw new IllegalStateException("Could not parse " + pomFile + " - " + e.getMessage());
		}
	}

	private static int getTokenIndex(String propertyXPath, VTDNav vtdNav) {
		AutoPilot ap = new AutoPilot(vtdNav);
		try {
			ap.selectXPath(propertyXPath);
			while (ap.evalXPath() != -1) {
				int index = vtdNav.getText();
				if (index != -1) {
					return index;
				}
			}
		} catch (NavException | XPathEvalException | XPathParseException e) {
			return -1;
		}
		return -1;
	}
}
