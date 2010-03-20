package org.openengsb.issues.github;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

public class GithubServiceWinkTest {

	GithubService githubService = new GithubServiceWink();

	@Test
	public void testGetIssue() {
		GithubIssue issue = this.githubService.getIssue("openengsb",
				"openengsb", 1);
		assertNotNull(issue);
		assertEquals("eyeball", issue.getUser());
	}

	@Test
	public void testGetIssueComments() {
		List<GithubComment> issueComments = this.githubService
				.getIssueComments("openengsb", "openengsb", 5);
		assertNotNull(issueComments);
		assertEquals(2, issueComments.size());
		assertEquals("eyeball", issueComments.get(0).getUser());
	}

	@Test
	public void testGetIssues() {
		List<GithubIssue> githubIssues = this.githubService.getIssues(
				"openengsb", "openengsb", "open");
		assertNotNull(githubIssues);
		assertTrue(githubIssues.size() > 5);
		assertNotNull(githubIssues.get(0).getCreated_at());
	}

}
