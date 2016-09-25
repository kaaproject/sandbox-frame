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

package org.kaaproject.kaa.sandbox.web.client.util;

import org.kaaproject.kaa.examples.common.projects.Bundle;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.sandbox.web.shared.Version;

public class Analytics {
	
	public static final String MAIN_SCREEN = "Main";
	public static final String CHANGE_KAA_HOST_SCREEN = "Change Kaa host/IP";
	
	public static final String SOURCE_ACTION = "Source";
	public static final String BINARY_ACTION = "Binary";
	
	public static final String GOTO_ADMIN_UI_ACTION = "Goto Admin UI";
	public static final String GOTO_AVRO_UI_SANDBOX_ACTION = "Goto Avro UI Sandbox";
	public static final String GOTO_KAA_PROJECT_SITE_ACTION = "Goto Kaa project site";
	
	public static final String CHANGE_KAA_HOST_ACTION = "Change Kaa host/IP";
	public static final String GET_LOGS_ACTION = "Get sandbox logs";

	public static final String CLICK= "Click";



	private static boolean inited = false;
	
	public static void initGA(String trackingId, String userId) {
		initGaImpl();
		setGaVars(trackingId, userId, Version.PROJECT_VERSION);
		inited = true;
	}
	
	private static native void initGaImpl() /*-{
		$wnd['GoogleAnalyticsObject']='ga';
		$wnd['ga']=$wnd['ga']||function() {
			($wnd['ga'].q=$wnd['ga'].q||[]).push(arguments)
		}; 
		$wnd['ga'].l=1*new Date();
		var a = $wnd.document.createElement('script');
		var m = $wnd.document.getElementsByTagName('script')[0];
		a.async=1;
		a.src='//www.google-analytics.com/analytics.js';
		m.parentNode.insertBefore(a,m);
	}-*/;
	
	private static native void setGaVars(String trackingId, String userId, String version) /*-{
		$wnd.ga('create', trackingId, { 'userId': userId });
		$wnd.ga('set', 'appName', 'Sandbox');
		$wnd.ga('set', 'appVersion', version);
	}-*/;
	
	private static String currentScreen;
	
	public static void switchBundleScreen(Bundle bundle) {
		if (inited) {
			currentScreen = bundle.getName();
			switchScreenImpl(currentScreen);
		}
	}

	public static void switchProjectScreen(Project project) {
		if (inited) {
			currentScreen = project.getName() + " - " + Utils.getPlatformText(project.getPlatforms().get(0));
			switchScreenImpl(currentScreen);
		}
	}

	public static void switchScreen(String screenName) {
		if (inited) {
			currentScreen = screenName;
			switchScreenImpl(screenName);
		}
	}
	

	public static void sendProjectEvent(Project project, String action) {
		if (inited) {
			String category = project.getName() + " - " + Utils.getPlatformText(project.getPlatforms().get(0));
			sendEventImpl(category, action);
		}
	}
	
	public static void sendEvent(String action) {
		if (inited) {
			sendEventImpl(currentScreen, action);
		}
	}
	
	public static void sendEvent(String action, String label) {
		if (inited) {
			sendEventImpl(currentScreen, action, label);
		}
	}

	public static void sendEvent(String category, String action, String label) {
		if (inited) {
			sendEventImpl(category, action, label);
		}
	}
	
	public static void sendException(String description) {
		if (inited) {
			sendExceptionImpl(description);
		}
	}
	
	public static void sendException(String description, boolean fatal) {
		if (inited) {
			sendExceptionImpl(description, fatal);
		}
	}
	
	private static native void switchScreenImpl(String screenName) /*-{
		$wnd.ga('set', 'screenName', screenName);
		$wnd.ga('send', 'screenview');
	}-*/;
	
	private static native void sendEventImpl(String category, String action) /*-{
		$wnd.ga('send', 'event', category, action);
	}-*/;
	
	private static native void sendEventImpl(String category, String action, String label) /*-{
		$wnd.ga('send', 'event', category, action, label);
	}-*/;
	
	private static native void sendExceptionImpl(String description) /*-{
		$wnd.ga('send', 'exception', {'exDescription': description, 'exFatal': false});
	}-*/;
	
	private static native void sendExceptionImpl(String description, boolean fatal) /*-{
		$wnd.ga('send', 'exception', {'exDescription': description, 'exFatal': fatal});
	}-*/;
}
