package com.github.keub.maven.plugin.strategy;

import java.io.File;

import com.github.keub.maven.plugin.exception.ResourceExecutionException;
import com.github.keub.maven.plugin.model.Resource;
import com.github.keub.maven.plugin.resources.CopyResourcesMojo;
import com.github.keub.maven.plugin.service.FtpService;

public class FtpStrategy implements ProtocolStrategy {

	public String getSourceFolder(Resource resource,
			CopyResourcesMojo copyResourcesMojo, File workspacePlugin)
			throws ResourceExecutionException {
		return FtpService.getSourceFolder(resource, copyResourcesMojo,
				workspacePlugin);
	}

}
