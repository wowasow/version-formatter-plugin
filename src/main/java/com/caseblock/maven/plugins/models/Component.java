package com.caseblock.maven.plugins.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Component {
	
	public enum Types {
		RELEASE, MAJOR, MINOR;
		
		public static Types of(int order) {
			if(order > Types.values().length) 
				return Types.RELEASE;

			for(Types value : Types.values()) {
				if(value.ordinal() == order)
					return value;
			}

			return Types.RELEASE;
		}
	}

	private Types type;
	private Long value;
	
	public Component increment() {
		this.value += 1;
		return this;
	}
	
	public Component reset() {
		this.value = 0L;
		return this;
	}
	
	/**
	 * Only release component doesn't begin with comma.
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return Types.RELEASE.equals(type) ? value + "" : "." + value;
	}

}
