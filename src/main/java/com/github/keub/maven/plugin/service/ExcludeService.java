package com.github.keub.maven.plugin.service;

import java.util.HashSet;
import java.util.Set;

import com.github.keub.maven.plugin.utils.AntPathMatcher;
import com.github.keub.maven.plugin.utils.FileUtils;

public class ExcludeService {

	/**
	 * <p>
	 * exclude only file match with pattern into includes set
	 * </p>
	 * 
	 * @param excludes
	 *            ant patterns collection
	 * @param files
	 *            files set to process
	 * @return files set
	 * 
	 */
	public static Set<String> process(Set<String> excludes, Set<String> files) {
		if (excludes == null || files == null) {
			return files;
		}
		Set<String> retval = new HashSet<String>();
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		for (String pattern : excludes) {
			for (String path : files) {
				boolean match = antPathMatcher.match(pattern,
						FileUtils.normalizePath(path));
				if (!match) {
					retval.add(path);
				}
			}
		}
		return retval;
	}

}
