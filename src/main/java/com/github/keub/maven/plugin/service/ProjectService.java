package com.github.keub.maven.plugin.service;

import com.github.keub.maven.plugin.model.Project;
import com.github.keub.maven.plugin.model.Resource;

public class ProjectService {

	/**
	 * <p>
	 * get project from a resource
	 * </p>
	 * 
	 * @param resource
	 * @return
	 */
	public static Project getProject(Resource resource) {
		Project retval = new Project();
		retval.setUri(resource.getUri());
		retval.setUsername(resource.getUsername());
		retval.setPassword(resource.getPassword());
		retval.setBranchTagName(resource.getBranchTagName());
		return retval;
	}

}
