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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class GithubServiceWinkRestFrameworkImplUT {
    //enter your own github username here
    private static final String REPOSITORYUSER = "xxx";
    //enter your own token here
    private static final String REPOSITROYTOKEN = "xxx";
    private static final String PROJECT = "openengsb";
    GithubServiceWinkRestFrameworkImpl githubService = new GithubServiceWinkRestFrameworkImpl();

    @Before
    public void init() {
        githubService.setUser(REPOSITORYUSER);

        githubService.setToken(REPOSITROYTOKEN);
    }

    @Test
    public void testGetIssue() {
        GithubIssue issue = this.githubService.getIssue(PROJECT, PROJECT, 1);
        assertNotNull(issue);
        assertEquals("anpieber", issue.getUser());
    }

    @Test
    public void testGetIssueComments() {
        List<GithubComment> issueComments = this.githubService.getIssueComments(PROJECT, PROJECT, 5);
        assertNotNull(issueComments);
        assertEquals(3, issueComments.size());
        assertEquals("anpieber", issueComments.get(0).getUser());
    }

    @Test
    public void testGetIssues() {
        List<GithubIssue> githubIssues = this.githubService.getIssues(PROJECT, PROJECT, "open");
        assertNotNull(githubIssues);
        assertTrue(githubIssues.size() > 5);
        assertNotNull(githubIssues.get(0).getCreated_at());
    }

    @Test
    public void testCreateIssue() {
        GithubIssue issue = new GithubIssue();
        issue.setTitle("test-title");
        issue.setBody("test-body");
        GithubIssue githubIssuesCreated = this.githubService.createIssue(REPOSITORYUSER, PROJECT, issue);
        assertNotNull(githubIssuesCreated);
        assertEquals("test-title", githubIssuesCreated.getTitle());
        assertEquals("test-body", githubIssuesCreated.getBody());
        GithubIssue githubIssueStored = this.githubService.getIssue(REPOSITORYUSER, PROJECT, githubIssuesCreated
                .getNumber());
        assertNotNull(githubIssueStored);
    }

    @Test
    public void testEditIssue() {
        GithubIssue oldIssue = githubService.getIssue(REPOSITORYUSER, PROJECT, 1);
        assertEquals("oldbody", oldIssue.getBody());
        assertEquals("oldtitle", oldIssue.getTitle());

        githubService.editIssue(REPOSITORYUSER, PROJECT, 1, "changedtitle", "changedbody");

        GithubIssue issue = githubService.getIssue(REPOSITORYUSER, PROJECT, 1);
        assertNotNull(issue);
        assertEquals("changedbody", issue.getBody());
        assertEquals("changedtitle", issue.getTitle());
        // cleanup
        githubService.editIssue(REPOSITORYUSER, PROJECT, 1, "oldtitle", "oldbody");
    }

    @Test
    public void testAddCommentIssue() {
        List<GithubComment> issueComments = githubService.getIssueComments(REPOSITORYUSER, PROJECT, 1);
        assertNotNull(issueComments);
        long time = new Date().getTime();
        githubService.addComment(REPOSITORYUSER, PROJECT, 1, "testcomment" + time);
        List<GithubComment> issueCommentsafter = githubService.getIssueComments(REPOSITORYUSER, PROJECT, 1);
        assertNotNull(issueCommentsafter);
        assertEquals(issueComments.size() + 1, issueCommentsafter.size());
        assertEquals("testcomment" + time, issueCommentsafter.get(issueCommentsafter.size() - 1).getBody());
    }

    @Test
    public void testChangeState() {
        GithubIssue issue = githubService.getIssue(REPOSITORYUSER, PROJECT, 1);
        assertNotNull(issue);
        assertEquals("closed", issue.getState());
        githubService.changeState(REPOSITORYUSER, PROJECT, 1, "OPEN");
        issue = githubService.getIssue(REPOSITORYUSER, PROJECT, 1);
        assertNotNull(issue);
        assertEquals("open", issue.getState());
        githubService.changeState(REPOSITORYUSER, PROJECT, 1, "CLOSE");
        issue = githubService.getIssue(REPOSITORYUSER, PROJECT, 1);
        assertNotNull(issue);
        assertEquals("closed", issue.getState());
    }
}
