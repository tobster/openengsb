package org.openengsb.issues.github;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openengsb.drools.model.Issue;
import org.springframework.test.annotation.ExpectedException;

public class GithubConnectorTest {

    private GithubService githubService;

    private GithubConnector githubConnector;

    @Before
    public void init() {
        githubService = mock(GithubService.class);
        githubConnector = new GithubConnector();
        githubConnector.setGithubService(githubService);
        githubConnector.setProject("testProject");
        githubConnector.setRepositoryUser("testUser");
    }

    @Test
    public void testAddComment() {
        String comment = "testcomment";
        String id = "123";
        githubConnector.addComment(id, comment);
        verify(githubService, times(1)).addComment("testUser", "testProject", 123, comment);
    }

    @Test
    public void testCreateIssue() {
        GithubIssue githubIssue = new GithubIssue();
        githubIssue.setNumber(0);
        githubIssue.setTitle("title");
        githubIssue.setBody("body\n\n[STATUS -> NEW]\n[PRIORITY -> HIGH]\n[REPORTER -> testreporter]");
        githubIssue.setUser("user");
        when(githubService.createIssue("testUser", "testProject", githubIssue)).thenReturn(githubIssue);

        Issue issue = new Issue();
        issue.setSummary("title");
        issue.setDescription("body");
        issue.setPriority(Issue.priorityHIGH);
        issue.setOwner("user");
        issue.setReporter("testreporter");
        issue.setStatus(Issue.statusNEW);
        String id = githubConnector.createIssue(issue);
        verify(githubService, times(1)).createIssue("testUser", "testProject", githubIssue);
        assertNotNull(id);
        assertEquals("0", id);
    }

    @Test
    public void testDeleteIssue() {
        githubConnector.deleteIssue("1");
        verify(githubService, times(1)).changeState("testUser", "testProject", 1, GithubService.STATESCLOSE);
    }

    @Test
    @ExpectedException(NumberFormatException.class)
    public void testDeleteIssueNumberFormatException() {
        githubConnector.deleteIssue("1a");
    }

    @Test
    public void testUpdateIssue() {
        Map<String, Object> changes = new HashMap<String, Object>();
        changes.put(Issue.fieldSUMMARY, "testtitle");
        changes.put(Issue.fieldDESCRIPTION, "testdescr");
        changes.put(Issue.fieldOWNER, "testowner");
        changes.put(Issue.fieldPRIORITY, Issue.priorityNONE);
        changes.put(Issue.fieldREPORTER, "testreporter");
        changes.put(Issue.fieldSTATUS, Issue.statusASSIGNED);
        githubConnector.updateIssue("1", "test commment", changes);
        verify(githubService, times(1)).editIssue("testUser", "testProject", 1, "testtitle",
                "testdescr\n\n[STATUS -> ASSIGNED]\n[PRIORITY -> NONE]\n[REPORTER -> testreporter]");
        verify(githubService, times(1)).addComment("testUser", "testProject", 1, "test commment");
    }

    @Test
    public void testUpdateIssueEmptyFields() {
        Map<String, Object> changes = new HashMap<String, Object>();
        changes.put(Issue.fieldSUMMARY, null);
        changes.put(Issue.fieldDESCRIPTION, null);
        changes.put(Issue.fieldOWNER, null);
        changes.put(Issue.fieldPRIORITY, null);
        changes.put(Issue.fieldREPORTER, "testreporter");
        changes.put(Issue.fieldSTATUS, null);
        githubConnector.updateIssue("1", "test commment", changes);
        verify(githubService, times(1)).editIssue("testUser", "testProject", 1, null,
                "\n[REPORTER -> testreporter]");
        verify(githubService, times(1)).addComment("testUser", "testProject", 1, "test commment");
    }

    @Test
    public void testUpdateIssueNoChanges() {
        githubConnector.updateIssue("1", "test commment", null);
        verify(githubService, never()).editIssue(anyString(), anyString(), anyLong(), anyString(), anyString());
        verify(githubService, times(1)).addComment("testUser", "testProject", 1, "test commment");
    }

    @Test
    public void testUpdateIssueNoComment() {
        Map<String, Object> changes = new HashMap<String, Object>();
        changes.put(Issue.fieldSUMMARY, "testtitle");
        changes.put(Issue.fieldDESCRIPTION, "testdescr");
        changes.put(Issue.fieldOWNER, "testowner");
        changes.put(Issue.fieldPRIORITY, Issue.priorityNONE);
        changes.put(Issue.fieldREPORTER, "testreporter");
        changes.put(Issue.fieldSTATUS, Issue.statusASSIGNED);
        githubConnector.updateIssue("1", null, changes);
        verify(githubService, times(1)).editIssue("testUser", "testProject", 1, "testtitle",
                "testdescr\n\n[STATUS -> ASSIGNED]\n[PRIORITY -> NONE]\n[REPORTER -> testreporter]");
        verify(githubService, never()).addComment(anyString(), anyString(), anyLong(), anyString());
    }

}
