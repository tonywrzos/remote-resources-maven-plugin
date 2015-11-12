package com.github.keub.maven.plugin.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtils {

	/**
	 * <p>
	 * copy reader into writer
	 * </p>
	 * 
	 * @param reader
	 * @param writer
	 * @throws IOException
	 */
	public static void writeFile(BufferedInputStream reader,
			BufferedOutputStream writer) throws IOException {
		byte[] buff = new byte[8192];
		int numChars;
		while ((numChars = reader.read(buff, 0, buff.length)) != -1) {
			writer.write(buff, 0, numChars);
		}
	}

	/**
	 * <p>
	 * create target folders file if doesnt exist
	 * </p>
	 * 
	 * @param absoluteFile
	 * @throws FileNotFoundException
	 */
	public static void createIntermediateFolders(String absoluteFile)
			throws FileNotFoundException {
		if (absoluteFile == null) {
			throw new FileNotFoundException("'" + absoluteFile
					+ "' is not valid");
		}
		// normalize the path
		String normalizedAbsoluteFile = normalizePath(absoluteFile);
		int endIndex = normalizedAbsoluteFile.endsWith(PathUtils.SLASH) ? normalizedAbsoluteFile
				.length() : normalizedAbsoluteFile.lastIndexOf(PathUtils.SLASH);

		String filePath = normalizedAbsoluteFile.substring(0, endIndex);
		new File(filePath).mkdirs();
	}

	/**
	 * <p>
	 * standardization of a path by replacing the slash and backslash by a file
	 * separator
	 * </p>
	 * 
	 * @param path
	 *            the path to normalize
	 * @return normalized string
	 */
	public static String normalizePath(String path) {
		String retval = path;
		retval = retval.replaceAll(PathUtils.REGEX_MULTIPLE_SLASH,
				PathUtils.REGEX_SLASH);
		retval = retval.replaceAll(PathUtils.REGEX_BACKSLASH,
				PathUtils.REGEX_SLASH);
		return retval;
	}

}
