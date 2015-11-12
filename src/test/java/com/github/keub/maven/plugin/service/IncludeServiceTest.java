package com.github.keub.maven.plugin.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.keub.maven.plugin.resources.CopyResourcesMojo;

public class IncludeServiceTest {

	@Test
	public void process_with_empty_include_set_do_nothing() {
		Set<String> includes = null;
		Set<String> files = new HashSet<String>();
		String file = "foo.txt";
		files.add(file);

		Set<String> retval = IncludeService.process(new CopyResourcesMojo(),
				includes, files);
		Assert.assertNotNull(retval);
		Assert.assertEquals(files.size(), retval.size());
		Assert.assertTrue(retval.contains(file));
	}

	@Test
	public void process_with_empty_file_set_do_nothing() {
		Set<String> includes = null;
		Set<String> files = new HashSet<String>();

		Set<String> retval = IncludeService.process(new CopyResourcesMojo(),
				includes, files);
		Assert.assertNotNull(retval);
		Assert.assertEquals(files.size(), retval.size());
	}

	@Test
	public void process_with_includes_and_empty_files_set() {
		Set<String> includes = new HashSet<String>();
		includes.add("**/foo.txt");
		Set<String> files = new HashSet<String>();

		Set<String> retval = IncludeService.process(new CopyResourcesMojo(),
				includes, files);
		Assert.assertNotNull(retval);
		Assert.assertEquals(files.size(), retval.size());
	}

	@Test
	public void process_with_includes_and_files() {
		Set<String> includes = new HashSet<String>();
		includes.add("**/foo.txt");
		Set<String> files = new HashSet<String>();
		String file1 = "foo.txt";
		files.add(file1);
		String file2 = "toto/foo.txt";
		files.add(file2);
		String file3 = "bar.txt";
		files.add(file3);

		Set<String> retval = IncludeService.process(new CopyResourcesMojo(),
				includes, files);
		Assert.assertNotNull(retval);
		Assert.assertEquals(2, retval.size());
		Assert.assertTrue(retval.contains(file1));
		Assert.assertTrue(retval.contains(file2));
	}
}
