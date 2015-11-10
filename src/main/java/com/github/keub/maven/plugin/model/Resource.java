package com.github.keub.maven.plugin.model;

import java.net.URI;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * <p>
 * This object represents a resource as it is described in the pom.xml resources
 * in tags
 * </p>
 * 
 */
public class Resource {

	@Parameter(required = true)
	private URI uri;

	@Parameter(required = false)
	private String branchTagName;

	@Parameter(required = false)
	private String username;

	@Parameter(required = false)
	private String password;

	@Parameter(required = false)
	private Set<String> includes;

	@Parameter(required = false)
	private Set<String> excludes;

	@Parameter(required = false)
	private Boolean flatten;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resource other = (Resource) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getIncludes() {
		return includes;
	}

	public void setIncludes(Set<String> includes) {
		this.includes = includes;
	}

	public Set<String> getExcludes() {
		return excludes;
	}

	public void setExcludes(Set<String> excludes) {
		this.excludes = excludes;
	}

	public String getBranchTagName() {
		return branchTagName;
	}

	public void setBranchTagName(String branchTagName) {
		this.branchTagName = branchTagName;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("Resource [");
		if (uri != null) {
			builder.append("uri=");
			builder.append(uri);
			builder.append(", ");
		}
		if (branchTagName != null) {
			builder.append("branchTagName=");
			builder.append(branchTagName);
			builder.append(", ");
		}
		if (username != null) {
			builder.append("username=");
			builder.append(username);
			builder.append(", ");
		}
		if (includes != null) {
			builder.append("includes=");
			builder.append(toString(includes, maxLen));
			builder.append(", ");
		}
		if (excludes != null) {
			builder.append("excludes=");
			builder.append(toString(excludes, maxLen));
		}
		builder.append("]");
		return builder.toString();
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public Boolean getFlatten() {
		return flatten;
	}

	public void setFlatten(Boolean flatten) {
		this.flatten = flatten;
	}
}
