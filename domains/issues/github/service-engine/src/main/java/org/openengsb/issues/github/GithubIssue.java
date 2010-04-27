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

import java.util.List;
import org.joda.time.DateTime;

public class GithubIssue {
   
    private long number;
    private long votes;
    private DateTime created_at;
    private String body;
    private String title;
    private DateTime updated_at;
    private DateTime closed_at;
    private String user;
    private List<String> labels;
    private String state;

    public GithubIssue() {
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(final long number) {
        this.number = number;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(final long votes) {
        this.votes = votes;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public DateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(final DateTime updated_at) {
        this.updated_at = updated_at;
    }

    public DateTime getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(final DateTime closed_at) {
        this.closed_at = closed_at;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(final List<String> labels) {
        this.labels = labels;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    @Override
    public java.lang.String toString() {
        return "GithubIssue(number=" + number + ", votes=" + votes + ", created_at=" + created_at + ", body=" + body
                + ", title=" + title + ", updated_at=" + updated_at + ", closed_at=" + closed_at + ", user=" + user
                + ", labels=" + labels + ", state=" + state + ")";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + ((closed_at == null) ? 0 : closed_at.hashCode());
        result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
        result = prime * result + ((labels == null) ? 0 : labels.hashCode());
        result = prime * result + (int) (number ^ (number >>> 32));
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((updated_at == null) ? 0 : updated_at.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + (int) (votes ^ (votes >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GithubIssue other = (GithubIssue) obj;
        if (body == null) {
            if (other.body != null)
                return false;
        } else if (!body.equals(other.body))
            return false;
        if (closed_at == null) {
            if (other.closed_at != null)
                return false;
        } else if (!closed_at.equals(other.closed_at))
            return false;
        if (created_at == null) {
            if (other.created_at != null)
                return false;
        } else if (!created_at.equals(other.created_at))
            return false;
        if (labels == null) {
            if (other.labels != null)
                return false;
        } else if (!labels.equals(other.labels))
            return false;
        if (number != other.number)
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (updated_at == null) {
            if (other.updated_at != null)
                return false;
        } else if (!updated_at.equals(other.updated_at))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (votes != other.votes)
            return false;
        return true;
    }
}