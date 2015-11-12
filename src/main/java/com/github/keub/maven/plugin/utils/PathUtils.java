package com.github.keub.maven.plugin.utils;

import java.io.File;

import org.eclipse.jgit.util.StringUtils;

import com.github.keub.maven.plugin.model.Project;
import com.github.keub.maven.plugin.resources.CopyResourcesMojo;

public class PathUtils {

	public static final String REGEX_MULTIPLE_SLASH = "\\\\/\\\\/";
	public static final String REGEX_SLASH = "\\\\/";
	public static final String REGEX_BACKSLASH = "\\\\";
	public static final String SLASH = "/";

	/**
	 * <p>
	 * add the character ' /' at the end value
	 * </p>
	 * 
	 * @param src
	 */
	public static void addEndingSlashIfNeeded(StringBuilder src) {
		if (src == null || StringUtils.isEmptyOrNull(String.valueOf(src))) {
			return;
		}
		if (src != null
				&& src.charAt(src.length() - 1) != File.separator.charAt(0)) {
			src.append(File.separator);
		}
		return;
	}

	/**
	 * <p>
	 * returns a file that will be required to retrieve the plugin resources
	 * required to copy
	 * </p>
	 * 
	 * @param project
	 * @param copyResourcesMojo
	 * @return
	 */
	public static File getWorkspace(CopyResourcesMojo copyResourcesMojo) {
		copyResourcesMojo.getLog().debug("Find workspace");
		// init workspace in absolute target path project : /target
		StringBuilder retval = new StringBuilder(copyResourcesMojo.getProject()
				.getBuild().getDirectory());
		addEndingSlashIfNeeded(retval);
		retval.append(Constants.WORKSPACE_TARGET_DIR);
		copyResourcesMojo.getLog().debug("Workspace is : '" + retval + "'");
		return new File(retval.toString());
	}

	/**
	 * <p>
	 * returns the absolute path or the local path is the clone git project
	 * which is the url parameter
	 * </p>
	 * 
	 * @param rootLocation
	 * @param url
	 * 
	 * @param project
	 * @param rootLocation
	 * @return
	 */
	public static String getAbsoluteProjectPath(Project project,
			String rootLocation) {
		StringBuilder retval = new StringBuilder(rootLocation);
		PathUtils.addEndingSlashIfNeeded(retval);
		retval.append(GitHelper.extractRepositoryNameFromUrl(String
				.valueOf(project.getUri())));
		PathUtils.addEndingSlashIfNeeded(retval);
		// construction du chemin local vers le clone
		return retval.toString();
	}

}
