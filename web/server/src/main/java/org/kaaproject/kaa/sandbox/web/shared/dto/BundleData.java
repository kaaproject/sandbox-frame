/**
 *  Copyright 2014-2016 CyberVision, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.kaaproject.kaa.sandbox.web.shared.dto;

import org.kaaproject.kaa.examples.common.projects.Bundle;
import org.kaaproject.kaa.examples.common.projects.Project;

import java.io.Serializable;
import java.util.List;

public class BundleData implements Serializable {

	private static final long serialVersionUID = -3659915249345297095L;

	private Bundle bundle;
    private List<Project> bundleProjects;

    public BundleData() {
    }

    public BundleData(Bundle bundle, List<Project> bundleProjects) {
        this.bundle = bundle;
        this.bundleProjects = bundleProjects;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public List<Project> getBundleProjects() {
        return bundleProjects;
    }

    public void setBundleProjects(List<Project> bundleProjects) {
        this.bundleProjects = bundleProjects;
    }
}
