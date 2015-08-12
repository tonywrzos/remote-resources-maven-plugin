package com.github.keub.maven.plugin.service;

import java.util.HashSet;
import java.util.Set;

import com.github.keub.maven.plugin.utils.AntPathMatcher;

public class ExcludeService {

	/**
	 * <p>
	 * exclude only file match with pattern into includes set
	 * </p>
	 * 
	 * @param excludes
	 * @param files
	 */
	public static Set<String> process(Set<String> excludes, Set<String> files) {
		if (excludes == null || files == null) {
			return files;
		}
		Set<String> retval = new HashSet<String>();
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		for (String pattern : excludes) {
			for (String path : files) {
				boolean match = antPathMatcher.match(pattern, FileService.normalizePath(path));
				if (!match) {
					retval.add(path);
				}
			}
		}
		return retval;
	}

}
