/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openengsb.issues.github;

import org.joda.time.DateTime;

public class GithubComment {
    private DateTime created_at;
    private String body;
    private DateTime updated_at;
    private long id;
    private String user;

    public GithubComment() {
    }

    public DateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(final DateTime created_at) {
        this.created_at = created_at;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public DateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(final DateTime updated_at) {
        this.updated_at = updated_at;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    @Override
    public java.lang.String toString() {
        return "GithubComment(created_at=" + created_at + ", body=" + body + ", updated_at=" + updated_at + ", id="
                + id + ", user=" + user + ")";
    }
}