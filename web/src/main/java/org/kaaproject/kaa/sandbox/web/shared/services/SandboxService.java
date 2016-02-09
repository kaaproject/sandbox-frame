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

package org.kaaproject.kaa.sandbox.web.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.sandbox.web.client.util.LogLevel;
import org.kaaproject.kaa.sandbox.web.shared.dto.AnalyticsInfo;
import org.kaaproject.kaa.sandbox.web.shared.dto.BuildOutputData;
import org.kaaproject.kaa.sandbox.web.shared.dto.BundleData;
import org.kaaproject.kaa.sandbox.web.shared.dto.FilterData;
import org.kaaproject.kaa.sandbox.web.shared.dto.ProjectDataType;
import org.kaaproject.kaa.sandbox.web.shared.dto.ProjectsData;

import java.util.List;

@RemoteServiceRelativePath("springGwtServices/sandboxService")
public interface SandboxService extends RemoteService {

    public int getKaaNodeWebPort() throws SandboxServiceException;

    public boolean changeKaaHostEnabled() throws SandboxServiceException;

    public boolean showChangeKaaHostDialog() throws SandboxServiceException;

    public void changeKaaHostDialogShown() throws SandboxServiceException;

    public void changeKaaHost(String uuid, String host) throws SandboxServiceException;

    public boolean getLogsEnabled() throws SandboxServiceException;

    public void getLogsArchive() throws SandboxServiceException;

    public void changeKaaLogLevel(String uuid, String logLevel, Boolean removeOldLogs) throws SandboxServiceException;

    public String getKaaCurrentHost() throws SandboxServiceException;

    public LogLevel getKaaCurrentLogLevel() throws SandboxServiceException;

    public AnalyticsInfo getAnalyticsInfo() throws SandboxServiceException;
    
    public FilterData getFilterData() throws SandboxServiceException; 

    public List<Project> getDemoProjects() throws SandboxServiceException;

    public ProjectsData getDemoProjectsData() throws SandboxServiceException;

    public BundleData getProjectsBundleDataByBundleId(String bundleId) throws SandboxServiceException;

    public Project getDemoProject(String projectId) throws SandboxServiceException;

    public boolean checkProjectDataExists(String projectId, ProjectDataType dataType) throws SandboxServiceException;

    public void buildProjectData(String uuid, BuildOutputData outputData, String projectId, ProjectDataType dataType) throws SandboxServiceException;
}
