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

package org.kaaproject.kaa.sandbox.web.client.mvp.activity;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import java.util.ArrayList;
import java.util.List;

import org.kaaproject.avro.ui.gwt.client.util.BusyAsyncCallback;
import org.kaaproject.kaa.sandbox.web.client.Sandbox;
import org.kaaproject.kaa.sandbox.web.client.i18n.SandboxConstants;
import org.kaaproject.kaa.sandbox.web.client.mvp.ClientFactory;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectFilterEvent;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectFilterEventHandler;
import org.kaaproject.kaa.sandbox.web.client.mvp.place.MainPlace;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.FilterView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.widget.FilterPanel;
import org.kaaproject.kaa.sandbox.web.client.util.Analytics;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;
import org.kaaproject.kaa.sandbox.web.shared.dto.FilterData;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LeftPanelActivity extends AbstractActivity {

    private final ClientFactory clientFactory;
    private final FilterView filterView;
    
    protected List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

    public LeftPanelActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.filterView = clientFactory.getFilterView();
    }
    
    @Override
    public void start(AcceptsOneWidget containerWidget, final EventBus eventBus) {

        registrations.add(filterView.getDemoProjectsFeatureFilter().addValueChangeHandler(
                new AnalyticValueChangeHandler(Utils.constants.featuresFilter(),filterView.getDemoProjectsFeatureFilter())));

        registrations.add(filterView.getDemoProjectsSdkLanguageFilter().addValueChangeHandler(
                new AnalyticValueChangeHandler(Utils.constants.sdkLanguagesFilter(),filterView.getDemoProjectsSdkLanguageFilter())));

        registrations.add(filterView.getDemoProjectsPlatformFilter().addValueChangeHandler(
                new AnalyticValueChangeHandler(Utils.constants.platformsFilter(),filterView.getDemoProjectsPlatformFilter())));

        registrations.add(filterView.getComplexitiesFilter().addValueChangeHandler(
                new AnalyticValueChangeHandler(Utils.constants.complexity(),filterView.getComplexitiesFilter())
        ));


        registrations.add(filterView.addProjectFilterHandler(new ProjectFilterEventHandler() {
            @Override
            public void onProjectFilter(ProjectFilterEvent event) {
                clientFactory.getPlaceController().goTo(new MainPlace());
                eventBus.fireEvent(event);
            }
        }));
        this.filterView.reset();
        Sandbox.getSandboxService().getFilterData(new BusyAsyncCallback<FilterData>() {
            
            @Override
            public void onSuccessImpl(FilterData result) {
                filterView.setFilterData(result);
            }
            
            @Override
            public void onFailureImpl(Throwable caught) {
                String message = Utils.getErrorMessage(caught);
                Analytics.sendException(message);
            }
        });
        containerWidget.setWidget(filterView.asWidget());
    }
    
    @Override
    public void onStop() {
        for (HandlerRegistration registration : registrations) {
            registration.removeHandler();
        }
        registrations.clear();
    }
    
    public void setPlace(Place place) {
    	filterView.setActive(place != null && place instanceof MainPlace);
    }

    private static class AnalyticValueChangeHandler implements ValueChangeHandler<Boolean>{

        private final String eventCategory;
        private final FilterPanel<?> filterPanel;

        public AnalyticValueChangeHandler(String eventCategory, FilterPanel<?> filterPanel) {
            this.eventCategory = eventCategory;
            this.filterPanel = filterPanel;
        }
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> valueChangeEvent) {
            for (FilterPanel<?>.FilterItem<?> widgets : filterPanel.getFilterItems()) {
                if(widgets.getValue()){
                    if(widgets.isSendAnalytic()) {
                        Analytics.sendEvent(eventCategory, Analytics.CLICK, widgets.getFilterEntity().toString());
                        widgets.setSendAnalytic(false);
                    }
                }
            }
        }
    }

}
