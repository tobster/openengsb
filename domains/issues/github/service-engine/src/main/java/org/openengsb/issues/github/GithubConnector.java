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

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openengsb.drools.IssuesDomain;
import org.openengsb.drools.model.Issue;

public class GithubConnector implements IssuesDomain {
    private final Log log = LogFactory.getLog(this.getClass());
    private GithubService githubService;
    private String repositoryUser;
    private String project;

    @Override
    public void addComment(String id, String comment) {
        githubService.addComment(repositoryUser, project, Long.valueOf(id), comment);
    }

    @Override
    public String createIssue(Issue issue) {
        GithubIssue githubIssue = new GithubIssue();
        githubIssue.setBody(createBody(issue));
        githubIssue.setTitle(issue.getSummary());
        githubIssue.setUser(issue.getOwner());
        GithubIssue result = githubService.createIssue(repositoryUser, project, githubIssue);
        return String.valueOf(result.getNumber());
    }

    @Override
    public void deleteIssue(String id) {
        try {
            this.githubService.changeState(this.repositoryUser, this.project, Long.valueOf(id),
                    GithubService.State.CLOSE);
        } catch (NumberFormatException e) {
            log.error("could not transform id >" + id + "<to long value", e);
        }
    }

    @Override
    public void updateIssue(String id, String comment, Map<String, Object> changes) {
        if (changes != null) {
            this.githubService.editIssue(this.repositoryUser, this.project, Long.valueOf(id), (String) changes
                    .get(Issue.fieldSUMMARY), createBody(changes));
        }
        if (comment != null) {
            this.githubService.addComment(this.repositoryUser, this.project, Long.valueOf(id), comment);
        }
    }

    private String createBody(Map<String, Object> changes) {
        if (changes == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        String value;
        if ((value = (String) changes.get(Issue.fieldDESCRIPTION)) != null) {
            result.append(value);
            result.append("\n");
        }
        appendField(changes, result, Issue.fieldSTATUS);
        appendField(changes, result, Issue.fieldPRIORITY);
        appendField(changes, result, Issue.fieldREPORTER);
        log.error(result.toString());
        return result.toString();
    }

    private String createBody(Issue issue) {
        StringBuilder result = new StringBuilder();
        String value;
        if ((value = (String) issue.getDescription()) != null) {
            result.append(value);
            result.append("\n");
        }
        appendField(result, Issue.fieldSTATUS, issue.getStatus());
        appendField(result, Issue.fieldPRIORITY, issue.getPriority());
        appendField(result, Issue.fieldREPORTER, issue.getReporter());
        log.error(result.toString());
        return result.toString();
    }

    private void appendField(Map<String, Object> changes, StringBuilder result, String field) {
        String txt = (String) changes.get(field);
        appendField(result, field, txt);
    }

    private void appendField(StringBuilder result, String field, String value) {
        if (value != null) {
            result.append("\n[");
            result.append(field);
            result.append(" -> ");
            result.append(value);
            result.append("]");
        }
    }

    public void setGithubService(final GithubService githubService) {
        this.githubService = githubService;
    }

    public String getRepositoryUser() {
        return repositoryUser;
    }

    public void setRepositoryUser(final String repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    public String getProject() {
        return project;
    }

    public void setProject(final String project) {
        this.project = project;
    }
}