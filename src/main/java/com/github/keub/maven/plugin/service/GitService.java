package com.github.keub.maven.plugin.service;

import java.io.File;
import java.nio.file.Paths;

import com.github.keub.maven.plugin.exception.GitException;
import com.github.keub.maven.plugin.exception.ResourceExecutionException;
import com.github.keub.maven.plugin.git.GitRepository;
import com.github.keub.maven.plugin.model.Project;
import com.github.keub.maven.plugin.model.Resource;
import com.github.keub.maven.plugin.resources.CopyResourcesMojo;
import com.github.keub.maven.plugin.utils.Constants;
import com.github.keub.maven.plugin.utils.PathUtils;

public class GitService {

	private final static GitRepository gitRepository = new GitRepository();

	/**
	 * <p>
	 * recovery of a deposit of clone git locally
	 * </p>
	 * 
	 * @param resource
	 *            a resource
	 * @param copyResourcesMojo
	 *            mojo attach to goal copy
	 * @param workspacePlugin
	 *            file as workspace where plugin clone git repository
	 * @return source folder
	 * @throws ResourceExecutionException
	 * 
	 */
	public static String getSourceFolder(Resource resource,
			CopyResourcesMojo copyResourcesMojo, File workspacePlugin)
			throws ResourceExecutionException {
		// Build a git project
		Project project = ProjectService.getProject(resource);
		// Clone git project
		try {
			GitService.cloneProjectIntoWorkspace(project,
					workspacePlugin.getAbsolutePath());
			return PathUtils.getAbsoluteProjectPath(project,
					workspacePlugin.getAbsolutePath());
		} catch (GitException e) {
			throw new ResourceExecutionException("Error when clone Project : "
					+ project, e);
		}
	}

	/**
	 * 
	 * @param project
	 * @param targetFolder
	 * @throws GitException
	 */
	public static void cloneProjectIntoWorkspace(Project project,
			String targetFolder) throws GitException {

		String url = String.valueOf(project.getUri());
		String gitCommiterName = project.getUsername();
		String gitCommiterPassword = project.getPassword();

		if (url == null || url.isEmpty() || url.indexOf("/") == -1) {
			return;
		}
		String absolutePath = PathUtils.getAbsoluteProjectPath(project,
				targetFolder);
		File absoluteFolder = new File(absolutePath);
		// test de presence du clone
		if (absoluteFolder.exists()) {
			// le depot a deja ete clone - on update
			String branchTagName = project.getBranchTagName() == null ? Constants.MASTER_NAME
					: project.getBranchTagName();
			gitRepository.url(url).localPath(Paths.get(absolutePath))
					.credentials(gitCommiterName, gitCommiterPassword)
					.disableCertificateValidation().selectBranch(branchTagName)
					.fetch().hardReset(branchTagName);

		} else {
			// premier clone
			gitRepository.url(url).localPath(Paths.get(targetFolder))
					.credentials(gitCommiterName, gitCommiterPassword)
					.disableCertificateValidation()
					.disableHostnameVerification().cloneRepository()
					.localPath(Paths.get(absolutePath));

		}
	}

}
