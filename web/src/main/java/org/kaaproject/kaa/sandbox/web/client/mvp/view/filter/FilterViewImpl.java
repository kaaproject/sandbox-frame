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

package org.kaaproject.kaa.sandbox.web.client.mvp.view.filter;

import com.google.gwt.dom.client.Style;
import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.SdkLanguage;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectFilter;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectFilterEvent;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectFilterEventHandler;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.FilterView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.widget.FilterPanel;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.widget.LeftPanelWidget;
import org.kaaproject.kaa.sandbox.web.client.util.Analytics;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;
import org.kaaproject.kaa.sandbox.web.shared.dto.FilterData;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FilterViewImpl extends LeftPanelWidget implements FilterView, ValueChangeHandler<Boolean> {

    private VerticalPanel filterPanel;
    private DemoProjectsFeatureFilter featureFilter;
    private DemoProjectsSdkLanguageFilter sdkLanguageFilter;
    private DemoProjectsPlatformFilter platformFilter;
    private DemoProjectsComplexityFilter complexityFilter;
    
    public FilterViewImpl() {
        super(Unit.PX);
        
        setHeadTitle(Utils.constants.filter());
        
        filterPanel = new VerticalPanel();
        
        filterPanel.setWidth("100%");
        featureFilter = new DemoProjectsFeatureFilter();
        filterPanel.add(featureFilter);
        featureFilter.addValueChangeHandler(this);
        
        sdkLanguageFilter = new DemoProjectsSdkLanguageFilter();
        filterPanel.add(sdkLanguageFilter);
        sdkLanguageFilter.addValueChangeHandler(this);
        
        platformFilter = new DemoProjectsPlatformFilter();
        filterPanel.add(platformFilter);
        platformFilter.addValueChangeHandler(this);
        
        complexityFilter = new DemoProjectsComplexityFilter();
        filterPanel.add(complexityFilter);
        complexityFilter.addValueChangeHandler(this);
        
        setContent(filterPanel);
        
    }
    
    @Override
    public void onValueChange(ValueChangeEvent<Boolean> event) {
    	if (isActive) {
    		fireFilter();
    	}
    }
    
    private void fireFilter() {
        ProjectFilter projectFilter = new ProjectFilter(featureFilter.getEnabledFilterEntities(), 
                sdkLanguageFilter.getEnabledFilterEntities(), 
                platformFilter.getEnabledFilterEntities(), 
                complexityFilter.getEnabledFilterEntities());
        ProjectFilterEvent filterEvent = new ProjectFilterEvent(projectFilter);
        fireEvent(filterEvent);
    }
    
    @Override
    public HandlerRegistration addProjectFilterHandler(
            ProjectFilterEventHandler handler) {
        return this.addHandler(handler, ProjectFilterEvent.getType());
    }
    
    private class DemoProjectsFeatureFilter extends FilterPanel<Feature> {

        public DemoProjectsFeatureFilter() {
            super(Utils.constants.featuresFilter());
            setWidth("100%");
        }
        
        public void addFeature(Feature feature) {
            Analytics.sendEventImpl(Analytics.FEATURE_CATEGORY,Analytics.CLICK,Utils.getFeatureText(feature));
            addItem(feature, Utils.getFeatureIcon(feature), Utils.getFeatureBackgroundClass(feature), Utils.getFeatureText(feature));
        }
    }
    
    private class DemoProjectsSdkLanguageFilter extends FilterPanel<SdkLanguage> {

        public DemoProjectsSdkLanguageFilter() {
            super(Utils.constants.sdkLanguagesFilter());
            setWidth("100%");
        }
        
        public void addSdkLanguage(SdkLanguage sdkLanguage) {
            Analytics.sendEventImpl(Analytics.SDK_CATEGORY,Analytics.CLICK,Utils.getSdkLanguageText(sdkLanguage));
            addItem(sdkLanguage, Utils.getSdkLanguageIcon(sdkLanguage), Utils.getSdkLanguageBackgroundClass(sdkLanguage), Utils.getSdkLanguageText(sdkLanguage));
        }
    }
    
    private class DemoProjectsPlatformFilter extends FilterPanel<Platform> {

        public DemoProjectsPlatformFilter() {
            super(Utils.constants.platformsFilter());
            setWidth("100%");
        }
        
        public void addPlatform(Platform platform) {
            Analytics.sendEventImpl(Analytics.PLATFORMS_CATEGORY,Analytics.CLICK, Utils.getPlatformText(platform));
            addItem(platform, Utils.getFilterPlatformIcon(platform), Utils.getPlatformBackgroundClass(platform), Utils.getPlatformText(platform));
        }
    }

    private class DemoProjectsComplexityFilter extends FilterPanel<Complexity> {

        public DemoProjectsComplexityFilter() {
            super(Utils.constants.complexity());
            setWidth("100%");
        }
        
        public void addComplexity(Complexity complexity) {
            addItem(complexity, Utils.getFilterComplexityIcon(complexity), Utils.getComplexityBackgroundClass(complexity), Utils.getComplexityText(complexity));
        }
    }

    @Override
    public void reset() {
        sdkLanguageFilter.reset();
        featureFilter.reset();
        platformFilter.reset();
        complexityFilter.reset();
    }
    
    @Override
    public void setFilterData(FilterData filterData) {
        for (Feature feature : filterData.getAvailableFeatures()) {
            featureFilter.addFeature(feature);
        }
        for (SdkLanguage sdkLanguage : filterData.getAvailableSdkLanguages()) {
            sdkLanguageFilter.addSdkLanguage(sdkLanguage);
        }
        for (Platform platform : filterData.getAvailablePlatforms()) {
            platformFilter.addPlatform(platform);
        }
        for (Complexity complexity : filterData.getAvailableComplexities()) {
            complexityFilter.addComplexity(complexity);
        }
        featureFilter.updateItemsHeight();
        sdkLanguageFilter.updateItemsHeight();
        platformFilter.updateItemsHeight();
        complexityFilter.updateItemsHeight();
    }

    @Override
    public void setActive(boolean active) {
    	super.setActive(active);
    	sdkLanguageFilter.setActive(active);
    	featureFilter.setActive(active);
    	platformFilter.setActive(active);
    	complexityFilter.setActive(active);
    }

    @Override
    public FilterPanel<?> getDemoProjectsFeatureFilter() {
        return featureFilter;
    }

    @Override
    public FilterPanel<?> getDemoProjectsSdkLanguageFilter() {
        return sdkLanguageFilter;
    }

    @Override
    public FilterPanel<?> getDemoProjectsPlatformFilter() {
        return platformFilter;
    }
}
