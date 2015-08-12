package com.github.keub.maven.plugin.service;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.keub.maven.plugin.service.ExcludeService;
import com.github.keub.maven.plugin.service.FileService;
import com.github.keub.maven.plugin.service.IncludeService;

public class FileServiceTest {

	private final static File sandboxResourcesDirectory = new File("src/test/resources/sandbox");
	private final static Set<String> sandboxFiles = new HashSet<String>();
	private final static Set<String> includes = new HashSet<String>();
	private final static Set<String> excludes = new HashSet<String>();
	static {

		sandboxFiles.add("src/test/resources/sandbox/subsandbox/bar.txt");
		sandboxFiles.add("src/test/resources/sandbox/subsandbox/foo.txt");
		sandboxFiles.add("src/test/resources/sandbox/foo.txt");

		includes.add("**/foo.txt");

		excludes.add("**/foo.txt");
	}

	@Test
	public void should_return_all_files() {
		Set<String> retval = FileService.findFiles(sandboxResourcesDirectory);

		Assert.assertNotNull(retval);
		Assert.assertEquals(3, retval.size());

		for (String file : retval) {
			boolean match = false;
			for (String sandboxFile : sandboxFiles) {
				File actualFile = new File(file);
				File expectedFile = new File(sandboxFile);
				if (actualFile.getAbsolutePath().equals(expectedFile.getAbsolutePath())) {
					match = true;
					break;
				}
			}
			// check if exepcted file has match
			Assert.assertTrue(match);
		}
	}

	@Test
	public void should_returns_only_included_files() {
		Set<String> retval = FileService.findFiles(sandboxResourcesDirectory);
		retval = IncludeService.process(includes, retval);

		Assert.assertNotNull(retval);
		Assert.assertEquals(2, retval.size());

		for (String file : retval) {
			boolean match = false;
			for (String sandboxFile : sandboxFiles) {
				File actualFile = new File(file);
				File expectedFile = new File(sandboxFile);
				if (actualFile.getAbsolutePath().equals(expectedFile.getAbsolutePath())) {
					match = true;
					break;
				}
			}
			// check if exepected file has match
			Assert.assertTrue(match);
		}
	}

	@Test
	public void should_returns_only_excluded_files() {
		Set<String> retval = FileService.findFiles(sandboxResourcesDirectory);
		retval = ExcludeService.process(excludes, retval);

		Assert.assertNotNull(retval);
		Assert.assertEquals(1, retval.size());

		for (String file : retval) {
			boolean match = false;
			for (String sandboxFile : sandboxFiles) {
				File actualFile = new File(file);
				File expectedFile = new File(sandboxFile);
				if (actualFile.getAbsolutePath().equals(expectedFile.getAbsolutePath())) {
					match = true;
					break;
				}
			}
			// check if exepected file has match
			Assert.assertTrue(match);
		}
	}
}
