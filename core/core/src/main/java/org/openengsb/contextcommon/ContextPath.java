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

package org.openengsb.contextcommon;

class ContextPath {

    private final String path;
    private final String[] elements;

    public ContextPath(String path) {
        this.path = normalizePath(path);
        this.elements = this.path.split("/", -1);
    }

    private String normalizePath(String path) {
        path = path.trim();
        path = path.replaceAll("/+", "/");

        if (path.length() > 0 && path.charAt(0) == '/') {
            path = path.substring(1);
        }

        if (path.length() > 0 && path.charAt(path.length() - 1) == '/') {
            path = path.substring(0, path.length() - 1);
        }

        return path;
    }

    public String getPath() {
        return path;
    }

    public String[] getElements() {
        return elements;
    }

    public boolean isRoot() {
        return path.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("[ContextPath, path=%s]", path);
    }
}
