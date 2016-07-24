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

package org.kaaproject.kaa.sandbox.web.client;

import org.kaaproject.kaa.sandbox.web.client.layout.AppLayout;
import org.kaaproject.kaa.sandbox.web.client.mvp.ClientFactory;
import org.kaaproject.kaa.sandbox.web.client.mvp.activity.HeaderActivityMapper;
import org.kaaproject.kaa.sandbox.web.client.mvp.activity.LeftPanelActivityMapper;
import org.kaaproject.kaa.sandbox.web.client.mvp.activity.SandboxActivityMapper;
import org.kaaproject.kaa.sandbox.web.client.mvp.place.MainPlace;
import org.kaaproject.kaa.sandbox.web.client.mvp.place.SandboxPlaceHistoryMapper;
import org.kaaproject.kaa.sandbox.web.client.util.Analytics;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;
import org.kaaproject.kaa.sandbox.web.shared.dto.AnalyticsInfo;
import org.kaaproject.kaa.sandbox.web.shared.services.SandboxServiceAsync;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.EventBus;

public class Sandbox implements EntryPoint {

    private static SandboxServiceAsync sandboxService = SandboxServiceAsync.Util.getInstance();

    private AppLayout appWidget = new AppLayout();

    @Override
    public void onModuleLoad() {
    	Utils.injectSandboxStyles();
    	sandboxService.getAnalyticsInfo(new AsyncCallback<AnalyticsInfo>() {
			@Override
			public void onSuccess(AnalyticsInfo info) {
				if (info.isEnableAnalytics()) {
					Analytics.initGA(info.getTrackingId(), info.getUserId());
				}
				init();
			}
			@Override
			public void onFailure(Throwable caught) {
				init();
			}
		});    	
    }
    
    public static SandboxServiceAsync getSandboxService() {
        return sandboxService;
    }

    private void init() {
    	ClientFactory clientFactory = GWT.create(ClientFactory.class);
        EventBus eventBus = clientFactory.getEventBus();

        PlaceController placeController = clientFactory.getPlaceController();

        ActivityMapper headerActivityMapper = new HeaderActivityMapper(clientFactory);
        ActivityManager headerActivityManager = new ActivityManager(headerActivityMapper, eventBus);
        headerActivityManager.setDisplay(appWidget.getAppHeaderHolder());
        
        ActivityMapper leftPanelActivityMapper = new LeftPanelActivityMapper(clientFactory);
        ActivityManager leftPanelActivityManager = new ActivityManager(leftPanelActivityMapper, eventBus);
        leftPanelActivityManager.setDisplay(appWidget.getLeftPanel());

        ActivityMapper appActivityMapper = new SandboxActivityMapper(clientFactory);
        ActivityManager appActivityManager = new ActivityManager(appActivityMapper, eventBus);
        appActivityManager.setDisplay(appWidget.getAppContentHolder());

        PlaceHistoryMapper historyMapper = GWT.create(SandboxPlaceHistoryMapper.class);

        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);

        Place place = new MainPlace();

        historyHandler.register(placeController, eventBus, place);

        RootLayoutPanel.get().add(appWidget);

        // Goes to the place represented on URL else default place
        historyHandler.handleCurrentHistory();
    }

    public static void redirectToModule(String module) {
        setWindowHref("/"+module);
    }
    
    public static void redirectToUrl(String url) {
        setWindowHref(url);
    }
    
    private static native void setWindowHref(String url) /*-{
        $wnd.location.href = url;
    }-*/; 
    
    public static native String getWindowHost() /*-{
    	return window.location.hostname;
    }-*/; 

}
