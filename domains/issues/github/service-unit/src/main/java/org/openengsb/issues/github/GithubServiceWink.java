package org.openengsb.issues.github;

import java.util.List;

import org.apache.wink.client.RestClient;

public class GithubServiceWink implements GihubService {

	RestClient client = new RestClient();

	@Override
	public GithubIssue getIssue(String project, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GithubComment> getIssueComments(String project, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GithubIssue> getIssues(String project, String state) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
