package com.caseblock.maven.plugins.versions;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import com.caseblock.maven.plugins.models.Version;

public class ReleaseVersion extends Version {

	public ReleaseVersion(String version, Log log) throws MojoFailureException {
		super(version, log);
		
		this.incrementRelease();
	}
}
