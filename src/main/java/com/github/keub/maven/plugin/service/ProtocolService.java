package com.github.keub.maven.plugin.service;

import com.github.keub.maven.plugin.exception.ProtocolException;
import com.github.keub.maven.plugin.model.Resource;
import com.github.keub.maven.plugin.strategy.GitStrategy;
import com.github.keub.maven.plugin.strategy.ProtocolStrategy;
import com.github.keub.maven.plugin.utils.Constants;

public class ProtocolService {

	/**
	 * procotols
	 */
	private final static String GIT_PROTOCOL = "git";
	private final static String FTP_PROTOCOL = "ftp";
	private final static String HTTP_PROTOCOL = "http";
	private final static String HTTPS_PROTOCOL = "https";

	/**
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param resource
	 * @return
	 * @throws ProtocolException
	 */
	public static ProtocolStrategy getStrategy(Resource resource) throws ProtocolException {
		if (resource == null) {
			throw new ProtocolException("Unable to find implementation with resource : " + resource);
		}
		// search by url
		ProtocolStrategy retval = findStrategyByUrl(resource);
		if (retval != null) {
			return retval;
		}
		throw new ProtocolException("This protocol has not yet been implemented : " + resource);
	}

	private static ProtocolStrategy findStrategyByUrl(Resource resource) {
		/**
		 * GIT
		 * 
		 * tests whether the resource is configured with a git project
		 * 
		 * - protocol url is git
		 * 
		 * - protocol url is http(s) and url ends with '.git'
		 */
		String protocol = resource.getUri().getScheme();
		if (protocol.equalsIgnoreCase(GIT_PROTOCOL)
				|| (protocol.equalsIgnoreCase(HTTP_PROTOCOL) || protocol.equalsIgnoreCase(HTTPS_PROTOCOL)
						&& resource.getUri().toString().endsWith(Constants.EXTENSION_GIT))) {
			return new GitStrategy();
		}
		return null;
	}
}
