package com.caseblock.maven.plugins.versions;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import com.caseblock.maven.plugins.models.Version;

public class CurrentVersion extends Version {

	public CurrentVersion(String version, Log log) throws MojoFailureException {
		super(version, log);
	}
}
