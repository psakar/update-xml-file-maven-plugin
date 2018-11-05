package org.wildfly.maven.plugins.update.versions;

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

	public String findPropertyVersion(String propertyName) {
		int index = getTokenIndex(propertyName, vtdNav.cloneNav());
		if (index > 0) {
			try {
				return vtdNav.toNormalizedString(index);
			} catch (NavException e) {
				throw new IllegalStateException("Failed to find property " + propertyName + " - " + e.getMessage());
			}
		}
		return null;
	}

	public boolean updatePropertyVersion(String propertyName, String newVersion) {
		int index = getTokenIndex(propertyName, vtdNav.cloneNav());
		if (index > 0) {
			try {
				xmlModifier.bind(vtdNav);
				xmlModifier.updateToken(index, newVersion);
				return true;
			} catch (ModifyException | UnsupportedEncodingException e) {
				throw new IllegalStateException("Failed to update property " + propertyName + " - " + e.getMessage());
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

	private static int getTokenIndex(String propertyName, VTDNav vtdNav) {
		AutoPilot ap = new AutoPilot(vtdNav);
		ap.selectElement(propertyName);
		try {
			while (ap.iterate()) {
				int index = vtdNav.getText();
				if (index != -1) {
					return index;
				}
			}
		} catch (NavException e) {
			throw new IllegalStateException("Failed to get token index for " + propertyName + " - " + e.getMessage());
		}
		return -1;
	}
}
