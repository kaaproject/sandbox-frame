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

package org.kaaproject.kaa.sandbox.web.services;

import org.kaaproject.kaa.common.dto.admin.SdkPlatform;
import org.kaaproject.kaa.common.dto.file.FileData;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.examples.common.projects.SdkLanguage;
import org.kaaproject.kaa.sandbox.web.services.cache.CacheService;
import org.kaaproject.kaa.sandbox.web.services.rest.AdminClientProvider;
import org.kaaproject.kaa.sandbox.web.services.util.Utils;
import org.kaaproject.kaa.sandbox.web.shared.dto.ProjectDataKey;
import org.kaaproject.kaa.sandbox.web.shared.services.SandboxService;
import org.kaaproject.kaa.sandbox.web.shared.services.SandboxServiceException;
import org.kaaproject.kaa.server.common.admin.AdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class CacheServiceImpl implements CacheService {

    private static final Logger LOG = LoggerFactory.getLogger(CacheServiceImpl.class);

    private static final String SDK_CACHE = "sdkCache";
    private static final String FILE_CACHE = "fileCache";
    private static final String PROPS_CACHE = "propsCache";

    @Autowired
    private AdminClientProvider clientProvider;
    
    @Autowired
    private SandboxService sandboxService;

    /**
     * The thrift host.
     */
    @Value("#{properties[tenant_developer_user]}")
    private String tenantDeveloperUser;

    /**
     * The thrift port.
     */
    @Value("#{properties[tenant_developer_password]}")
    private String tenantDeveloperPassword;

    @Override
    @Cacheable(value = SDK_CACHE, key = "#projectId")
    public FileData getSdk(String projectId) throws SandboxServiceException {
    	Project project = sandboxService.getDemoProject(projectId);
        AdminClient client = clientProvider.getClient();
        client.login(tenantDeveloperUser, tenantDeveloperPassword);
        FileData fileData;
        try {
            fileData = client.downloadSdk(project.getSdkProfileId(), getSdkPlatform(project));
        } catch (Exception e) {
            throw Utils.handleException(e);
        }
        return fileData;
    }

    @Override
    @Cacheable(value = FILE_CACHE, key = "#key", unless = "#result == null")
    public FileData getProjectFile(ProjectDataKey key) {
        return null;
    }

    @Override
    @CachePut(value = FILE_CACHE, key = "#key")
    public FileData putProjectFile(ProjectDataKey key, FileData data) {
        return data;
    }

    @Override
    @Cacheable(value = PROPS_CACHE, key = "#propertyKey", unless = "#result == null")
    public Object getProperty(String propertyKey) {
        return null;
    }

    @Override
    @CachePut(value = PROPS_CACHE, key = "#propertyKey")
    public Object putProperty(String propertyKey, Object propertyValue) {
        return propertyValue;
    }


    @Override
    @Caching(evict = {
            @CacheEvict(value = SDK_CACHE, allEntries = true),
            @CacheEvict(value = FILE_CACHE, allEntries = true)
    })
    public void flushAllCaches() throws SandboxServiceException {
        final AdminClient client = clientProvider.getClient();
        client.login(tenantDeveloperUser, tenantDeveloperPassword);
        try {
            retryRestCall(new RestCall() {
                @Override
                public void executeRestCall() throws Exception {
                    client.flushSdkCache();
                }
            }, 3, 5000, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw Utils.handleException(e);
        }
        LOG.info("All caches have been completely flushed.");
    }

    private static SdkPlatform getSdkPlatform(Project project) {
        switch (project.getSdkLanguage()) {
            case C:
                return SdkPlatform.C;
            case CPP:
                return SdkPlatform.CPP;
            case JAVA:
            	if (project.getPlatforms().contains(Platform.ANDROID)) {
            		return SdkPlatform.ANDROID;
            	} else {
            		return SdkPlatform.JAVA;
            	}
            case OBJC:
                return SdkPlatform.OBJC;
            default:
                throw new IllegalArgumentException("Unsupported language " + project.getSdkLanguage());
        }
    }

    private void retryRestCall(RestCall restCall, int maxRetries,
                               long retryInterval, HttpStatus... acceptedErrorStatuses) throws SandboxServiceException {
        int retryCount = 0;
        while (retryCount++ < maxRetries) {
            try {
                restCall.executeRestCall();
                return;
            } catch (HttpClientErrorException httpClientError) {
                boolean accepted = false;
                for (HttpStatus acceptedStatus : acceptedErrorStatuses) {
                    if (httpClientError.getStatusCode() == acceptedStatus) {
                        accepted = true;
                        break;
                    }
                }
                if (accepted) {
                    try {
                        Thread.sleep(retryInterval);
                    } catch (InterruptedException e) {
                    }
                } else {
                    throw Utils.handleException(httpClientError);
                }
            } catch (Exception e) {
                throw Utils.handleException(e);
            }
        }
    }

    private interface RestCall {

        void executeRestCall() throws Exception;

    }
}
