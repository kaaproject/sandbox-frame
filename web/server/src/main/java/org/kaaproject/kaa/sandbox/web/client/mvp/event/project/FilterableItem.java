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

import java.util.Set;

import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.SdkLanguage;

public class FilterableItem {

    private Set<SdkLanguage> sdkLanguages;
    private Set<Platform> platforms;
    private Set<Feature> features;
    private Complexity complexity;

    public FilterableItem() {
    }

    public FilterableItem(Set<SdkLanguage> sdkLanguages, Set<Platform> platforms, Set<Feature> features, Complexity complexity) {
        this.sdkLanguages = sdkLanguages;
        this.platforms = platforms;
        this.features = features;
        this.complexity = complexity;
    }
    
    public Set<SdkLanguage> getSdkLanguages() {
        return sdkLanguages;
    }

    public void setSdkLanguages(Set<SdkLanguage> sdkLanguages) {
        this.sdkLanguages = sdkLanguages;
    }

    public Set<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Set<Platform> platforms) {
        this.platforms = platforms;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }
}
