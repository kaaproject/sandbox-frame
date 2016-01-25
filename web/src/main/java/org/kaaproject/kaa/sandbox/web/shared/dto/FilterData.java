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

import java.io.Serializable;
import java.util.List;

import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.SdkLanguage;

public class FilterData implements Serializable {

    private static final long serialVersionUID = 217150709820867407L;
    
    private List<Feature> availableFeatures;
    private List<SdkLanguage> availableSdkLanguages;
    private List<Platform> availablePlatforms;
    private List<Complexity> availableComplexities;
    
    public FilterData() {
    }
    
    public FilterData(List<Feature> availableFeatures,
            List<SdkLanguage> availableSdkLanguages,
            List<Platform> availablePlatforms,
            List<Complexity> availableComplexities) {
        super();
        this.availableFeatures = availableFeatures;
        this.availableSdkLanguages = availableSdkLanguages;
        this.availablePlatforms = availablePlatforms;
        this.availableComplexities = availableComplexities;
    }

    public List<Feature> getAvailableFeatures() {
        return availableFeatures;
    }

    public void setAvailableFeatures(List<Feature> availableFeatures) {
        this.availableFeatures = availableFeatures;
    }

    public List<SdkLanguage> getAvailableSdkLanguages() {
        return availableSdkLanguages;
    }

    public void setAvailableSdkLanguages(List<SdkLanguage> availableSdkLanguages) {
        this.availableSdkLanguages = availableSdkLanguages;
    }

    public List<Platform> getAvailablePlatforms() {
        return availablePlatforms;
    }

    public void setAvailablePlatforms(List<Platform> availablePlatforms) {
        this.availablePlatforms = availablePlatforms;
    }

    public List<Complexity> getAvailableComplexities() {
        return availableComplexities;
    }

    public void setAvailableComplexities(List<Complexity> availableComplexities) {
        this.availableComplexities = availableComplexities;
    }
    
}
