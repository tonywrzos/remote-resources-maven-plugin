package com.github.keub.maven.plugin.exception;

/**
 * <p>
 * Exception possibly raised during operations on a resource, if the defined
 * source does not exist or is malformed
 * </p>
 *
 */
public class InvalidSourceException extends Exception {

	private static final long serialVersionUID = 83300648057703931L;

	public InvalidSourceException() {
		super();
	}

	public InvalidSourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidSourceException(String message) {
		super(message);
	}

	public InvalidSourceException(Throwable cause) {
		super(cause);
	}

}
