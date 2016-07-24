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

import org.kaaproject.kaa.sandbox.web.client.mvp.ClientFactory;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class HeaderActivityMapper implements ActivityMapper {

    private final ClientFactory clientFactory;
    private HeaderActivity headerActivity;

    public HeaderActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
    	if (headerActivity == null) {
    		headerActivity = new HeaderActivity(place, clientFactory);
    	} else {
    		headerActivity.setPlace(place);
    	}
    	return headerActivity;
    }
}

