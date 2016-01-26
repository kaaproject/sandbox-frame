/*
 * Copyright 2014 CyberVision, Inc.
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

package org.kaaproject.kaa.sandbox.web.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.TextResource;

public interface SandboxResources extends ClientBundle {

    public interface KaaTheme extends CssResource {
        
        String DEFAULT_CSS = "KaaTheme.css";

    }

    public interface SandboxStyle extends SandboxTheme {
        
        String DEFAULT_CSS = "SandboxTheme.css";

    }
    
    @NotStrict
    @Source(KaaTheme.DEFAULT_CSS)
    KaaTheme kaaTheme();
    
    @NotStrict
    @Source(SandboxStyle.DEFAULT_CSS)
    SandboxStyle sandboxStyle();
    
    @Source("js/resizeSupport.js")
    TextResource resizeSupportScript();
    
    @ImageOptions(width = 77, height = 50)
    @Source("images/kaa_logo.png")
    ImageResource kaaLogo();
    
    @ImageOptions(width = 20, height = 20)
    @Source("images/collapse.png")
    ImageResource collapse();

    @ImageOptions(width = 128, height = 128)
    @Source("images/android.png")
    ImageResource android();
    
    @ImageOptions(width = 128, height = 128)
    @Source("images/java.png")
    ImageResource java();

    @ImageOptions(width = 128, height = 128)
    @Source("images/c.png")
    ImageResource c();

    @ImageOptions(width = 114, height = 128)
    @Source("images/cpp.png")
    ImageResource cpp();

    @ImageOptions(width = 128, height = 128)
    @Source("images/objc.png")
    ImageResource objc();
    
    @ImageOptions(width = 32, height = 28)
    @Source("images/config_feature.png")
    ImageResource configFeature();
    
    @ImageOptions(width = 32, height = 28)
    @Source("images/profiling_feature.png")
    ImageResource profilingFeature();

    @ImageOptions(width = 32, height = 28)
    @Source("images/notification_feature.png")
    ImageResource notificationFeature();
    
    @ImageOptions(width = 32, height = 28)
    @Source("images/event_feature.png")
    ImageResource eventFeature();

    @ImageOptions(width = 32, height = 28)
    @Source("images/user_verifier_feature.png")
    ImageResource userVerifierFeature();

    @ImageOptions(width = 32, height = 28)
    @Source("images/data_collection_feature.png")
    ImageResource dataCollectionFeature();

    @ImageOptions(width = 28, height = 28)
    @Source("images/java_language.png")
    ImageResource javaLanguage();

    @ImageOptions(width = 24, height = 24)
    @Source("images/c_language.png")
    ImageResource cLanguage();

    @ImageOptions(width = 25, height = 28)
    @Source("images/cpp_language.png")
    ImageResource cppLanguage();

    @ImageOptions(width = 32, height = 32)
    @Source("images/objc_language.png")
    ImageResource objcLanguage();
    
    @ImageOptions(width = 28, height = 28)
    @Source("images/linux_platform.png")
    ImageResource linuxPlatform();

    @ImageOptions(width = 28, height = 28)
    @Source("images/windows_platform.png")
    ImageResource windowsPlatform();

    @ImageOptions(width = 28, height = 28)
    @Source("images/android_platform.png")
    ImageResource androidPlatform();

    @ImageOptions(width = 66, height = 28)
    @Source("images/ios_platform.png")
    ImageResource iosPlatform();

    @ImageOptions(width = 24, height = 24)
    @Source("images/android_platform.png")
    ImageResource androidPlatformFilter();
    
    @ImageOptions(width = 26, height = 28)
    @Source("images/artik5_platform.png")
    ImageResource artik5Platform();

    @ImageOptions(width = 29, height = 28)
    @Source("images/esp8266_platform.png")
    ImageResource esp8266Platform();

    @ImageOptions(width = 29, height = 21)
    @Source("images/cc32xx_platform.png")
    ImageResource cc32xxPlatform();
    
    @ImageOptions(width = 32, height = 32)
    @Source("images/multiple_platforms.png")
    ImageResource multiplePlatforms();
    
    @ImageOptions(width = 32, height = 32)
    @Source("images/multiple_languages.png")
    ImageResource multipleSdkLanguages();
    
    @ImageOptions(width = 30, height = 26)
    @Source("images/complexity_basic.png")
    ImageResource basic();
    
    @ImageOptions(width = 29, height = 26)
    @Source("images/complexity_regular.png")
    ImageResource regular();
    
    @ImageOptions(width = 29, height = 25)
    @Source("images/complexity_advanced.png")
    ImageResource advanced();
    
    @ImageOptions(width = 19, height = 18)
    @Source("images/complexity_basic_s.png")
    ImageResource basicStar();
    
    @ImageOptions(width = 19, height = 38)
    @Source("images/complexity_regular_s.png")
    ImageResource regularStar();
    
    @ImageOptions(width = 21, height = 56)
    @Source("images/complexity_advanced_s.png")
    ImageResource advancedStar();

}
