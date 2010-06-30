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
package org.openengsb.drools.message;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class ListResponse {
    private Collection<RuleBaseElementId> theList;

    public ListResponse() {
    }

    public ListResponse(Collection<RuleBaseElementId> list) {
        super();
        this.theList = list;
    }

    @XmlElement(required = true, name = "list")
    public Collection<RuleBaseElementId> getList() {
        return this.theList;
    }

    public void setList(Collection<RuleBaseElementId> list) {
        this.theList = list;
    }
}
