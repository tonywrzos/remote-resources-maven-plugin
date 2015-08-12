package com.github.keub.maven.plugin.exception;

/**
 * <p>
 * Exception thrown if the protocol to implement is not found
 * </p>
 * 
 */
public class ProtocolException extends Exception {
	private static final long serialVersionUID = 83300648057703931L;

	public ProtocolException() {
		super();
	}

	public ProtocolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProtocolException(String message) {
		super(message);
	}

	public ProtocolException(Throwable cause) {
		super(cause);
	}

}
