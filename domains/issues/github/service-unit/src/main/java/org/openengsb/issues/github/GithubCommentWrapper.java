package org.openengsb.issues.github;

import java.util.List;

import lombok.Data;

@Data
public class GithubCommentWrapper {
	private List<GithubComment> comments;
}
