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
	public static void writeFile(BufferedInputStream reader, BufferedOutputStream writer) throws IOException {
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
	public static void createIntermediateFolders(String absoluteFile) throws FileNotFoundException {
		if (absoluteFile == null) {
			throw new FileNotFoundException("'" + absoluteFile + "' is not valid");
		}
		String filePath = absoluteFile.substring(0, absoluteFile.lastIndexOf(File.separator));
		new File(filePath).mkdirs();
	}

}
