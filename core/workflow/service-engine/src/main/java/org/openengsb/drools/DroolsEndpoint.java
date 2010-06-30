/**

   Copyright 2010 OpenEngSB Division, Vienna University of Technology

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE\-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package org.openengsb.drools;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.jbi.servicedesc.ServiceEndpoint;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;

import org.apache.servicemix.common.DefaultComponent;
import org.apache.servicemix.common.ServiceUnit;
import org.drools.RuleBase;
import org.openengsb.contextcommon.ContextHelper;
import org.openengsb.core.MessageProperties;
import org.openengsb.core.endpoints.SimpleEventEndpoint;
import org.openengsb.core.model.Event;
import org.openengsb.drools.helper.XmlHelper;
import org.openengsb.drools.message.GetResponse;
import org.openengsb.drools.message.ListResponse;
import org.openengsb.drools.message.ManageRequest;
import org.openengsb.drools.message.RuleBaseElementId;
import org.openengsb.drools.message.RuleBaseElementType;
import org.openengsb.drools.source.RuleBaseSource;

/**
 * @org.apache.xbean.XBean element="droolsEndpoint"
 *                         description="Drools Component"
 */
public class DroolsEndpoint extends SimpleEventEndpoint {

    private RuleBase ruleBase;

    private RuleBaseSource ruleSource;

    private boolean noRemoteLogging;

    /**
     * List of global variables for rules to use.
     */
    private Map<String, Object> globals = new HashMap<String, Object>();

    public DroolsEndpoint() {
    }

    public DroolsEndpoint(DefaultComponent component, ServiceEndpoint endpoint) {
        super(component, endpoint);
    }

    public DroolsEndpoint(ServiceUnit su, QName service, String endpoint) {
        super(su, service, endpoint);
    }

    @Override
    public synchronized void start() throws Exception {
        super.start();
        ruleBase = ruleSource.getRulebase();
    }

    @Override
    protected void processInOnly(MessageExchange exchange, NormalizedMessage in) throws Exception {
        if (exchange.getOperation().getLocalPart().equals("event")) {
            super.processInOnly(exchange, in);
            return;
        }
        Source msgSource = in.getContent();
        ManageRequest request = XmlHelper.unmarshal(ManageRequest.class, msgSource);
        QName op = exchange.getOperation();
        if ("create".equals(op.getLocalPart())) {
            ruleSource.add(request.getId(), request.getCode());
        } else if ("delete".equals(op.getLocalPart())) {
            ruleSource.delete(request.getId());
        } else if ("update".equals(op.getLocalPart())) {
            ruleSource.update(request.getId(), request.getCode());
        }
    }

    @Override
    protected void processInOut(MessageExchange exchange, NormalizedMessage in, NormalizedMessage out) throws Exception {
        if (exchange.getOperation().getLocalPart().equals("event")) {
            super.processInOut(exchange, in, out);
            return;
        }
        Source msgSource = in.getContent();
        ManageRequest request = XmlHelper.unmarshal(ManageRequest.class, msgSource);
        QName op = exchange.getOperation();
        if ("list".equals(op.getLocalPart())) {
            Collection<RuleBaseElementId> list;
            RuleBaseElementType type = request.getId().getType();
            String packageName = request.getId().getPackageName();
            if (packageName == null) {
                list = ruleSource.list(type);
            } else {
                list = ruleSource.list(type, packageName);
            }
            ListResponse response = new ListResponse(list);
            Source outContent = XmlHelper.marshal(response);
            out.setContent(outContent);
        } else if ("get".equals(op.getLocalPart())) {
            String code = ruleSource.get(request.getId());
            GetResponse response = new GetResponse(request.getId(), code);
            Source outContent = XmlHelper.marshal(response);
            out.setContent(outContent);
        }
    }

    @Override
    protected void handleEvent(Event event, ContextHelper contextHelper, MessageProperties msgProperties) {
        try {
            init();
        } catch (RuleBaseException e) {
            throw new RuntimeException(e);
        }
        drools(event, msgProperties);
    }

    @Override
    protected void handleEvent(MessageExchange exchange, NormalizedMessage in, ContextHelper contextHelper,
            MessageProperties msgProperties) throws MessagingException {
        forwardMessageToLogEndpoint(in);
        super.handleEvent(exchange, in, contextHelper, msgProperties);
    }

    private void forwardMessageToLogEndpoint(NormalizedMessage messageToLog) throws MessagingException {
        if (noRemoteLogging) {
            return;
        }
        InOnly loggingMessageExchange = getExchangeFactory().createInOnlyExchange();
        QName loggingServiceIdentification = getLoggingServiceIdentification();
        loggingMessageExchange.setService(loggingServiceIdentification);
        loggingMessageExchange.setInMessage(messageToLog);
        send(loggingMessageExchange);
    }

    private QName getLoggingServiceIdentification() {
        String loggingNamespace = "urn:openengsb:logging";
        String loggingServiceName = "logging";
        return new QName(loggingNamespace, loggingServiceName);
    }

    private void init() throws RuleBaseException {

    }

    public final boolean isNoRemoteLogging() {
        return this.noRemoteLogging;
    }

    public final void setNoRemoteLogging(boolean noRemoteLogging) {
        this.noRemoteLogging = noRemoteLogging;
    }

    public RuleBase getRuleBase() {
        return this.ruleBase;
    }

    public void setRuleBase(RuleBase ruleBase) {
        this.ruleBase = ruleBase;
    }

    public final RuleBaseSource getRuleSource() {
        return this.ruleSource;
    }

    public final void setRuleSource(RuleBaseSource ruleSource) {
        this.ruleSource = ruleSource;
    }

    public Map<String, Object> getGlobals() {
        return this.globals;
    }

    public void setGlobals(Map<String, Object> variables) {
        this.globals = variables;
    }

    /**
     * handle the MessageExchange with drools.
     *
     * @param e2 exchange to handle
     */
    protected void drools(Event e, MessageProperties msgProperties) {
        Collection<Object> objects = Arrays.asList(new Object[] { e });
        DroolsExecutionContext drools = new DroolsExecutionContext(this, objects, msgProperties);
        try {
            drools.start();
        } finally {
            shutdown(drools);
        }
    }

    private void shutdown(DroolsExecutionContext drools) {
        drools.stop();
        this.ruleBase = null;
    }

}
