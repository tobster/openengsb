package org.openengsb.issues.github;

import java.util.List;

public interface GihubService {

	List<GithubIssue> getIssues(String project, String state);

	GithubIssue getIssue(String project, long id);
	
	List<GithubComment> getIssueComments(String project, long id);
	
}
