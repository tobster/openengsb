package org.openengsb.issues.github;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.joda.time.DateTime;
import org.openengsb.issues.github.util.DateTimeTypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GithubServiceWink implements GithubService {

	private RestClient restClient = new RestClient();
	private GsonBuilder gsonBuilder = new GsonBuilder();
	private Gson gson;

	private static final String SHOW = "show";
	private static final String ISSUES = "issues";
	private static final String BASEURL = "http://github.com/api/v2/json/";
	private static final String COMMENTS = "comments";
	private static final String LIST = "list";

	public GithubServiceWink() {
		gsonBuilder.registerTypeAdapter(DateTime.class,
				new DateTimeTypeConverter());
		gson = gsonBuilder.create();
	}

	@Override
	public GithubIssue getIssue(String user, String project, long id) {
		URI uri = UriBuilder.fromUri(BASEURL).path(ISSUES).path(SHOW)
				.path(user).path(project).path(String.valueOf(id)).build();

		Resource resource = this.restClient.resource(uri);

		String result = resource.accept(MediaType.APPLICATION_JSON).get(
				String.class);

		gsonBuilder.registerTypeAdapter(DateTime.class,
				new DateTimeTypeConverter());

		return this.gson.fromJson(result, GithubIssueWrapper.class).getIssue();
	}

	@Override
	public List<GithubComment> getIssueComments(String user, String project,
			long id) {
		URI uri = UriBuilder.fromUri(BASEURL).path(ISSUES).path(COMMENTS).path(
				user).path(project).path(String.valueOf(id)).build();

		Resource resource = this.restClient.resource(uri);

		String result = resource.accept(MediaType.APPLICATION_JSON).get(
				String.class);

		return this.gson.fromJson(result, GithubCommentWrapper.class)
				.getComments();
	}

	@Override
	public List<GithubIssue> getIssues(String user, String project, String state) {
		URI uri = UriBuilder.fromUri(BASEURL).path(ISSUES).path(LIST).path(
				user).path(project).path(state).build();

		Resource resource = this.restClient.resource(uri);

		String result = resource.accept(MediaType.APPLICATION_JSON).get(
				String.class);

		return this.gson.fromJson(result, GithubIssuesWrapper.class)
				.getIssues();
	}

}
