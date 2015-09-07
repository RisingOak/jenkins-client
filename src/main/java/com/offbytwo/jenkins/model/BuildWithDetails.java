/*
 * Copyright (c) 2013 Rising Oak LLC.
 *
 * Distributed under the MIT license: http://opensource.org/licenses/MIT
 */

package com.offbytwo.jenkins.model;

import com.google.common.base.Predicate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static com.google.common.collect.Collections2.filter;

public class BuildWithDetails extends Build {

    List actions;
    boolean building;
    String description;
    int duration;
    int estimatedDuration;
    String fullDisplayName;
    String id;
    long timestamp;
    BuildResult result;
    List<Artifact> artifacts;
    String consoleOutputText;
    String consoleOutputHtml;
    BuildChangeSet changeSet;
    List<BuildChangeSetAuthor> culprits;

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public boolean isBuilding() { return building; }

    public List<BuildCause> getCauses() {
        // actions is a List<Map<String, List<Map<String, String ..
        // we have a List[i]["causes"] -> List[BuildCause]
        Collection causes = filter(actions, new Predicate<Map<String, Object>>() {
            @Override
            public boolean apply(Map<String, Object> action) {
                return action.containsKey("causes");
            }
        });

        List<BuildCause> result = new ArrayList<BuildCause>();

        if (causes != null && ! causes.isEmpty()) {
            // The underlying key-value can be either a <String, Integer> or a <String, String>.
            List<Map<String, Object>> causes_blob =
                    ((Map<String, List<Map<String, Object>>>) causes.toArray()[0]).get("causes");
            for( Map<String, Object> cause : causes_blob) {

                BuildCause cause_object = new BuildCause();
                cause_object.setShortDescription((String)cause.get("shortDescription"));
                cause_object.setUpstreamBuild((Integer)cause.get("upstreamBuild"));
                cause_object.setUpstreamProject((String)cause.get("upstreamProject"));
                cause_object.setUpstreamUrl((String)cause.get("upstreamUrl"));
                cause_object.setUserId((String)cause.get("userId"));
                cause_object.setUserName((String)cause.get("userName"));

                result.add(cause_object);
            }
        }

        return result;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public String getFullDisplayName() {
        return fullDisplayName;
    }

    public String getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public BuildResult getResult() {
        return result;
    }

    public List getActions() {
        return actions;
    }

    public Map<String, String> getParameters() {
        Collection parameters = filter(actions, new Predicate<Map<String, Object>>() {
            @Override
            public boolean apply(Map<String, Object> action) {
                return action.containsKey("parameters");
            }
        });

        Map<String, String> params = new HashMap<String, String>();

        if (parameters != null && !parameters.isEmpty()) {
            for (Map<String, Object> param : ((Map<String, List<Map<String, Object>>>) parameters.toArray()[0]).get("parameters")) {
                String key = (String) param.get("name");
                Object value = param.get("value");
                params.put(key, String.valueOf(value));
            }
        }

        return params;
    }

    public String getConsoleOutputText() throws IOException {
        return client.get(url + "/logText/progressiveText");
    }

    public String getConsoleOutputHtml() throws IOException {
        return client.get(url + "/logText/progressiveHtml");
    }

    public BuildChangeSet getChangeSet() {
        return changeSet;
    }

    public void setChangeSet(BuildChangeSet changeSet) {
        this.changeSet = changeSet;
    }

    public List<BuildChangeSetAuthor> getCulprits() {
        return culprits;
    }

    public void setCulprits(List<BuildChangeSetAuthor> culprits) {
        this.culprits = culprits;
    }

    public InputStream downloadArtifact(Artifact a) throws IOException, URISyntaxException {
        //We can't just put the artifact's relative path at the end of the url string,
        //as there could be characters that need to be escaped.
        URI uri = new URI(getUrl());
        String artifactPath = uri.getPath() + "artifact/" + a.getRelativePath();
        URI artifactUri = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), artifactPath, "", "");
        return client.getFile(artifactUri);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        BuildWithDetails other = (BuildWithDetails) obj;
        if (actions == null) {
            if (other.actions != null)
                return false;
        } else if (!actions.equals(other.actions))
            return false;
        if (artifacts == null) {
            if (other.artifacts != null)
                return false;
        } else if (!artifacts.equals(other.artifacts))
            return false;
        if (building != other.building)
            return false;
        if (changeSet == null) {
            if (other.changeSet != null)
                return false;
        } else if (!changeSet.equals(other.changeSet))
            return false;
        if (consoleOutputHtml == null) {
            if (other.consoleOutputHtml != null)
                return false;
        } else if (!consoleOutputHtml.equals(other.consoleOutputHtml))
            return false;
        if (consoleOutputText == null) {
            if (other.consoleOutputText != null)
                return false;
        } else if (!consoleOutputText.equals(other.consoleOutputText))
            return false;
        if (culprits == null) {
            if (other.culprits != null)
                return false;
        } else if (!culprits.equals(other.culprits))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (duration != other.duration)
            return false;
        if (estimatedDuration != other.estimatedDuration)
            return false;
        if (fullDisplayName == null) {
            if (other.fullDisplayName != null)
                return false;
        } else if (!fullDisplayName.equals(other.fullDisplayName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (result != other.result)
            return false;
        if (timestamp != other.timestamp)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((actions == null) ? 0 : actions.hashCode());
        result = prime * result + ((artifacts == null) ? 0 : artifacts.hashCode());
        result = prime * result + (building ? 1231 : 1237);
        result = prime * result + ((changeSet == null) ? 0 : changeSet.hashCode());
        result = prime * result + ((consoleOutputHtml == null) ? 0 : consoleOutputHtml.hashCode());
        result = prime * result + ((consoleOutputText == null) ? 0 : consoleOutputText.hashCode());
        result = prime * result + ((culprits == null) ? 0 : culprits.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + duration;
        result = prime * result + estimatedDuration;
        result = prime * result + ((fullDisplayName == null) ? 0 : fullDisplayName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
        result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }
}
