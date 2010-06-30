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
package org.openengsb.util;

import java.util.ArrayList;

public class Lists {
    /**
     * Default initial capacity for an {@code ArrayList} as stated in the API
     * doc.
     */
    public static final int ARRAYLIST_INITIAL_CAPACITY = 10;

    /**
     * Constructs a list containing the specified elements in the given order.
     */
    public static <E> ArrayList<E> newArrayList(E... elems) {
        ArrayList<E> list = new ArrayList<E>(
                elems.length <= Lists.ARRAYLIST_INITIAL_CAPACITY ? Lists.ARRAYLIST_INITIAL_CAPACITY : elems.length);
        for (E e : elems) {
            list.add(e);
        }
        return list;
    }

    private Lists() {
        throw new AssertionError();
    }
}
