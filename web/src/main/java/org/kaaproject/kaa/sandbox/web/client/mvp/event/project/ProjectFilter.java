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

package org.kaaproject.kaa.sandbox.web.client.mvp.event.project;

import java.util.HashSet;
import java.util.Set;

import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.SdkLanguage;

public class ProjectFilter {

    private final Set<Feature> enabledFeatures;
    private final Set<SdkLanguage> enabledSdkLanguages;
    private final Set<Platform> enabledPlatforms;
    private final Set<Complexity> enabledComplexities;

    private boolean useFeatureFilter = false;
    private boolean useSdkLanguageFilter = false;
    private boolean usePlatformFilter = false;
    private boolean useComplexityFilter = false;

    public ProjectFilter() {
        enabledFeatures = new HashSet<>();
        enabledSdkLanguages = new HashSet<>();
        enabledPlatforms = new HashSet<>();
        enabledComplexities = new HashSet<>();
    }

    public ProjectFilter(Set<Feature> enabledFeatures, Set<SdkLanguage> enabledSdkLanguages, Set<Platform> enabledPlatforms, Set<Complexity> enabledComplexities) {
        this.enabledFeatures = enabledFeatures;
        this.enabledSdkLanguages = enabledSdkLanguages;
        this.enabledPlatforms = enabledPlatforms;
        this.enabledComplexities = enabledComplexities;

        useFeatureFilter = !enabledFeatures.isEmpty();
        useSdkLanguageFilter = !enabledSdkLanguages.isEmpty();
        usePlatformFilter = !enabledPlatforms.isEmpty();
        useComplexityFilter = !enabledComplexities.isEmpty();
    }

    public boolean filter(FilterableItem item) {
        boolean hasFeature = !useFeatureFilter;
        boolean hasSdkLanguage = !useSdkLanguageFilter;
        boolean hasPlatform = !usePlatformFilter;
        boolean hasComplexity = !useComplexityFilter;
        if (useFeatureFilter) {
            Set<Feature> features = item.getFeatures();
            for (Feature feature : features) {
                if (enabledFeatures.contains(feature)) {
                    hasFeature = true;
                    break;
                }
            }
        }
        if (useSdkLanguageFilter) {
            Set<SdkLanguage> sdkLanguages = item.getSdkLanguages();
            for (SdkLanguage sdkLanguage : sdkLanguages) {
                if (enabledSdkLanguages.contains(sdkLanguage)) {
                    hasSdkLanguage = true;
                    break;
                }
            }
        }
        if (usePlatformFilter) {
        	Set<Platform> platforms = item.getPlatforms();
            for (Platform platform : platforms) {
                if (enabledPlatforms.contains(platform)) {
                    hasPlatform = true;
                    break;
                }
            }
        }
        if (useComplexityFilter) {
            hasComplexity = enabledComplexities.contains(item.getComplexity());
        }
        return hasFeature && hasSdkLanguage && hasPlatform && hasComplexity;
    }

}
