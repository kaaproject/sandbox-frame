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

package org.kaaproject.kaa.sandbox.web.client.mvp;

import org.kaaproject.kaa.sandbox.web.client.mvp.view.ChangeKaaHostView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.FilterView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.HeaderView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.MainView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.ProjectBundleView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.ProjectView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.filter.FilterViewImpl;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.header.HeaderViewImpl;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.main.MainViewImpl;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.project.ProjectBundleViewImpl;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.project.ProjectViewImpl;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.settings.ChangeKaaHostViewImpl;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory {

    private final EventBus eventBus = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(eventBus);

    private final HeaderView headerView = new HeaderViewImpl();
    
    private final FilterView filterView = new FilterViewImpl();
    
    private final MainView mainView = new MainViewImpl();
    
    private final ProjectView projectView = new ProjectViewImpl();

    private final ProjectBundleView projectBundleView = new ProjectBundleViewImpl();

    private final ChangeKaaHostView changeKaaHostView = new ChangeKaaHostViewImpl();

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public HeaderView getHeaderView() {
        return headerView;
    }

    @Override
    public FilterView getFilterView() {
        return filterView;
    }

    @Override
    public MainView getMainView() {
        return mainView;
    }

    @Override
    public ProjectView getProjectView() {
        return projectView;
    }

    @Override
    public ChangeKaaHostView getChangeKaaHostView() {
        return changeKaaHostView;
    }

    @Override
    public ProjectBundleView getProjectsBundleView() {
        return projectBundleView;
    }
}