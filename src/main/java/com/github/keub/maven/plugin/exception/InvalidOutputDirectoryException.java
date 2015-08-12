package com.github.keub.maven.plugin.exception;

/**
 * <p>
 * Exception possibly raised during operations on a resource, if the defined
 * output directorydoes not exist or is malformed
 * </p>
 *
 */
public class InvalidOutputDirectoryException extends Exception {

	private static final long serialVersionUID = 83300648057703931L;

	public InvalidOutputDirectoryException() {
		super();
	}

	public InvalidOutputDirectoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidOutputDirectoryException(String message) {
		super(message);
	}

	public InvalidOutputDirectoryException(Throwable cause) {
		super(cause);
	}

}
