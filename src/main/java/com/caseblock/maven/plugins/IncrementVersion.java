package com.caseblock.maven.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.caseblock.maven.plugins.models.Version;
import com.caseblock.maven.plugins.versions.CurrentVersion;
import com.caseblock.maven.plugins.versions.MajorVersion;
import com.caseblock.maven.plugins.versions.MinorVersion;
import com.caseblock.maven.plugins.versions.ReleaseVersion;

@Mojo(name = "increment", defaultPhase = LifecyclePhase.INITIALIZE)
public class IncrementVersion extends AbstractMojo {
	
	private static final String CURRENT_PROPERTY = "version.current";
	private static final String CURRENT_SNAPSHOT_PROPERTY = "version.current.snapshot";
	
	private static final String MINOR_PROPERTY = "version.next.minor";
	private static final String MINOR_SNAPSHOT_PROPERTY = "version.next.minor.snapshot";

	private static final String MAJOR_PROPERTY = "version.next.major";
	private static final String MAJOR_SNAPSHOT_PROPERTY = "version.next.major.snapshot";

	private static final String RELEASE_PROPERTY = "version.next.release";
	private static final String RELEASE_SNAPSHOT_PROPERTY = "version.next.release.snapshot";
	
	@Parameter(defaultValue = "${project.version}")
	private String projectVersion;

	@Parameter(defaultValue = "${project}")
	private MavenProject project;

	public void execute() throws MojoExecutionException, MojoFailureException {
		
		Version currentVersion = new CurrentVersion(project.getVersion(), getLog());
		Version minorVersion = new MinorVersion(project.getVersion(), getLog());
		Version majorVersion = new MajorVersion(project.getVersion(), getLog());
		Version releaseVersion = new ReleaseVersion(project.getVersion(), getLog());
		
		// set properties
		setProperty(CURRENT_PROPERTY, currentVersion.getVersion());
		setProperty(CURRENT_SNAPSHOT_PROPERTY, currentVersion.getSnapshotVersion());

		setProperty(MINOR_PROPERTY, minorVersion.getVersion());
		setProperty(MINOR_SNAPSHOT_PROPERTY, minorVersion.getSnapshotVersion());

		setProperty(MAJOR_PROPERTY, majorVersion.getVersion());
		setProperty(MAJOR_SNAPSHOT_PROPERTY, majorVersion.getSnapshotVersion());
		
		setProperty(RELEASE_PROPERTY, releaseVersion.getVersion());
		setProperty(RELEASE_SNAPSHOT_PROPERTY, releaseVersion.getSnapshotVersion());

		// log properties
		logProperty(CURRENT_PROPERTY);
		logProperty(CURRENT_SNAPSHOT_PROPERTY);

		logProperty(MINOR_PROPERTY);
		logProperty(MINOR_SNAPSHOT_PROPERTY);
		
		logProperty(MAJOR_PROPERTY);
		logProperty(MAJOR_SNAPSHOT_PROPERTY);

		logProperty(RELEASE_PROPERTY);
		logProperty(RELEASE_SNAPSHOT_PROPERTY);
	}
	
	public void logProperty(String name) {
		getLog().debug(String.format("%s: %s", name, project.getProperties().getProperty(name)));
	}

	public void setProperty(String name, String value) {
		project.getProperties().setProperty(name, value);
	}
}
