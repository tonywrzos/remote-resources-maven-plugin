package com.github.keub.maven.plugin.exception;

/**
 * <p>
 * Exception possibly raised during operations on a resource
 * </p>
 *
 */
public class ResourceExecutionException extends Exception {

	private static final long serialVersionUID = 83300648057703931L;

	public ResourceExecutionException() {
		super();
	}

	public ResourceExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceExecutionException(String message) {
		super(message);
	}

	public ResourceExecutionException(Throwable cause) {
		super(cause);
	}

}
