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
package org.openengsb.build;

import javax.xml.namespace.QName;

import org.openengsb.contextcommon.ContextHelper;
import org.openengsb.core.endpoints.ForwardEndpoint;
import org.openengsb.drools.BuildDomain;

/**
 * @org.apache.xbean.XBean element="buildEndpoint" description="Build Endpoint"
 */
public class BuildEndpoint extends ForwardEndpoint<BuildDomain> {

    @Override
    protected QName getForwardTarget(ContextHelper contextHelper) {
        String defaultName = contextHelper.getValue("build/default");
        String serviceName = contextHelper.getValue("build/" + defaultName + "/servicename");
        String namespace = contextHelper.getValue("build/" + defaultName + "/namespace");
        return new QName(namespace, serviceName);
    }

}
