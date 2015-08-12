/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.keub.maven.plugin.utils;

import java.io.File;

public class GitHelper {
	public static String extractRepositoryNameFromUrl(String url) {
		File f = new File(url);
		String name = f.getName();
		if (name.endsWith(Constants.EXTENSION_GIT)) {
			int pos = name.indexOf(Constants.EXTENSION_GIT);
			name = name.substring(0, pos);
		}
		return name;
	}
}
