/**
 * Copyright 2010 OpenEngSB Division, Vienna University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.domains.test.maven.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openengsb.core.common.util.AliveState;
import org.openengsb.domains.test.TestDomain;
import org.openengsb.maven.common.AbstractMavenDomainImpl;
import org.openengsb.maven.common.MavenParameters;
import org.openengsb.maven.common.MavenResult;

public class MavenTestDomainServiceImpl extends AbstractMavenDomainImpl implements TestDomain {

    private Log log = LogFactory.getLog(getClass());

    @Override
    public Boolean runTests() {
        log.info("Running tests using maven.");
        MavenParameters params = getMavenParametersForMavenCall();
        params.setGoals(new String[]{"test"});
        MavenResult mavenResult = callMaven(params);
        return mavenResult.isSuccess();
    }

    @Override
    public AliveState getAliveState() {
        log.info("validating project structure.");
        MavenParameters params = getMavenParametersForMavenCall();
        params.setGoals(new String[]{"validate"});
        MavenResult mavenResult = callMaven(params);
        if (mavenResult.isSuccess()) {
            return AliveState.ONLINE;
        } else {
            return AliveState.DISCONNECTED;
        }
    }

}
