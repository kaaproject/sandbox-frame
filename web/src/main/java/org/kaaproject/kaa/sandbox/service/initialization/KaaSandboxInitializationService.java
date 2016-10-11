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

package org.kaaproject.kaa.sandbox.service.initialization;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RedirectPatternRule;
import org.eclipse.jetty.webapp.WebAppContext;
import org.kaaproject.kaa.server.common.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KaaSandboxInitializationService implements InitializationService {
	
	private static final Logger LOG = LoggerFactory.getLogger(KaaSandboxInitializationService.class);
	
    private int webPort;

    private Server server;
    private WebAppContext sandboxWebAppContext;
    private WebAppContext avroUiSandboxWebAppContext;
    
    private Handler getRedirectRoot2SandboxHandler() {
        RewriteHandler rewrite = new RewriteHandler();
        rewrite.setRewriteRequestURI(true);
        rewrite.setRewritePathInfo(true);
        
        String[] redirectArray = {"", "/"};
        for (String redirect : redirectArray) {
            RedirectPatternRule rule = new RedirectPatternRule();
            rule.setTerminating(true);
            rule.setPattern(redirect);
            rule.setLocation("/sandbox");
            rewrite.addRule(rule);
        }
        
        return rewrite;
    }
    
    public void setWebPort(int webPort) {
    	this.webPort = webPort;
    }
    
	@Override
	public void start() {
        LOG.info("Starting Kaa Sandbox Web Server...");
        String webappsDir = Environment.getServerHomeDir() + "/webapps";
        LOG.info("Webapps dir [{}]", webappsDir);
        
        server = new Server(webPort);
        HandlerCollection handlers = new HandlerCollection();
        
        sandboxWebAppContext = new WebAppContext();
        sandboxWebAppContext.setContextPath("/sandbox");
        sandboxWebAppContext.setDescriptor(webappsDir + "/sandbox/WEB-INF/web.xml");
        sandboxWebAppContext.setResourceBase(webappsDir + "/sandbox");
        
        avroUiSandboxWebAppContext = new WebAppContext();
        avroUiSandboxWebAppContext.setContextPath("/avroUiSandbox");
        avroUiSandboxWebAppContext.setDescriptor(webappsDir + "/avroUiSandbox/WEB-INF/web.xml");        
        avroUiSandboxWebAppContext.setResourceBase(webappsDir + "/avroUiSandbox");
        
        handlers.addHandler(sandboxWebAppContext);
        handlers.addHandler(avroUiSandboxWebAppContext);
        handlers.addHandler(getRedirectRoot2SandboxHandler());
        
        server.setHandler(handlers);
        
        try {
            server.start();
            LOG.info("Kaa Sandbox Web Server started.");
        } catch (Exception e) {
            LOG.error("Error starting Kaa Sandbox Web Server!", e);
        }
        try {
			server.join();
		} catch (InterruptedException e) {
			LOG.error("Sandbox Web Server Interrupted!", e);
		}
	}

	@Override
	public void stop() {
        try {
            LOG.info("Stopping Kaa Sandbox Web Server...");
            server.stop();
            sandboxWebAppContext.destroy();
            avroUiSandboxWebAppContext.destroy();
            LOG.info("Kaa Sandbox Web Server stopped.");
        } catch (Exception e) {
            LOG.error("Error stopping Kaa Sandbox Web Server!", e);
        }
	}
	
}
