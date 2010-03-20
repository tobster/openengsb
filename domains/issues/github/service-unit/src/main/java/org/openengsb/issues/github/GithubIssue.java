package org.openengsb.issues.github;

import java.util.List;

import lombok.Data;

import org.joda.time.DateTime;

@Data
public class GithubIssue {
	private long number;
	private long votes;
	private DateTime created_at;
	private String body;
	private String title;
	private DateTime updated_at;
	private DateTime closed_at;
	private String user;
	private List<String> labels;
	private String state;
}
