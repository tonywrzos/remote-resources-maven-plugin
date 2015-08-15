package com.github.keub.maven.plugin.service;

import org.junit.Test;

public class FtpServiceTest {
	/**
	 * Tele2 provides ftp://speedtest.tele2.net , you can log in as anonymous
	 * and upload anything to test your upload speed. For download testing they
	 * provide fixed size files, you can choose which fits best to your test.
	 * 
	 * http://speedtest.tele2.net/
	 */
	private final String FTP_PUBLIC_HOST = "speedtest.tele2.net";
	private final String FTP_PUBLIC_USER = "anonymous";
	private final String FTP_PUBLIC_PASSWORD = null;
	private final String FTP_PUBLIC_REMOTE_FILE_NAME = "512KB.zip";
	private final String FTP_PUBLIC_LOCAL_FILE_NAME = "c:/tmp/test.zip";

	@Test
	public void test() throws Exception {
		FtpService.downloadFile(FTP_PUBLIC_HOST, FTP_PUBLIC_USER,
				FTP_PUBLIC_PASSWORD, FTP_PUBLIC_REMOTE_FILE_NAME,
				FTP_PUBLIC_LOCAL_FILE_NAME);
	}
}
