/*
 * Copyright 2014-2015 CyberVision, Inc.
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

package org.kaaproject.kaa.sandbox.web.client.mvp.event.project;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.Project;

public class ProjectFilter {
    
    private final Set<Feature> enabledFeatures;
    private final Set<Platform> enabledPlatforms;
    private final Set<Complexity> enabledComplexities;
    
    private boolean useFeatureFilter = false;
    private boolean usePlatformFilter = false;
    private boolean useComplexityFilter = false;
    
    public ProjectFilter() {
        enabledFeatures = new HashSet<>();
        enabledPlatforms = new HashSet<>();
        enabledComplexities = new HashSet<>();
    }
    
    public ProjectFilter(Set<Feature> enabledFeatures, Set<Platform> enabledPlatforms, Set<Complexity> enabledComplexities) {
        this.enabledFeatures = enabledFeatures;
        this.enabledPlatforms = enabledPlatforms;
        this.enabledComplexities = enabledComplexities;
        
        useFeatureFilter = !enabledFeatures.isEmpty();
        usePlatformFilter = !enabledPlatforms.isEmpty();
        useComplexityFilter = !enabledComplexities.isEmpty();
    }
    
    public boolean filter(Project project) {
        boolean hasFeature = !useFeatureFilter;
        boolean hasPlatform = !usePlatformFilter;
        boolean hasComplexity= !useComplexityFilter;
        if (useFeatureFilter) {
            for (Feature feature : project.getFeatures()) {
                if (enabledFeatures.contains(feature)) {
                    hasFeature = true;
                    break;
                }
            }
        }
        if (usePlatformFilter) {
            for (Platform platform : project.getPlatforms()) {
                if (enabledPlatforms.contains(platform)) {
                    hasPlatform = true;
                    break;
                }
            }
        }
        if (useComplexityFilter) {
        	hasComplexity = enabledComplexities.contains(project.getComplexity());
        }
        return hasFeature && hasPlatform && hasComplexity;
    }

}
