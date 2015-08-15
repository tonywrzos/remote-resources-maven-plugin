package com.github.keub.maven.plugin.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.github.keub.maven.plugin.exception.ResourceExecutionException;
import com.github.keub.maven.plugin.model.Resource;
import com.github.keub.maven.plugin.resources.CopyResourcesMojo;
import com.github.keub.maven.plugin.utils.FileUtils;
import com.github.keub.maven.plugin.utils.PathUtils;

public class FtpService {

	/**
	 * <p>
	 * put in localDestination the remote file on remote hostname
	 * </p>
	 * 
	 * @param hostname
	 * @param username
	 * @param password
	 * @param remoteSource
	 * @param localDestination
	 * @throws Exception
	 */
	public static void downloadFile(String hostname, String username,
			String password, String remoteSource, String localDestination)
			throws Exception {
		FTPClient ftp = new FTPClient();
		ftp.connect(hostname);
		ftp.login(username, password);
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
		FileOutputStream fos = new FileOutputStream(localDestination);
		ftp.retrieveFile(remoteSource, fos);
		ftp.logout();
		ftp.disconnect();
	}

	/**
	 * <p>
	 * recovery of a deposit of clone ftp remote folder locally
	 * </p>
	 * 
	 * @param resource
	 *            a resource
	 * @param copyResourcesMojo
	 *            mojo attach to goal copy
	 * @param workspacePlugin
	 *            file as workspace where plugin clone git repository
	 * @return source folder
	 * @throws ResourceExecutionException
	 * 
	 */
	public static String getSourceFolder(Resource resource,
			CopyResourcesMojo copyResourcesMojo, File workspacePlugin)
			throws ResourceExecutionException {

		String hostname = resource.getUri().getHost();
		String username = resource.getUsername();
		String password = resource.getPassword();
		String remoteSource = resource.getUri().getPath();
		StringBuilder retval = new StringBuilder(
				workspacePlugin.getAbsolutePath());
		PathUtils.addEndingSlashIfNeeded(retval);
		retval.append(Calendar.getInstance().getTimeInMillis());
		PathUtils.addEndingSlashIfNeeded(retval);

		try {
			FileUtils.createIntermediateFolders(retval.toString());
			copyResourcesMojo.getLog().debug(
					"IntermediateFolder created : " + retval.toString());
		} catch (FileNotFoundException e) {
			throw new ResourceExecutionException(
					"Error when clone ftp resource : " + resource, e);
		}
		String localDestination = retval.toString().concat(remoteSource);
		try {

			downloadFile(hostname, username, password, remoteSource,
					localDestination);
		} catch (Exception e) {
			throw new ResourceExecutionException("Error when clone into '"
					+ localDestination + "' ftp resource : " + resource, e);
		}

		return String.valueOf(retval);

	}
}
