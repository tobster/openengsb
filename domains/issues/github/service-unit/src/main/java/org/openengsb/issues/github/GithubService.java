package org.openengsb.issues.github;

import java.util.List;

public interface GithubService {

	List<GithubIssue> getIssues(String user, String project, String state);

	GithubIssue getIssue(String user, String project, long id);
	
	List<GithubComment> getIssueComments(String user, String project, long id);
	
}
