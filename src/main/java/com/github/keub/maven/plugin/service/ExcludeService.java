package com.github.keub.maven.plugin.service;

import java.util.HashSet;
import java.util.Set;

import com.github.keub.maven.plugin.resources.CopyResourcesMojo;
import com.github.keub.maven.plugin.utils.AntPathMatcher;
import com.github.keub.maven.plugin.utils.FileUtils;

public class ExcludeService {

	/**
	 * <p>
	 * exclude only file match with pattern into includes set
	 * </p>
	 * 
	 * @param copyResourcesMojo
	 * 
	 * @param excludes
	 *            ant patterns collection
	 * @param files
	 *            files set to process
	 * @return files set
	 * 
	 */
	public static Set<String> process(CopyResourcesMojo copyResourcesMojo,
			Set<String> excludes, Set<String> files) {
		copyResourcesMojo.getLog().debug("Start exclude.");
		if (excludes == null || files == null) {
			return files;
		}
		Set<String> retval = new HashSet<String>();
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		for (String pattern : excludes) {
			copyResourcesMojo.getLog().debug(
					"Process exclude with pattern : '" + pattern + "'.");
			for (String path : files) {
				boolean match = antPathMatcher.match(pattern,
						FileUtils.normalizePath(path));
				copyResourcesMojo.getLog().debug(
						"Test pattern on file : '" + path + "', result is : '"
								+ match + "'.");
				if (!match) {
					retval.add(path);
				}
			}
		}
		return retval;
	}

}
