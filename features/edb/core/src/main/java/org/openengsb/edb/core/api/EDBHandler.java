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

package org.openengsb.edb.core.api;

import java.io.File;
import java.util.List;

import org.openengsb.edb.core.entities.GenericContent;
import org.openengsb.edb.core.repository.RepositoryFactory;
import org.openengsb.edb.core.search.IndexFactory;

/**
 * "god" interface to provide an API,'Driver',... to handle access for the
 * Engineering Data Base. NOTE FOR IMPLEMENTATIONS: All methods provided by such
 * a handler should be synchronized, either by using the explicit keyword or own
 * synchronization.
 * 
 */
public interface EDBHandler {

    /**
     * Query the EDB for data (from the current revision), returns a list of
     * generic content. Search syntax depends on implementation. First element
     * of the list should ONLY contain information about the 'head', iff
     * headRevision is set true (number/id/..)
     */
    List<GenericContent> query(String query, boolean headRevision) throws EDBException;

    /**
     * Query the EDB for data structures. Returns all nodes below a given path,
     * queries consist of an ordered sequence of node names (like folders in a
     * file system), always starting from the root element. The actual data is
     * stored in leaves which will never returned by this search. Searches on
     * the lowest level of a tree will return the leaves names,their UUIDs, but
     * no actual content, to this purpose, use {@link #query(String, boolean)}.
     */
    List<GenericContent> queryNodes(List<String> query) throws EDBException;

    /**
     * Add a list of objects to be persisted on the next update (should be
     * called by the same user in the same session). Any element within the
     * given list that does not have a UUID, will be assigned a random one.
     */
    EDBHandler add(List<GenericContent> content) throws EDBException;

    /**
     * Mark a list of objects to be deleted on the next update (should be called
     * by the same user in the same session). Any element not having a UUID will
     * be ignored.
     */
    EDBHandler remove(List<GenericContent> content) throws EDBException;

    /**
     * Perform all actions marked with {@link #add(List)} and/or
     * {@link #delete(List)} against the EDB. User performing this commit must
     * provide username and email.
     */
    String commit(String user, String email) throws EDBException;

    /**
     * Undo the last 'steps' update operations. Values lesser 1 cause a removal
     * off all pending add or remove operations. Number/Id (implementation
     * dependent) of the current revision is required to ensure no unintended
     * reset is done.
     */
    String reset(String headInfo, int steps) throws EDBException;

    /**
     * Removes all pending changes not yet committed WITHOUT any checks.
     */
    EDBHandler resetToCurrent() throws EDBException;

    /**
     * Retrieve identifier/counter/etc of the current version of the EDB
     * instance.
     */
    String getHeadInfo();

    /**
     * Retrieve repository base path (for GenericContent)
     */
    File getRepositoryBase();

    /**
     * Uses
     * {@link org.openengsb.edb.core.repository.Repository#removeRepository()}
     * to completely remove the EDB instance. This should be used with extreme
     * caution, as all versioning information is removed this way.
     */
    void removeRepository();

    /**
     * Setter for the factory to obtain/create the search index.
     */
    EDBHandler setFactoryIndex(IndexFactory indexFactory);

    /**
     * Setter for the factory to obtain/create the repository managing the
     * search index. May be identical with the index data repository factory.
     */
    EDBHandler setFactoryIndexRepo(RepositoryFactory indexRepoFactory);

    /**
     * Setter for the factory to obtain/create the repository managing the
     * content. May be identical with the index index repository factory.
     */
    EDBHandler setFactoryDataRepo(RepositoryFactory dataRepoFactory);

}
