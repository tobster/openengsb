/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openengsb.issues.github;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import lombok.Setter;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.joda.time.DateTime;
import org.openengsb.issues.github.util.DateTimeTypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GithubServiceWinkRestFrameworkImpl implements GithubService {

    private RestClient restClient = new RestClient();
    private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;
    @Setter
    private String user;
    @Setter
    private String token;

    private static final String SHOW = "show";
    private static final String ISSUES = "issues";
    private static final String LIST = "list";
    private static final String OPEN = "open";
    private static final String BASEURL = "http://github.com/api/v2/json/";
    private static final String COMMENTS = "comments";
    private static final String LONGIN = "login";
    private static final String TOKEN = "token";

    public GithubServiceWinkRestFrameworkImpl() {
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeTypeConverter());
        gson = gsonBuilder.create();
    }

    private <T> T requestandmarshal(URI uri, Class<T> clazz) {
        Resource resource = this.restClient.resource(uri).accept(MediaType.APPLICATION_JSON);
        String result = resource.get(String.class);
        return this.gson.fromJson(result, clazz);
    }

    @Override
    public GithubIssue getIssue(String repositoryUser, String project, long id) {
        URI uri = UriBuilder.fromUri(BASEURL).path(ISSUES).path(SHOW).path(repositoryUser).path(project).path(
                String.valueOf(id)).queryParam(LONGIN, this.user).queryParam(TOKEN, this.token).build();
        return requestandmarshal(uri, GithubIssueWrapper.class).getIssue();
    }

    @Override
    public List<GithubComment> getIssueComments(String repositoryUser, String project, long id) {
        URI uri = UriBuilder.fromUri(BASEURL).path(ISSUES).path(COMMENTS).path(repositoryUser).path(project).path(
                String.valueOf(id)).build();
        return requestandmarshal(uri, GithubCommentWrapper.class).getComments();
    }

    @Override
    public List<GithubIssue> getIssues(String repositoryUser, String project, String state) {
        URI uri = UriBuilder.fromUri(BASEURL).path(ISSUES).path(LIST).path(repositoryUser).path(project).path(state)
                .build();
        return requestandmarshal(uri, GithubIssuesWrapper.class).getIssues();
    }

    @Override
    public GithubIssue createIssue(String repositoryUser, String project, GithubIssue issue) {
        URI uri = UriBuilder.fromUri(BASEURL).path(ISSUES).path(OPEN).path(repositoryUser).path(project).queryParam(
                LONGIN, this.user).queryParam(TOKEN, this.token).queryParam("title", issue.getTitle()).queryParam(
                "body", issue.getBody()).build();
        Resource resource = this.restClient.resource(uri).accept(MediaType.APPLICATION_JSON);
        String result = resource.post(String.class, null);
        System.out.println(result);
        return this.gson.fromJson(result, GithubIssueWrapper.class).getIssue();
    }

}
