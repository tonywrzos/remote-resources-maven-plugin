package com.github.keub.maven.plugin.resources;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.util.Set;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.github.keub.maven.plugin.exception.InvalidOutputDirectoryException;
import com.github.keub.maven.plugin.exception.ResourceExecutionException;
import com.github.keub.maven.plugin.model.Resource;
import com.github.keub.maven.plugin.service.CheckService;
import com.github.keub.maven.plugin.service.ResourceService;

/**
 * Copy resources for the main source code to the main output directory. Always
 * uses the project.build.resources element to specify the resources to copy.
 *
 */
@Mojo(name = "copy", defaultPhase = LifecyclePhase.GENERATE_RESOURCES, threadSafe = true)
public class CopyResourcesMojo extends AbstractMojo {

	/**
	 * The output directory into which to copy the resources.
	 */
	@Parameter(required = true)
	private File outputDirectory;

	/**
	 * Set of resource
	 */
	@Parameter(required = true)
	private Set<Resource> resources;

	/**
	 * Maven project variable
	 */
	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	private MavenProject project;

	/**
	 * Maven session variable
	 */
	@Parameter(defaultValue = "${session}", readonly = true, required = true)
	private MavenSession session;

	/**
	 * entry point for goal 'copy'
	 */
	public void execute() throws MojoExecutionException {

		getLog().debug("- outputDirectory : " + outputDirectory);
		// security
		try {
			CheckService.isValidOutputDirectory(outputDirectory);
		}
		catch (InvalidOutputDirectoryException e) {
			throw new MojoExecutionException("CheckService return error(s)", e);
		}

		getLog().debug("- resources : " + resources);
		for (Resource resource : resources) {
			try {
				ResourceService.execute(this, resource, outputDirectory);
			}
			catch (ResourceExecutionException e) {
				throw new MojoExecutionException("ResourceService return error(s)", e);
			}
		}

	}

	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}

	public MavenSession getSession() {
		return session;
	}

	public void setSession(MavenSession session) {
		this.session = session;
	}

}
