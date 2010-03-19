package org.openengsb.issues.github;

import static org.junit.Assert.fail;

import javax.ws.rs.core.MediaType;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.junit.Test;

import com.google.gson.Gson;

public class GithubServiceWinkTest {

	@Test
	public void test() {
		RestClient restClient = new RestClient();
		Resource resource = restClient
				.resource("http://github.com/api/v2/json/issues/show/openengsb/openengsb/1");
		String result = resource.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		System.out.println(result);
		Gson gson = new Gson();
		GithubIssue issue = gson.fromJson(result, GithubIssue.class);
		System.out.println(issue.toString());
	}

	@Test
	public void testGetIssue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIssueComments() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIssues() {
		fail("Not yet implemented");
	}

}
