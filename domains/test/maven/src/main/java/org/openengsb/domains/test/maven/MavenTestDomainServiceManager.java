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

package org.openengsb.domains.test.maven;

import java.util.Map;

import org.openengsb.core.common.AbstractServiceManager;
import org.openengsb.core.common.ServiceInstanceFactory;
import org.openengsb.core.common.validation.MultipleAttributeValidationResult;
import org.openengsb.domains.test.TestDomain;
import org.openengsb.domains.test.maven.internal.MavenTestDomainServiceImpl;

public class MavenTestDomainServiceManager extends AbstractServiceManager<TestDomain, MavenTestDomainServiceImpl> {

    public MavenTestDomainServiceManager(ServiceInstanceFactory<TestDomain, MavenTestDomainServiceImpl> factory) {
        super(factory);
    }

    @Override
    public MultipleAttributeValidationResult updateWithValidation(String id, Map<String, String> attributes) {
        // TODO Auto-generated method stub
        return null;
    }
}
