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
package org.openengsb.drools.source.dir;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openengsb.drools.message.RuleBaseElementId;
import org.openengsb.drools.source.DirectoryRuleSource;
import org.openengsb.drools.source.RuleBaseSource;
import org.openengsb.drools.source.RuleSourceCrudTest;

public class DirectorySourceCrudTest extends RuleSourceCrudTest<DirectoryRuleSource> {

    public DirectorySourceCrudTest(RuleBaseElementId id1, String code1, RuleBaseElementId id2, String code2) {
        super(id1, code1, id2, code2);
    }

    @Override
    public void setUp() throws Exception {
        File rulebaseReferenceDirectory = new File("src/test/resources/rulebase");
        File rulebaseTestDirectory = new File("data/rulebase");
        FileUtils.copyDirectory(rulebaseReferenceDirectory, rulebaseTestDirectory);
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        File ruleDir = new File("data");
        while (ruleDir.exists()) {
            FileUtils.deleteQuietly(ruleDir);
        }
    }

    @Override
    protected RuleBaseSource getRuleBaseSource() {
        return new DirectoryRuleSource("data/rulebase");
    }
}
