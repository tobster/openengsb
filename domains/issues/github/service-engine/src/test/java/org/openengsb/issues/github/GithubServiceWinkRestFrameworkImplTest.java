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

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class GithubServiceWinkRestFrameworkImplTest {

    GithubService githubService = new GithubServiceWinkRestFrameworkImpl();

    @Test
    public void testGetIssue() {
        GithubIssue issue = this.githubService.getIssue("openengsb", "openengsb", 1);
        assertNotNull(issue);
        assertEquals("eyeball", issue.getUser());
    }

    @Test
    public void testGetIssueComments() {
        List<GithubComment> issueComments = this.githubService.getIssueComments("openengsb", "openengsb", 5);
        assertNotNull(issueComments);
        assertEquals(2, issueComments.size());
        assertEquals("eyeball", issueComments.get(0).getUser());
    }

    @Test
    public void testGetIssues() {
        List<GithubIssue> githubIssues = this.githubService.getIssues("openengsb", "openengsb", "open");
        assertNotNull(githubIssues);
        assertTrue(githubIssues.size() > 5);
        assertNotNull(githubIssues.get(0).getCreated_at());
    }

    @Ignore
    @Test
    public void testCreateIssue() {
        GithubIssue issue = new GithubIssue();
        issue.setTitle("test-title");
        issue.setBody("test-body");
        GithubIssue githubIssuesCreated = this.githubService.createIssue("tobster", "openengsb", issue);
        assertNotNull(githubIssuesCreated);
        assertEquals("test-title", githubIssuesCreated.getTitle());
        assertEquals("test-body", githubIssuesCreated.getBody());
        GithubIssue githubIssueStored = this.githubService.getIssue("tobster", "openengsb", githubIssuesCreated
                .getNumber());
        assertNotNull(githubIssueStored);
    }

}
