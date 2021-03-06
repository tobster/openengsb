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
package org.openengsb.deploy.maven;

import org.openengsb.contextcommon.ContextHelper;
import org.openengsb.core.EventHelper;
import org.openengsb.drools.DeployDomain;
import org.openengsb.drools.events.DeployEvent;
import org.openengsb.drools.events.DeployStartEvent;
import org.openengsb.maven.common.AbstractMavenDomainImpl;
import org.openengsb.maven.common.MavenParameters;
import org.openengsb.maven.common.MavenResult;

public class MavenDeployDomainImpl extends AbstractMavenDomainImpl implements DeployDomain {

    private EventHelper eventHelper;

    public MavenDeployDomainImpl(ContextHelper contextHelper, EventHelper eventHelper) {
        super(contextHelper);
        this.eventHelper = eventHelper;
    }

    @Override
    public Boolean deployProject() {
        DeployStartEvent startEvent = new DeployStartEvent();
        MavenParameters params = getMavenParametersForMavenCall("deploy/maven-deploy");
        startEvent.setToolConnector("maven-deploy");
        startEvent.setParameters(params.toString());
        eventHelper.sendEvent(startEvent);

        MavenResult mavenResult = callMaven(params);
        DeployEvent event = new DeployEvent();
        event.setToolConnector("maven-deploy");
        event.setDeploySuccessful(mavenResult.isSuccess());
        event.setDeployOutput(mavenResult.getOutput());
        eventHelper.sendEvent(event);
        return mavenResult.isSuccess();
    }

}
