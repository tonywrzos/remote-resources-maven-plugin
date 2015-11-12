package com.github.keub.maven.plugin.service;

import java.util.HashSet;
import java.util.Set;

import com.github.keub.maven.plugin.resources.CopyResourcesMojo;
import com.github.keub.maven.plugin.utils.AntPathMatcher;
import com.github.keub.maven.plugin.utils.FileUtils;

public class IncludeService {

	/**
	 * include only file match with pattern into includes set
	 * 
	 * @param copyResourcesMojo
	 * 
	 * @param includes
	 * @param files
	 */
	public static Set<String> process(CopyResourcesMojo copyResourcesMojo,
			Set<String> includes, Set<String> files) {
		copyResourcesMojo.getLog().debug("Start include.");
		if (includes == null || files == null) {
			return files;
		}
		Set<String> retval = new HashSet<String>();
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		for (String pattern : includes) {
			copyResourcesMojo.getLog().debug(
					"Process include with pattern : '" + pattern + "'.");
			for (String path : files) {
				boolean match = antPathMatcher.match(pattern,
						FileUtils.normalizePath(path));
				copyResourcesMojo.getLog().debug(
						"Test pattern on file : '" + path + "', result is : '"
								+ match + "'.");
				if (match) {
					retval.add(path);
				}
			}
		}
		return retval;
	}

}
