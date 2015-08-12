/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.keub.maven.plugin.exception;

/**
 * <p>
 * Exception possibly raised at GIT operations
 * </p>
 */
public class GitException extends Exception {

	public GitException(String message) {
		super(message);
	}

	public GitException(String message, Throwable cause) {
		super(message, cause);
	}

}
