package org.openengsb.issues.github;

import lombok.Data;

import org.joda.time.DateTime;

@Data
public class GithubComment {
	private DateTime created_at;
	private String body;
	private DateTime updated_at;
	private long id;
	private String user;
}
