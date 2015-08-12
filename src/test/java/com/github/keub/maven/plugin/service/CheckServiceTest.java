package com.github.keub.maven.plugin.service;

import java.io.File;

import org.junit.Test;

import com.github.keub.maven.plugin.exception.InvalidOutputDirectoryException;
import com.github.keub.maven.plugin.service.CheckService;

public class CheckServiceTest {

	private final static File validDirectory = new File("src/test/resources/");
	private final static File fakeDirectory = new File("src/test/fake/");

	@Test
	public void should_check_valid_folder() throws InvalidOutputDirectoryException {
		CheckService.isValidOutputDirectory(validDirectory);
	}

	@Test(expected = InvalidOutputDirectoryException.class)
	public void should_check_invalid_folder() throws InvalidOutputDirectoryException {
		CheckService.isValidOutputDirectory(fakeDirectory);
	}

}
