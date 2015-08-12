package com.github.keub.maven.plugin.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.keub.maven.plugin.exception.InvalidSourceException;
import com.github.keub.maven.plugin.exception.ProtocolException;
import com.github.keub.maven.plugin.exception.ResourceExecutionException;
import com.github.keub.maven.plugin.model.Resource;
import com.github.keub.maven.plugin.resources.CopyResourcesMojo;
import com.github.keub.maven.plugin.strategy.ProtocolStrategy;
import com.github.keub.maven.plugin.utils.PathUtils;

public class ResourceService {

	/**
	 * execute with a resource
	 * 
	 * @param copyResourcesMojo
	 * @param resource
	 * @param outputDirectory
	 * @throws ResourceExecutionException
	 */
	public static void execute(CopyResourcesMojo copyResourcesMojo, Resource resource, File outputDirectory)
			throws ResourceExecutionException {

		copyResourcesMojo.getLog().debug("Execute resource : " + resource);
		// choose a location to checkout project
		File workspacePlugin = PathUtils.getWorkspace(copyResourcesMojo);
		// security
		if (workspacePlugin.exists()) {
			copyResourcesMojo.getLog().debug(
					"delete workspacePlugin resource because already exist : '" + workspacePlugin.getAbsolutePath()
							+ "'");
			if (workspacePlugin.delete()) {
				throw new ResourceExecutionException("Unable to delete workspace plugin directory '" + workspacePlugin
						+ "'");
			}
		}
		// find correct strategy
		ProtocolStrategy strategy;
		try {
			strategy = ProtocolService.getStrategy(resource);
		}
		catch (ProtocolException e) {
			throw new ResourceExecutionException("Protocol implementation not found", e);
		}
		// strategy return a source folder
		String sourceFolder = strategy.getSourceFolder(resource, copyResourcesMojo, workspacePlugin);
		// source folder is copied into destination
		try {
			FileService.copyFilesIntoOutputDirectory(copyResourcesMojo, new File(sourceFolder), outputDirectory,
					resource);
		}
		catch (FileNotFoundException e) {
			throw new ResourceExecutionException(e);
		}
		catch (InvalidSourceException e) {
			throw new ResourceExecutionException(e);
		}
		catch (IOException e) {
			throw new ResourceExecutionException(e);
		}
	}

}
