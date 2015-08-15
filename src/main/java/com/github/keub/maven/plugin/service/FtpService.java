package com.github.keub.maven.plugin.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpService {

	public static void downloadFile(String host, String user, String pwd,
			String remoteFilePath, String localFilePath) throws Exception {
		FTPClient ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(
				new PrintWriter(System.out)));
		int reply;
		ftp.connect(host);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new Exception("Exception in connecting to FTP Server");
		}
		ftp.login(user, pwd);
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
		try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
			ftp.retrieveFile(remoteFilePath, fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (ftp.isConnected()) {
			try {
				ftp.logout();
				ftp.disconnect();
			} catch (IOException f) {
				// do nothing as file is already downloaded from FTP server
			}
		}
	}

}
