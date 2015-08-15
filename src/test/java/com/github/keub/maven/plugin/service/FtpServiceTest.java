package com.github.keub.maven.plugin.service;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.keub.maven.plugin.utils.FileUtils;

public class FtpServiceTest {
	/**
	 * Tele2 provides ftp://speedtest.tele2.net , you can log in as anonymous
	 * and upload anything to test your upload speed. For download testing they
	 * provide fixed size files, you can choose which fits best to your test.
	 * 
	 * http://speedtest.tele2.net/
	 */
	private final static String FTP_PUBLIC_HOST = "speedtest.tele2.net";
	private final static String FTP_PUBLIC_USER = "anonymous";
	private final static String FTP_PUBLIC_PASSWORD = null;
	private final static String FTP_PUBLIC_REMOTE_FILE_NAME = "512KB.zip";
	private final static String FTP_SANDBOX = "src/test/resources/ftp_sandbox/";
	private final static String FTP_PUBLIC_LOCAL_FILE_NAME = "result.zip";

	@BeforeClass
	public static void init() throws IOException {
		FileUtils.createIntermediateFolders(FTP_SANDBOX);
		File sandboxFile = new File(FTP_SANDBOX);
		Assert.assertTrue(sandboxFile.exists());
		org.apache.commons.io.FileUtils.cleanDirectory(sandboxFile);
	}

	@Test
	public void test() throws Exception {
		FtpService.downloadFile(FTP_PUBLIC_HOST, FTP_PUBLIC_USER,
				FTP_PUBLIC_PASSWORD, FTP_PUBLIC_REMOTE_FILE_NAME,
				FTP_SANDBOX.concat(FTP_PUBLIC_LOCAL_FILE_NAME));
		File sandboxFile = new File(FTP_SANDBOX);
		Assert.assertTrue(sandboxFile.exists());
		Assert.assertNotNull(sandboxFile.list());
		Assert.assertEquals(1, sandboxFile.list().length);
		Assert.assertEquals(FTP_PUBLIC_LOCAL_FILE_NAME, sandboxFile.list()[0]);

	}

	@BeforeClass
	public static void after() throws IOException {
		File sandboxFile = new File(FTP_SANDBOX);
		org.apache.commons.io.FileUtils.cleanDirectory(sandboxFile);
	}

}
