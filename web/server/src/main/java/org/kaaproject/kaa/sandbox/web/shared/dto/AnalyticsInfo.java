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

package org.kaaproject.kaa.sandbox.web.shared.dto;

import java.io.Serializable;

public class AnalyticsInfo implements Serializable {

	private static final long serialVersionUID = 5587013032121262342L;
	
	private boolean enableAnalytics;
	private String trackingId;
	private String userId;
	
	public AnalyticsInfo () {
	}

	public AnalyticsInfo(boolean enableAnalytics, String trackingId,
			String userId) {
		super();
		this.enableAnalytics = enableAnalytics;
		this.trackingId = trackingId;
		this.userId = userId;
	}

	public boolean isEnableAnalytics() {
		return enableAnalytics;
	}

	public void setEnableAnalytics(boolean enableAnalytics) {
		this.enableAnalytics = enableAnalytics;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
