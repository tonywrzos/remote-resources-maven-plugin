package com.github.keub.maven.plugin.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.github.keub.maven.plugin.exception.InvalidSourceException;
import com.github.keub.maven.plugin.model.Resource;
import com.github.keub.maven.plugin.resources.CopyResourcesMojo;
import com.github.keub.maven.plugin.utils.Constants;
import com.github.keub.maven.plugin.utils.FileUtils;
import com.github.keub.maven.plugin.utils.PathUtils;

public class FileService {

	/**
	 * <p>
	 * copy files into sourceFolder into destination folder
	 * </p>
	 * 
	 * @param copyResourcesMojo
	 *            Implementation of mojo with goal 'copy'
	 * @param sourceFolder
	 *            The source folder
	 * @param destinationFolder
	 *            The destination folder
	 * @param resource
	 *            Resource to process
	 * @param isFlatten
	 * @throws InvalidSourceException
	 *             Exception throw if source is not valid
	 * @throws IOException
	 *             Exception throw if invalid manipulation files
	 */
	static void copyFilesIntoOutputDirectory(
			CopyResourcesMojo copyResourcesMojo, File sourceFolder,
			File destinationFolder, Resource resource, boolean isFlatten)
			throws InvalidSourceException, IOException {
		// check
		if (sourceFolder.isFile()) {
			throw new InvalidSourceException(
					"Expected folder as source, not a file : '" + sourceFolder
							+ "'");
		}
		if (destinationFolder.isFile()) {
			throw new InvalidSourceException("Expected destination as source");
		}
		copyResourcesMojo.getLog().debug(
				"Find file into '" + sourceFolder + "'");
		// find files into source folder
		Set<String> files = findFiles(sourceFolder);
		copyResourcesMojo.getLog().debug("files before processing :" + files);
		// process with optional include/exclude options
		files = processIncludeExclude(copyResourcesMojo, resource, files);

		if (files != null) {
			for (String file : files) {
				// source
				BufferedInputStream reader = new BufferedInputStream(
						new FileInputStream(file));
				// destination
				StringBuilder finalFile = buildAbsoluteFinalFile(file,
						sourceFolder.getAbsolutePath(), destinationFolder,
						isFlatten);

				// prepare writer
				FileUtils.createIntermediateFolders(String.valueOf(finalFile));

				BufferedOutputStream writer = new BufferedOutputStream(
						new FileOutputStream(finalFile.toString(), false));

				// copy
				try {
					FileUtils.writeFile(reader, writer);
				} catch (IOException e) {
					throw e;
				} finally {
					// close all
					writer.close();
					reader.close();
				}
			}
			copyResourcesMojo.getLog().info(
					files.size() + " files in outputDirectory.");
		}
	}

	/**
	 * <p>
	 * return a file path list by applying the inclusion filters and exclusion
	 * configured
	 * </p>
	 * 
	 * @param copyResourcesMojo
	 *            Implementation of mojo with goal 'copy'
	 * @param resource
	 *            Resource to process
	 * @param files
	 *            All files to proccess with include and exclude
	 * @return Files set
	 */
	private static Set<String> processIncludeExclude(
			CopyResourcesMojo copyResourcesMojo, Resource resource,
			Set<String> files) {
		Set<String> retval = new HashSet<String>(files);
		// process with include parameters
		retval = IncludeService.process(copyResourcesMojo,
				resource.getIncludes(), files);
		copyResourcesMojo.getLog().debug(
				"files after include processing :" + files);
		// process with exclude parameters
		retval = ExcludeService.process(copyResourcesMojo,
				resource.getExcludes(), retval);
		copyResourcesMojo.getLog().debug(
				"files after exclude processing :" + files);
		return retval;
	}

	/**
	 * <p>
	 * returns the absolute path to the final based file output folder where the
	 * resource is configured
	 * </p>
	 * 
	 * @param file
	 *            Relative file from remote resource
	 * @param basePath
	 *            Absolute path to outputdirectory
	 * @param destinationFolder
	 *            Relative sub path from output directory
	 * @param isFlatten
	 * @return absolute path file in output directory
	 */
	private static StringBuilder buildAbsoluteFinalFile(String file,
			String basePath, File destinationFolder, boolean isFlatten) {
		StringBuilder retval = new StringBuilder(
				destinationFolder.getAbsolutePath());
		PathUtils.addEndingSlashIfNeeded(retval);
		if (!isFlatten) {
			// delete output directory from basePath
			String relativePath = file.replace(basePath, "");
			retval.append(relativePath);
		} else {
			// skip location file
			String relativePath = new File(file).getName();
			retval.append(relativePath);
		}
		return retval;
	}

	/**
	 * <p>
	 * returns the list of files present in a folder
	 * </p>
	 * 
	 * @param sourceFolder
	 *            the source folder to search content
	 * @return file set
	 * 
	 */
	public static Set<String> findFiles(File sourceFolder) {
		return findFiles(sourceFolder, sourceFolder);
	}

	/**
	 * <p>
	 * returns the list of files found in a folder. Does not return hidden files
	 * , unreadable , folder ' .git '
	 * </p>
	 * 
	 * @param sourceFolder
	 *            the source folder to search content
	 * @param baseFile
	 *            sub directory from source folder
	 * @return file set
	 */
	private static Set<String> findFiles(File sourceFolder, File baseFile) {
		Set<String> retval = new HashSet<String>();
		if (!sourceFolder.exists() || sourceFolder.isHidden()
				|| !sourceFolder.isDirectory() || !sourceFolder.canRead()
				|| isGitMetaDataFolder(sourceFolder)) {
			return retval;
		}
		for (File file : sourceFolder.listFiles()) {
			if (file.isDirectory()) {
				retval.addAll(findFiles(file, sourceFolder));
			} else {
				retval.add(file.getAbsolutePath());
			}
		}
		return retval;
	}

	/**
	 * <p>
	 * check if folder is '.git' folder
	 * </p>
	 * 
	 * @param file
	 *            file to test
	 * @return if git is a meta data folder
	 * 
	 */
	private static boolean isGitMetaDataFolder(File file) {
		return Constants.EXTENSION_GIT.equals(file.getName());
	}
}
