package de.uhd.ifi.se.decision.management.jira.extraction.gitclient;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.uhd.ifi.se.decision.management.jira.extraction.GitClient;
import de.uhd.ifi.se.decision.management.jira.extraction.impl.GitClientImpl;

public class TestCloseAndDeleteRepository extends TestSetUpGit {

	@Test
	public void testCloseGitNull() {
		GitClient gitClient = new GitClientImpl();
		gitClient.close();
		assertNotNull(gitClient);
	}

	@Test
	public void testCloseGitExisting() {
		gitClient.close();
		assertNotNull(gitClient);
	}

	@Test
	public void testDeleteGitNull() {
		GitClient gitClient = new GitClientImpl();
		gitClient.deleteRepository();
		assertNotNull(gitClient);
	}
}