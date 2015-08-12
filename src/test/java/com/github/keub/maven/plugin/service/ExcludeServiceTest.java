package com.github.keub.maven.plugin.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class ExcludeServiceTest {

	@Test
	public void process_with_empty_exclude_set_do_nothing() {
		Set<String> excludes = null;
		Set<String> files = new HashSet<String>();
		String file = "foo.txt";
		files.add(file);

		Set<String> retval = ExcludeService.process(excludes, files);
		Assert.assertNotNull(retval);
		Assert.assertEquals(files.size(), retval.size());
		Assert.assertTrue(retval.contains(file));
	}

	@Test
	public void process_with_empty_file_set_do_nothing() {
		Set<String> excludes = null;
		Set<String> files = new HashSet<String>();

		Set<String> retval = ExcludeService.process(excludes, files);
		Assert.assertNotNull(retval);
		Assert.assertEquals(files.size(), retval.size());
	}

	@Test
	public void process_with_excludes_and_empty_files_set() {
		Set<String> excludes = new HashSet<String>();
		excludes.add("**/foo.txt");
		Set<String> files = new HashSet<String>();

		Set<String> retval = ExcludeService.process(excludes, files);
		Assert.assertNotNull(retval);
		Assert.assertEquals(files.size(), retval.size());
	}

	@Test
	public void process_with_excludes_and_files() {
		Set<String> excludes = new HashSet<String>();
		excludes.add("**/foo.txt");
		Set<String> files = new HashSet<String>();
		String file1 = "foo.txt";
		files.add(file1);
		String file2 = "toto/foo.txt";
		files.add(file2);
		String file3 = "bar.txt";
		files.add(file3);

		Set<String> retval = ExcludeService.process(excludes, files);
		Assert.assertNotNull(retval);
		Assert.assertEquals(1, retval.size());
		Assert.assertTrue(retval.contains(file3));
	}
}
