/*
 * Copyright 2014-2016 CyberVision, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kaaproject.kaa.sandbox.web.shared.dto;

import org.kaaproject.kaa.examples.common.projects.Bundle;
import org.kaaproject.kaa.examples.common.projects.Project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProjectsData implements Serializable{

	private static final long serialVersionUID = 6752405093583545845L;
	
	private Map<String, Project> projectsMap = new HashMap<>();
    private Map<String, Bundle> bundlesMap = new HashMap<>();

    public ProjectsData() {
    }

    public ProjectsData(Map<String, Project> projectsMap, Map<String, Bundle> bundlesMap) {
        this.projectsMap = projectsMap;
        this.bundlesMap = bundlesMap;
    }

    public Map<String, Project> getProjectsMap() {
        return projectsMap;
    }

    public void setProjectsMap(Map<String, Project> projectsMap) {
        this.projectsMap = projectsMap;
    }

    public Map<String, Bundle> getBundlesMap() {
        return bundlesMap;
    }

    public void setBundlesMap(Map<String, Bundle> bundlesMap) {
        this.bundlesMap = bundlesMap;
    }
}
