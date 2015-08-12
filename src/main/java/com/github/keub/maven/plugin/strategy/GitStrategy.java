package com.github.keub.maven.plugin.strategy;

import java.io.File;

import com.github.keub.maven.plugin.exception.ResourceExecutionException;
import com.github.keub.maven.plugin.model.Resource;
import com.github.keub.maven.plugin.resources.CopyResourcesMojo;
import com.github.keub.maven.plugin.service.GitService;

public class GitStrategy implements ProtocolStrategy {

	public String getSourceFolder(Resource resource, CopyResourcesMojo copyResourcesMojo, File workspacePlugin)
			throws ResourceExecutionException {
		return GitService.getSourceFolder(resource, copyResourcesMojo, workspacePlugin);
	}

}
