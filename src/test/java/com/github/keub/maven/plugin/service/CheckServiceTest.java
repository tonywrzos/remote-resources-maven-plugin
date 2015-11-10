package com.github.keub.maven.plugin.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.github.keub.maven.plugin.exception.InvalidOutputDirectoryException;

public class CheckServiceTest {

	private final static File validDirectory = new File("src/test/resources/");
	private final static File fakeDirectory = new File("src/test/fake/");

	@Test
	public void should_check_valid_folder()
			throws InvalidOutputDirectoryException {
		CheckService.isValidOutputDirectory(validDirectory);
	}

	@Test
	public void should_check_invalid_folder()
			throws InvalidOutputDirectoryException, IOException {
		CheckService.isValidOutputDirectory(fakeDirectory);
		FileUtils.deleteDirectory(fakeDirectory);
	}

}
