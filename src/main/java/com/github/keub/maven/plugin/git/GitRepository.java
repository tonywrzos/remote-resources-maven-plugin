package com.github.keub.maven.plugin.git;

import java.io.File;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.TransportCommand;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.github.keub.maven.plugin.exception.GitException;
import com.github.keub.maven.plugin.utils.GitHelper;
import com.github.keub.maven.plugin.utils.SSLHelper;

/**
 * Created by pseillier on 06/05/2015.
 */
public class GitRepository {

	protected String url;
	protected String username;
	protected String password;
	protected boolean withCredentials;
	protected boolean certificateValidation = true;
	protected boolean hostnameValidation = true;
	protected File localDirectoryPath;

	protected final void configureSSLValidation() {
		// if the certificate is not valide then we have to disable it to
		// prevent a validation error
		if (!certificateValidation) {
			SSLHelper.disableCertificatesValidation();
		}
		// if the hostname of the git server is not the same inside the
		// certificate
		// we have to disable the verification to prevent an error
		if (!hostnameValidation) {
			SSLHelper.disableHostnameVerifier();
		}
	}

	protected final void close(Git repo) {
		// Note: the call() returns an opened repository already which needs to
		// be closed to avoid file handle leaks!
		if (repo != null) {
			repo.close();
		}
	}

	protected final void configureCredentials(TransportCommand command) {
		if (withCredentials) {
			command.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
					username, password));
		}
	}

	public GitRepository disableCertificateValidation() {
		certificateValidation = false;
		return this;
	}

	public GitRepository disableHostnameVerification() {
		hostnameValidation = false;
		return this;
	}

	public GitRepository credentials(String username, String password) {
		this.username = username;
		this.password = password;
		this.withCredentials = (username != null && password != null);
		return this;
	}

	public GitRepository url(String url) {
		this.url = url;
		return this;
	}

	public GitRepository localPath(File localPath) {
		this.localDirectoryPath = localPath;
		return this;
	}

	public GitRepository cloneRepository() throws GitException {
		Git result = null;
		try {
			String repositoryName = GitHelper.extractRepositoryNameFromUrl(url);
			File localPath = new File(localDirectoryPath.getAbsolutePath()
					.concat(File.separator).concat(repositoryName));
			configureSSLValidation();
			CloneCommand cc = Git.cloneRepository();
			configureCredentials(cc);
			result = cc.setURI(url).setDirectory(localPath).call();
		} catch (Exception ex) {
			throw new GitException("Cannot clone remote repository " + url
					+ " into " + localDirectoryPath, ex);
		} finally {
			close(result);
		}
		return this;
	}

	public GitRepository fetch() throws GitException {
		Git git = null;
		try {
			configureSSLValidation();
			git = Git.open(localDirectoryPath);
			FetchCommand fc = git.fetch();
			configureCredentials(fc);
			fc.call();
		} catch (Exception e) {
			throw new GitException("Cannot open access local repository "
					+ localDirectoryPath, e);
		} finally {
			close(git);
		}
		return this;
	}

	public GitRepository hardReset(String branchName) throws GitException {
		Git git = null;
		try {
			configureSSLValidation();
			git = Git.open(localDirectoryPath);
			git.reset().setMode(ResetCommand.ResetType.HARD).setRef(branchName)
					.call();
		} catch (Exception e) {
			throw new GitException("Cannot open access local repository "
					+ localDirectoryPath, e);
		} finally {
			close(git);
		}
		return this;
	}

	public String currentBranch() throws GitException {
		Git git = null;
		try {
			git = Git.open(localDirectoryPath);
			return git.getRepository().getFullBranch();
		} catch (Exception e) {
			throw new GitException("Cannot open access local repository "
					+ localDirectoryPath, e);
		} finally {
			close(git);
		}
	}

	public GitRepository selectBranch(String name) throws GitException {
		Git git = null;
		try {
			git = Git.open(localDirectoryPath);
			git.checkout().setName(name).call();
		} catch (Exception e) {
			throw new GitException("Cannot open access local repository "
					+ localDirectoryPath, e);
		} finally {
			close(git);
		}

		return this;

	}
}
