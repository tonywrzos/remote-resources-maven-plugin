package com.github.keub.maven.plugin.service;

import java.io.File;

import com.github.keub.maven.plugin.exception.InvalidOutputDirectoryException;

public class CheckService {

	/**
	 * <p>
	 * check if outputDirectory is folder with write permission
	 * </p>
	 * 
	 * @param outputDirectory
	 *            the file represents output folder
	 * @throws InvalidOutputDirectoryException
	 *             exeception throw if folder {@code ouutputdirectory} is not
	 *             valid
	 */
	public static void isValidOutputDirectory(File outputDirectory)
			throws InvalidOutputDirectoryException {

		if (!outputDirectory.exists()) {
			throw new InvalidOutputDirectoryException("'" + outputDirectory
					+ "' doesn't exist.");
		}
		if (!outputDirectory.isDirectory()) {
			throw new InvalidOutputDirectoryException("'" + outputDirectory
					+ "' is not a directory.");
		}
		if (!outputDirectory.canWrite()) {
			throw new InvalidOutputDirectoryException("Unable to wrote into '"
					+ outputDirectory + "'.");
		}
	}
}
