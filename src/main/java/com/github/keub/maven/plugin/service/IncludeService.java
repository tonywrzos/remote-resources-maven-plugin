package com.github.keub.maven.plugin.service;

import java.util.HashSet;
import java.util.Set;

import com.github.keub.maven.plugin.utils.AntPathMatcher;

public class IncludeService {

	/**
	 * include only file match with pattern into includes set
	 * 
	 * @param includes
	 * @param files
	 */
	public static Set<String> process(Set<String> includes, Set<String> files) {
		if (includes == null || files == null) {
			return files;
		}
		Set<String> retval = new HashSet<String>();
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		for (String pattern : includes) {
			for (String path : files) {
				boolean match = antPathMatcher.match(pattern, FileService.normalizePath(path));
				if (match) {
					retval.add(path);
				}
			}
		}
		return retval;
	}

}
