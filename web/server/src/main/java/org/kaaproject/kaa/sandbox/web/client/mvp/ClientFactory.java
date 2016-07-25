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

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.ChangeKaaHostView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.FilterView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.HeaderView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.MainView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.ProjectBundleView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.ProjectView;

public interface ClientFactory {
    EventBus getEventBus();
    PlaceController getPlaceController();
    
    HeaderView getHeaderView();
    
    FilterView getFilterView();
    
    MainView getMainView();    
    
    ProjectView getProjectView();
    
    ChangeKaaHostView getChangeKaaHostView();

    ProjectBundleView getProjectsBundleView();
}
