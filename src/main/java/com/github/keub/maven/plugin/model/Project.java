package com.github.keub.maven.plugin.model;

import java.net.URI;

/**
 * Object represents a git project
 * 
 */
public class Project {

	private URI uri;
	private String username;
	private String password;
	private String branchTagName;

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

	public String getBranchTagName() {
		return branchTagName;
	}

	public void setBranchTagName(String branchTagName) {
		this.branchTagName = branchTagName;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

}