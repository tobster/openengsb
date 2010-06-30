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
package org.openengsb.drools.source;

import java.util.Collection;

import org.drools.RuleBase;
import org.openengsb.drools.RuleBaseException;
import org.openengsb.drools.message.RuleBaseElementId;
import org.openengsb.drools.message.RuleBaseElementType;

public abstract class RuleBaseSource {

    protected ResourceHandler<?> getRessourceHandler(RuleBaseElementType element) {
        throw new UnsupportedOperationException("not implemented for type " + getClass());
    }

    public abstract RuleBase getRulebase() throws RuleBaseException;

    public void add(RuleBaseElementId name, String code) throws RuleBaseException {
        this.getRessourceHandler(name.getType()).create(name, code);
    }

    public String get(RuleBaseElementId name) throws RuleBaseException {
        return this.getRessourceHandler(name.getType()).get(name);
    }

    public void update(RuleBaseElementId name, String newCode) throws RuleBaseException {
        this.getRessourceHandler(name.getType()).update(name, newCode);
    }

    public void delete(RuleBaseElementId name) throws RuleBaseException {
        this.getRessourceHandler(name.getType()).delete(name);
    }

    public Collection<RuleBaseElementId> list(RuleBaseElementType type) throws RuleBaseException {
        return this.getRessourceHandler(type).list();
    }

    public Collection<RuleBaseElementId> list(RuleBaseElementType type, String packageName) throws RuleBaseException {
        return this.getRessourceHandler(type).list(packageName);
    }
}
