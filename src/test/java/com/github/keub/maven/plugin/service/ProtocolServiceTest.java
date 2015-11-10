package com.github.keub.maven.plugin.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.keub.maven.plugin.exception.InvalidSourceException;
import com.github.keub.maven.plugin.exception.ProtocolException;
import com.github.keub.maven.plugin.model.Resource;
import com.github.keub.maven.plugin.strategy.FtpStrategy;
import com.github.keub.maven.plugin.strategy.GitStrategy;
import com.github.keub.maven.plugin.strategy.ProtocolStrategy;

public class ProtocolServiceTest {

	@Test
	public void test_return_git_strategy_with_uri()
			throws InvalidSourceException, IOException, ProtocolException,
			URISyntaxException {

		Resource r1 = new Resource();
		Resource r2 = new Resource();
		Resource r3 = new Resource();
		r1.setUri(new URI("http://gitblit.si3si.int/test.git"));
		r2.setUri(new URI("https://gitblit.si3si.int/test.git"));
		r3.setUri(new URI("git://gitblit.si3si.int/test.git"));

		Set<Resource> resources = new HashSet<Resource>();
		resources.add(r1);
		resources.add(r2);
		resources.add(r3);

		for (Resource resource : resources) {
			ProtocolStrategy protocolStrategy = ProtocolService
					.getStrategy(resource);
			Assert.assertNotNull(protocolStrategy);
			Assert.assertEquals(protocolStrategy.getClass().getCanonicalName(),
					GitStrategy.class.getCanonicalName());
		}
	}

	@Test
	public void test_return_ftp_strategy_with_uri()
			throws InvalidSourceException, IOException, ProtocolException,
			URISyntaxException {

		Resource r1 = new Resource();
		r1.setUri(new URI("ftp://hostname/test.txt"));

		Set<Resource> resources = new HashSet<Resource>();
		resources.add(r1);

		for (Resource resource : resources) {
			ProtocolStrategy protocolStrategy = ProtocolService
					.getStrategy(resource);
			Assert.assertNotNull(protocolStrategy);
			Assert.assertEquals(protocolStrategy.getClass().getCanonicalName(),
					FtpStrategy.class.getCanonicalName());
		}
	}

}
