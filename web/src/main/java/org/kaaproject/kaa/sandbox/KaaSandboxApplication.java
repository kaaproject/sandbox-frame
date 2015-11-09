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

package org.kaaproject.kaa.sandbox;

import org.kaaproject.kaa.sandbox.service.initialization.InitializationService;
import org.kaaproject.kaa.server.common.AbstractServerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Main class that is used to launch Operations Server.
 */
public class KaaSandboxApplication extends AbstractServerApplication {
	
	private static final Logger LOG = LoggerFactory.getLogger(KaaSandboxApplication.class);

    private static final String[] DEFAULT_APPLICATION_CONTEXT_XMLS = new String[] { "sandboxContext.xml" };

    private static final String[] DEFAULT_APPLICATION_CONFIGURATION_FILES = new String[] {
            "sandbox-server.properties" };
    
    private InitializationService kaaSandboxInitializationService;

    /**
     * The main method. Used to launch Kaa Node.
     * 
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
        KaaSandboxApplication app = new KaaSandboxApplication(DEFAULT_APPLICATION_CONTEXT_XMLS,
                DEFAULT_APPLICATION_CONFIGURATION_FILES);        
        app.startAndWait(args);
    }

    public KaaSandboxApplication(String[] defaultContextFiles, String[] defaultConfigurationFiles) {
        super(defaultContextFiles, defaultConfigurationFiles);
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
            	LOG.info("{} application stopping...", getName());
            	if (kaaSandboxInitializationService != null) {
            		kaaSandboxInitializationService.stop();
            	}
            }
        });
    }

    @Override
    protected String getName() {
        return "Kaa Sandbox";
    }

    @Override
    protected void init(ApplicationContext ctx) {
    	kaaSandboxInitializationService = ctx.getBean("kaaSandboxInitializationService", InitializationService.class);
        kaaSandboxInitializationService.start();
    }
}
