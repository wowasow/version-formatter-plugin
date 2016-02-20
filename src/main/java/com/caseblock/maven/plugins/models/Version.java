package com.caseblock.maven.plugins.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import com.caseblock.maven.plugins.models.Component.Types;

import lombok.Data;

@Data
public class Version {

	public static final String SNAPSHOT_TAG = "-SNAPSHOT";
	public static final String VERSION_COMPONENTS_SEPARATOR = "\\.";

	// keeps all components in the version
	private List<Component> components;

	// current string version, always without a snapshot
	private String version;

	public Version(String version, Log log) throws MojoFailureException {
		setVersion(version);

		log.debug(String.format("Creating version for string: %s", version));
		log.debug(String.format("Splitted version components: %s, size: %s", components, components.size()));
	}

	private Version incrementComponent(Types type) {
		Component comp = this.components.get(type.ordinal());
		comp.increment();

		return this;
	}

	protected void incrementMajor() {
		resetComponent(Types.MINOR);
		incrementComponent(Types.MAJOR);
	}

	protected void incrementMinor() {
		incrementComponent(Types.MINOR);
	}

	protected void incrementRelease() {
		resetComponent(Types.MINOR);
		resetComponent(Types.MAJOR);
		incrementComponent(Types.RELEASE);
	}

	public final void setVersion(String version) throws MojoFailureException {
		this.version = version.replace(SNAPSHOT_TAG, "");
		initComponents();
	}

	
	private final void initComponents() throws MojoFailureException {
		if (this.version == null)
			throw new MojoFailureException("Invalid state");

		this.components = new ArrayList<>();

		String[] splittedComponents = version.split(VERSION_COMPONENTS_SEPARATOR);

		if (splittedComponents.length != Component.Types.values().length)
			throw new MojoFailureException("Invalid plugin invocation, to little version components.");

		for (int i = 0; i < splittedComponents.length; i++) {
			String strComp = splittedComponents[i];
			Long value = Long.parseLong(strComp);
			Component comp = Component.builder().type(Types.of(i)).value(value).build();
			this.components.add(comp);
		}
		
	}

	protected Version resetComponent(Types type) {
		Component comp = this.components.get(type.ordinal());
		comp.reset();

		return this;
	}

	@Override
	public String toString() {
		return getVersion();
	}

	public final String getVersion() {
		StringBuffer buffer = new StringBuffer();

		this.getComponents().stream()
			.forEach(buffer::append);

		return buffer.toString();
	}
	
	public final String getSnapshotVersion() {
		return getVersion() + SNAPSHOT_TAG;
	}



}