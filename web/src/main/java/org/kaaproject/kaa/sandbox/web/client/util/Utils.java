/*
 * Copyright 2014-2015 CyberVision, Inc.
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

package org.kaaproject.kaa.sandbox.web.client.util;

import org.kaaproject.avro.ui.gwt.client.AvroUiResources;
import org.kaaproject.avro.ui.gwt.client.AvroUiResources.AvroUiStyle;
import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.examples.common.projects.SdkLanguage;
import org.kaaproject.kaa.sandbox.web.client.SandboxResources;
import org.kaaproject.kaa.sandbox.web.client.SandboxResources.KaaTheme;
import org.kaaproject.kaa.sandbox.web.client.SandboxResources.SandboxStyle;
import org.kaaproject.kaa.sandbox.web.client.i18n.SandboxConstants;
import org.kaaproject.kaa.sandbox.web.client.i18n.SandboxMessages;
import org.kaaproject.kaa.sandbox.web.shared.services.SandboxServiceException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;

public class Utils {

    public static final SandboxResources resources = GWT.create(
            SandboxResources.class);

    public static final SandboxConstants constants = GWT.create(
            SandboxConstants.class);

    public static final SandboxMessages messages = GWT.create(
            SandboxMessages.class);
    
    public static final AvroUiResources avroUiResources = 
            GWT.create(AvroUiResources.class);
    
    public static final KaaTheme kaaTheme = 
            resources.kaaTheme();
    
    public static final SandboxStyle sandboxStyle = 
            resources.sandboxStyle();
    
    public static final AvroUiStyle avroUiStyle =
            avroUiResources.avroUiStyle();

    public static void injectSandboxStyles() {
        kaaTheme.ensureInjected();
        sandboxStyle.ensureInjected();
        avroUiStyle.ensureInjected();
    }

    public static String getErrorMessage(Throwable throwable) {
        if (throwable instanceof SandboxServiceException) {
            SandboxServiceException sandboxException = (SandboxServiceException)throwable;
            String message = constants.generalError();
            message += sandboxException.getMessage();
            return message;
        }
        else {
            return throwable.getMessage();
        }
    }
    
    public static String getFeatureText(Feature feature) {
        switch (feature) {
            case CONFIGURATION:
                return constants.configuration();
            case DATA_COLLECTION:
                return constants.dataCollection();
            case EVENT:
                return constants.event();
            case NOTIFICATION:
                return constants.notification();
            case PROFILING:
                return constants.profiling();
            case USER_VERIFIER:
                return constants.userVerifier();
        }
        return null;
    }
    
    public static ImageResource getFeatureIcon(Feature feature) {
        switch (feature) {
            case CONFIGURATION:
                return resources.configFeature();
            case DATA_COLLECTION:
                return resources.dataCollectionFeature();
            case EVENT:
                return resources.eventFeature();
            case NOTIFICATION:
                return resources.notificationFeature();
            case PROFILING:
                return resources.profilingFeature();
            case USER_VERIFIER:
                return resources.userVerifierFeature();
        }
        return null;
    }
    
    public static String getFeatureBackgroundClass(Feature feature) {
        switch (feature) {
        case CONFIGURATION:
            return sandboxStyle.bgFeatureConfig();
        case DATA_COLLECTION:
            return sandboxStyle.bgFeatureDataCollection();
        case EVENT:
            return sandboxStyle.bgFeatureEvent();
        case NOTIFICATION:
            return sandboxStyle.bgFeatureNotification();
        case PROFILING:
            return sandboxStyle.bgFeatureProfiling();
        case USER_VERIFIER:
            return sandboxStyle.bgFeatureUserVerifier();
    }
    return null;
    }
    
    public static String getSdkLanguageText(SdkLanguage sdkLanguage) {
    	switch(sdkLanguage) {
			case C:
				return constants.c();
			case CPP:
				return constants.cpp();
			case JAVA:
				return constants.java();
			case OBJC:
				return constants.objc();
    	}
    	return null;
    }
    
    public static String getPlatformText(Platform platform) {
        switch(platform) {
			case LINUX_X_86:
				return constants.linux_x86();
			case WINDOWS_X_86:
				return constants.windows_x86();
            case ANDROID:
                return constants.android();
			case IOS:
				return constants.ios();
            case ARTIK_5:
                return constants.artik5();
            case ESP_8266:
                return constants.esp8266();
            case CC_32_XX:
                return constants.cc32xx();
    		case STM_32:
    			return constants.stm32();
			case ECONAIS:
				return constants.econais();
        }
        return null;
    }
    
    public static ImageResource getSdkLanguageIcon(SdkLanguage sdkLanguage) {
    	switch(sdkLanguage) {
			case C:
				return resources.cLanguage();
			case CPP:
				return resources.cppLanguage();
			case JAVA:
				return resources.javaLanguage();
			case OBJC:
				//TODO:
				return resources.javaLanguage();
    	}
    	return null;
    }
    
    public static ImageResource getPlatformIcon(Platform platform) {
        switch(platform) {
			case LINUX_X_86:
    			//TODO:
    			return resources.androidPlatform();
			case WINDOWS_X_86:
    			//TODO:
    			return resources.androidPlatform();
            case ANDROID:
                return resources.androidPlatform();
    		case IOS:
    			//TODO:
    			return resources.androidPlatform();
            case ARTIK_5:
                return resources.artik5Platform();
            case ESP_8266:
                return resources.esp8266Platform();
            case CC_32_XX:
                return resources.cc32xxPlatform();
    		case STM_32:
    			//TODO:
    			return resources.androidPlatform();
			case ECONAIS:
    			//TODO:
    			return resources.androidPlatform();
        }
        return null;
    }
    
    public static ImageResource getFilterPlatformIcon(Platform platform) {
        switch(platform) {
            case ANDROID:
                return resources.androidPlatformFilter();
            default:
                return getPlatformIcon(platform);
        }
    }
    
    public static String getPlatformBackgroundClass(Platform platform) {
        return sandboxStyle.bgPlatformCommon();
    }
    
    public static ImageResource getProjectIconBig(Project project) {
        switch(project.getSdkLanguage()) {
            case C:
                return resources.c();
            case CPP:
                return resources.cpp();
            case JAVA:
            	if (project.getPlatforms().contains(Platform.ANDROID)) {
            		return resources.android();
            	} else {
            		return resources.java();
            	}
			case OBJC:
				//TODO:
				return resources.android();
        }
        return null;
    }
    
    public static ImageResource getFilterComplexityIcon(Complexity complexity) {
    	switch(complexity) {
        case BASIC:
            return resources.basic();
        case REGULAR:
            return resources.regular();
        case ADVANCED:
            return resources.advanced();
    	}
    	return null;
    }
    
    public static ImageResource getComplexityStarIcon(Complexity complexity) {
    	switch(complexity) {
        case BASIC:
            return resources.basicStar();
        case REGULAR:
            return resources.regularStar();
        case ADVANCED:
            return resources.advancedStar();
    	}
    	return null;
    }
    
    public static String getComplexityBackgroundClass(Complexity complexity) {
        return sandboxStyle.bgPlatformCommon();
    }
    
    public static String getComplexityText(Complexity complexity) {
        switch(complexity) {
            case BASIC:
                return constants.basic();
            case REGULAR:
                return constants.regular();
            case ADVANCED:
                return constants.advanced();
        }
        return null;
    }

}
