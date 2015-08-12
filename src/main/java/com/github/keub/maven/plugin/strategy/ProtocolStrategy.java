package com.github.keub.maven.plugin.strategy;

import java.io.File;

import com.github.keub.maven.plugin.exception.ResourceExecutionException;
import com.github.keub.maven.plugin.model.Resource;
import com.github.keub.maven.plugin.resources.CopyResourcesMojo;

public interface ProtocolStrategy {

	String getSourceFolder(Resource resource, CopyResourcesMojo copyResourcesMojo, File workspacePlugin)
			throws ResourceExecutionException;

}
