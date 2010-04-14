/**

   Copyright 2010 OpenEngSB Division, Vienna University of Technology

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
 */
package org.openengsb.trac.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.xmlrpc.XmlRpcException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openengsb.drools.model.Issue;
import org.openengsb.trac.TracConnector;
import org.openengsb.trac.constants.TracFieldConstants;
import org.openengsb.trac.constants.TracPriorityConstants;
import org.openengsb.trac.constants.TracStatusConstants;
import org.openengsb.trac.xmlrpc.Ticket;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:../test-classes/test-bean.xml" })
public class TracTest {
    private TracConnector tracConnector;
    private Ticket ticket;

    @Before
    public void setUp() {
        ticket = Mockito.mock(Ticket.class);
        tracConnector = new TracConnector();
        tracConnector.setTicket(ticket);
    }

    @Test
    public void testCreateIssue() throws XmlRpcException {
        Issue i = new Issue();
        String s = "test " + new Date();
        i.setSummary(s);
        i.setDescription("testdescription");
        i.setOwner("testowner");
        i.setPriority(Issue.priorityURGENT);
        i.setReporter("testreporter");
        i.setStatus(Issue.statusNEW);

        Hashtable<String, String> attributes = new Hashtable<String, String>();
        attributes.put(TracFieldConstants.FIELD_OWNER, "testowner");
        attributes.put(TracFieldConstants.FIELD_REPORTER, "testreporter");
        attributes.put(TracFieldConstants.FIELD_PRIORITY, TracPriorityConstants.PRIORITY_URGENT);
        attributes.put(TracFieldConstants.FIELD_STATUS, TracStatusConstants.STATUS_NEW);

        tracConnector.createIssue(i);
        Mockito.verify(ticket, Mockito.times(1)).create(Mockito.eq(s), Mockito.eq("testdescription"),
                Mockito.eq(attributes));
    }

    @Test
    public void testAddComment() throws XmlRpcException {
        tracConnector.addComment("5", "testcomment");
        Mockito.verify(ticket, Mockito.times(1)).update(Mockito.eq(5), Mockito.eq("testcomment"));
    }

    @Test
    public void testDeleteIssue() throws XmlRpcException {
        tracConnector.deleteIssue("5");
        Mockito.verify(ticket, Mockito.times(1)).delete(Mockito.eq(5));
    }

    @Test
    public void testUpdateIssue() throws XmlRpcException {
        HashMap<String, Object> changes = new HashMap<String, Object>();
        changes.put(Issue.fieldSTATUS, Issue.statusCLOSED);

        Hashtable<String, String> result = new Hashtable<String, String>();
        result.put(TracFieldConstants.FIELD_STATUS, TracStatusConstants.STATUS_CLOSED);

        tracConnector.updateIssue("3", null, changes);
        Mockito.verify(ticket, Mockito.times(1)).update(Mockito.eq(3), Mockito.eq("[No comment added by author]"),
                Mockito.eq(result));
    }

    @Test
    public void testCreateXmlRpcErrorCatching() throws XmlRpcException {
        Mockito.when(ticket.create(Mockito.anyString(), Mockito.anyString(), Mockito.any(Hashtable.class))).thenThrow(new XmlRpcException("test"));
        tracConnector.createIssue(new Issue());
    }
    
    @Test
    public void testUpdateXmlRpcErrorCatching() throws XmlRpcException {
        Mockito.when(ticket.update(Mockito.anyInt(), Mockito.anyString(), Mockito.any(Hashtable.class))).thenThrow(new XmlRpcException("test"));
        tracConnector.updateIssue("0", "test", new HashMap<String, Object>());
    }
    
    @Test
    public void testUpdateNuberformatErrorCatching() throws NumberFormatException {
        tracConnector.updateIssue("test", "test",new HashMap<String, Object>());
    }
    
    @Test
    public void testCommentXmlRpcErrorCatching() throws XmlRpcException {
        Mockito.when(ticket.update(Mockito.anyInt(), Mockito.anyString())).thenThrow(new XmlRpcException("test"));
        tracConnector.addComment("0", "test");
    }
    
    @Test
    public void testCommentNuberformatErrorCatching() throws NumberFormatException {
        tracConnector.addComment("test", "test");
    }
    
    @Test
    public void testDeleteXmlRpcErrorCatching() throws XmlRpcException {
        Mockito.when(ticket.delete(Mockito.anyInt())).thenThrow(new XmlRpcException("test"));
        tracConnector.deleteIssue("0");
    }
    
    @Test
    public void testDeleteNuberformatErrorCatching() throws NumberFormatException {
        tracConnector.deleteIssue("test");
    }
}