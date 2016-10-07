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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.PumpStreamHandler;
import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Post;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.atmosphere.gwt20.managed.AtmosphereMessageInterceptor;
import org.atmosphere.gwt20.server.GwtRpcInterceptor;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.IdleResourceInterceptor;
import org.atmosphere.interceptor.SuspendTrackerInterceptor;
import org.kaaproject.kaa.common.dto.file.FileData;
import org.kaaproject.kaa.examples.common.projects.Bundle;
import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.examples.common.projects.ProjectsConfig;
import org.kaaproject.kaa.examples.common.projects.SdkLanguage;
import org.kaaproject.kaa.sandbox.web.client.util.LogLevel;
import org.kaaproject.kaa.sandbox.web.services.cache.CacheService;
import org.kaaproject.kaa.sandbox.web.services.util.Utils;
import org.kaaproject.kaa.sandbox.web.shared.dto.AnalyticsInfo;
import org.kaaproject.kaa.sandbox.web.shared.dto.BuildOutputData;
import org.kaaproject.kaa.sandbox.web.shared.dto.BundleData;
import org.kaaproject.kaa.sandbox.web.shared.dto.FilterData;
import org.kaaproject.kaa.sandbox.web.shared.dto.ProjectDataKey;
import org.kaaproject.kaa.sandbox.web.shared.dto.ProjectDataType;
import org.kaaproject.kaa.sandbox.web.shared.dto.ProjectsData;
import org.kaaproject.kaa.sandbox.web.shared.services.SandboxService;
import org.kaaproject.kaa.sandbox.web.shared.services.SandboxServiceException;
import org.kaaproject.kaa.server.common.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

@Service("sandboxService")
@ManagedService(path = "/sandbox/atmosphere/rpc",
        interceptors = {
                /**
                 * Handle lifecycle for us
                 */
                AtmosphereResourceLifecycleInterceptor.class,
                /**
                 * Send to the client the size of the message to prevent serialization error.
                 */
                TrackMessageSizeInterceptor.class,
                /**
                 * Serialize/Deserialize GWT message for us
                 */
                GwtRpcInterceptor.class,
                /**
                 * Make sure our {@link AtmosphereResourceEventListener#onSuspend} is only called once for transport
                 * that reconnect on every requests.
                 */
                SuspendTrackerInterceptor.class,
                /**
                 * Deserialize the GWT message
                 */
                AtmosphereMessageInterceptor.class,
                /**
                 * Discard idle AtmosphereResource in case the network didn't advise us the client disconnected
                 */
                IdleResourceInterceptor.class
        })
public class SandboxServiceImpl implements SandboxService, InitializingBean {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SandboxServiceImpl.class);

    private static final String DEMO_PROJECTS_FOLDER = "demo_projects";

    private static final String DEMO_PROJECTS_XML_FILE = "demo_projects.xml";

    private static final String SANDBOX_ENV_FILE = "sandbox-env.properties";

    private static final String CHANGE_KAA_HOST_DIALOG_SHOWN_PROPERTY = "changeKaaHostDialogShown";

    private static final int MESSAGE_BROADCAST_TIMEOUT = 10000;

    private static final String ANALYTICS_USER_ID_FILE = "analytics_user_id";
    private static String[] sandboxEnv;
    private static AtmosphereResourceFactory atmosphereResourceFactory;
    private static String analyticsUserId;
    @Autowired
    private CacheService cacheService;
    /**
     * Change host enabled.
     */
    @Value("#{properties[gui_change_host_enabled]}")
    private boolean guiChangeHostEnabled;
    /**
     * Get sandbox logs enabled.
     */
    @Value("#{properties[gui_get_logs_enabled]}")
    private boolean guiGetLogsEnabled;
    @Value("#{properties[gui_show_change_host_dialog]}")
    private boolean guiShowChangeHostDialog;
    @Value("#{properties[kaa_node_web_port]}")
    private int kaaNodeWebPort;
    @Value("#{properties[enable_analytics]}")
    private boolean enableAnalytics;
    @Value("#{properties[analytics_tracking_id]}")
    private String analyticsTrackingId;
    private Map<String, Project> projectsMap = new HashMap<>();
    private Map<String, Bundle> bundlesMap = new HashMap<>();

    public List<Project> getDemoProjectsByBundleId(String bundleId) throws SandboxServiceException {
        List<Project> bundleProjects = new ArrayList<>();
        for (Project project : projectsMap.values()) {
            String bundle = project.getBundleId();
            if (bundle != null && !bundle.isEmpty() && bundle.equals(bundleId)) {
                bundleProjects.add(project);
            }
        }
        return bundleProjects;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {

            LOG.info("Initializing Sandbox Service...");
            LOG.info("sandboxHome [{}]", Environment.getServerHomeDir());
            LOG.info("guiChangeHostEnabled [{}]", guiChangeHostEnabled);
            LOG.info("kaaNodeWebPort [{}]", kaaNodeWebPort);
            LOG.info("enableAnalytics [{}]", enableAnalytics);

            prepareAnalytics();

            JAXBContext jc = JAXBContext.newInstance("org.kaaproject.kaa.examples.common.projects");
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            String demoProjectsXmlFile = Environment.getServerHomeDir() + "/" + DEMO_PROJECTS_FOLDER + "/" + DEMO_PROJECTS_XML_FILE;

            ProjectsConfig projectsConfig = (ProjectsConfig) unmarshaller.unmarshal(new File(demoProjectsXmlFile));
            for (Project project : projectsConfig.getProjects()) {
                projectsMap.put(project.getId(), project);
                LOG.info("Demo project: id [{}] name [{}]", project.getId(), project.getName());
            }
            for (Bundle bundle : projectsConfig.getBundles()) {
                bundlesMap.put(bundle.getId(), bundle);
                LOG.info("Demo projects bundle: id [{}] name [{}]", bundle.getId(), bundle.getName());
            }

            if (sandboxEnv == null) {
                Properties sandboxEnvProperties =
                        org.kaaproject.kaa.server.common.utils.FileUtils.readResourceProperties(SANDBOX_ENV_FILE);

                sandboxEnv = new String[sandboxEnvProperties.size()];
                int i = 0;
                for (Object key : sandboxEnvProperties.keySet()) {
                    String keyValue = key + "=" + sandboxEnvProperties.getProperty(key.toString());
                    sandboxEnv[i++] = keyValue;
                    LOG.info("Sandbox env: [{}]", keyValue);
                }
            }
            LOG.info("Initialized Sandbox Service.");
        } catch (JAXBException e) {
            LOG.error("Unable to initialize Sandbox Service", e);
            throw e;
        }
    }

    private void prepareAnalytics() throws IOException {
        if (enableAnalytics) {
            try {
                File f = new File(Environment.getServerHomeDir() + "/" + ANALYTICS_USER_ID_FILE);
                if (!f.exists()) {
                    f.createNewFile();
                }
                String uid = FileUtils.readFileToString(f, "UTF-8");
                if (uid == null || uid.isEmpty()) {
                    uid = UUID.randomUUID().toString();
                    FileUtils.write(f, uid);
                }
                analyticsUserId = uid;
            } catch (IOException e) {
                LOG.error("Unable to initialize analytics", e);
                throw e;
            }
        }
    }

    @Ready
    public void onReady(final AtmosphereResource r) {
        LOG.debug("Received RPC GET, uuid: {}", r.uuid());
        if (atmosphereResourceFactory == null) {
            atmosphereResourceFactory = r.getAtmosphereConfig().resourcesFactory();
        }
        r.getBroadcaster().broadcast(r.uuid(), r);
    }

    @Disconnect
    public void disconnected(AtmosphereResourceEvent event) {
        if (event.isCancelled()) {
            LOG.debug("User:" + event.getResource().uuid() + " unexpectedly disconnected");
        } else if (event.isClosedByClient()) {
            LOG.debug("User:" + event.getResource().uuid() + " closed the connection");
        }
    }

    @Post
    public void post(AtmosphereResource r) {
        LOG.debug("POST received with transport + " + r.transport());
    }

    @Override
    public boolean changeKaaHostEnabled() throws SandboxServiceException {
        return guiChangeHostEnabled;
    }

    @Override
    public boolean showChangeKaaHostDialog() throws SandboxServiceException {
        if (guiChangeHostEnabled && guiShowChangeHostDialog) {
            Boolean result = (Boolean) cacheService.getProperty(CHANGE_KAA_HOST_DIALOG_SHOWN_PROPERTY);
            return result == null || !result.booleanValue();
        } else {
            return false;
        }
    }

    @Override
    public void changeKaaHostDialogShown() throws SandboxServiceException {
        cacheService.putProperty(CHANGE_KAA_HOST_DIALOG_SHOWN_PROPERTY, Boolean.TRUE);
    }

    @Override
    public void validateKaaHost(String host) throws SandboxServiceException {
        if (host == null || host.length() == 0) {
            throw new SandboxServiceException("Kaa host field can not be empty!");
        } else if (!InetAddressValidator.getInstance().isValid(host) &&
                !DomainValidator.getInstance(true).isValid(host)) {
            throw new SandboxServiceException("Invalid hostname/ip address format!");
        }
    }

    @Override
    public void changeKaaHost(String uuid, String host) throws SandboxServiceException {
        try {
            ClientMessageOutputStream outStream = new ClientMessageOutputStream(uuid, null);
            if (guiChangeHostEnabled) {
                executeCommand(outStream, new String[]{"sudo", Environment.getServerHomeDir() + "/bin/change_kaa_host.sh", host}, null);
                try {
                    outStream.println("Going to flush all SDK caches...");
                    cacheService.flushAllCaches();
                    outStream.println("All caches successfuly flushed!");
                } catch (SandboxServiceException e) {
                    outStream.println("Failed to flush SDK caches due to unexpected error: " + e.getMessage());
                    throw e;
                }
            } else {
                outStream.println("WARNING: change host from GUI is disabled!");
            }
        } finally {
            if (uuid != null) {
                broadcastMessage(uuid, uuid + " finished");
            }
        }
    }

    @Override
    public boolean getLogsEnabled() throws SandboxServiceException {
        return guiGetLogsEnabled;
    }

    @Override
    public void getLogsArchive() throws SandboxServiceException {

        if (guiGetLogsEnabled) {
            executeCommand(null, new String[]{"sudo", Environment.getServerHomeDir() + "/bin/create_logs_archive.sh"}, null);
        } else {
            throw new SandboxServiceException("Get logs from GUI is disabled!");
        }
    }

    @Override
    public void changeKaaLogLevel(String uuid, String logLevel, Boolean removeOldLogs) throws SandboxServiceException {
        try {
            ClientMessageOutputStream outStream = new ClientMessageOutputStream(uuid, null);
            if (guiGetLogsEnabled) {
                String script = "/bin/change_kaa_log_level.sh";
                if (logLevel != null && !logLevel.isEmpty()) {
                    executeCommand(outStream, new String[]{"sudo", Environment.getServerHomeDir() + script, logLevel, removeOldLogs ? "1" : "0"}, null);
                } else {
                    throw new SandboxServiceException("Empty logLevel argument");
                }
            } else {
                outStream.println("WARNING: change log level from GUI is disabled!");
            }
        } finally {
            if (uuid != null) {
                broadcastMessage(uuid, uuid + " finished");
            }
        }
    }

    @Override
    public String getKaaCurrentHost() throws SandboxServiceException {
        try {
            String script = "/bin/get_kaa_current_host_ip.sh";
            return executeCommand("sudo", Environment.getServerHomeDir() + script);
        } catch (Exception e) {
            throw Utils.handleException(e);
        }
    }

    @Override
    public LogLevel getKaaCurrentLogLevel() throws SandboxServiceException {
        try {
            LogLevel currentLevel = null;
            String script = "/bin/get_kaa_current_log_level.sh";
            String logLevel = executeCommand("sudo", Environment.getServerHomeDir() + script);
            for (LogLevel level : LogLevel.values()) {
                if (logLevel.contains(level.toString())) {
                    currentLevel = level;
                }
            }
            return currentLevel;
        } catch (Exception e) {
            throw Utils.handleException(e);
        }
    }

    @Override
    public AnalyticsInfo getAnalyticsInfo() throws SandboxServiceException {
        AnalyticsInfo info = new AnalyticsInfo();
        info.setEnableAnalytics(enableAnalytics);
        if (enableAnalytics) {
            info.setTrackingId(analyticsTrackingId);
            info.setUserId(analyticsUserId);
        }
        return info;
    }

    @Override
    public FilterData getFilterData() throws SandboxServiceException {
        Set<Feature> availableFeaturesSet = new HashSet<>();
        Set<SdkLanguage> availableSdkLanguagesSet = new HashSet<>();
        Set<Platform> availablePlatformsSet = new HashSet<>();
        Set<Complexity> availableComplexitiesSet = new HashSet<>();

        for (Project project : projectsMap.values()) {
            availableFeaturesSet.addAll(project.getFeatures());
            availableSdkLanguagesSet.add(project.getSdkLanguage());
            availablePlatformsSet.addAll(project.getPlatforms());
            availableComplexitiesSet.add(project.getComplexity());
        }

        List<Feature> availableFeatures = new ArrayList<>(availableFeaturesSet);
        Collections.sort(availableFeatures);
        List<SdkLanguage> availableSdkLanguages = new ArrayList<>(availableSdkLanguagesSet);
        Collections.sort(availableSdkLanguages);
        List<Platform> availablePlatforms = new ArrayList<>(availablePlatformsSet);
        Collections.sort(availablePlatforms);
        List<Complexity> availableComplexities = new ArrayList<>(availableComplexitiesSet);
        Collections.sort(availableComplexities);

        return new FilterData(availableFeatures, availableSdkLanguages, availablePlatforms, availableComplexities);
    }

    @Override
    public List<Project> getDemoProjects() throws SandboxServiceException {
        return new ArrayList<Project>(projectsMap.values());
    }

    @Override
    public Project getDemoProject(String projectId)
            throws SandboxServiceException {
        return projectsMap.get(projectId);
    }

    @Override
    public ProjectsData getDemoProjectsData() throws SandboxServiceException {
        return new ProjectsData(projectsMap, bundlesMap);
    }

    @Override
    public BundleData getProjectsBundleDataByBundleId(String bundleId) throws SandboxServiceException {
        return new BundleData(bundlesMap.get(bundleId), getDemoProjectsByBundleId(bundleId));
    }

    @Override
    public int getKaaNodeWebPort() throws SandboxServiceException {
        return kaaNodeWebPort;
    }

    @Override
    public boolean checkProjectDataExists(String projectId,
                                          ProjectDataType dataType) throws SandboxServiceException {
        ProjectDataKey dataKey = new ProjectDataKey(projectId, dataType);
        FileData data = cacheService.getProjectFile(dataKey);
        return data != null;
    }

    @Override
    public void buildProjectData(String uuid, BuildOutputData outputData, String projectId, ProjectDataType dataType) throws SandboxServiceException {
        PrintStream outPrint = null;
        ClientMessageOutputStream outStream = null;
        ByteArrayOutputStream byteOutStream = null;

        if (outputData != null) {
            byteOutStream = new ByteArrayOutputStream();
            outPrint = new PrintStream(byteOutStream);
        }

        buildCheckedData(uuid, outputData, projectId, dataType, outPrint, outStream, byteOutStream);
    }

    private void buildCheckedData(String uuid, BuildOutputData outputData, String projectId, ProjectDataType dataType,
                                  PrintStream outPrint, ClientMessageOutputStream outStream, ByteArrayOutputStream byteOutStream)
            throws SandboxServiceException {

        try {
            outStream = new ClientMessageOutputStream(uuid, outPrint);
            Project project = projectsMap.get(projectId);

            if (project != null) {
                buildProject(projectId, dataType, outStream, project);
            } else {
                outStream.println("No project configuration found!");
            }

        } catch (Exception e) {
            if (outStream != null) {
                outStream.println("Unexpected error occurred: " + e.getMessage());
            }
            throw Utils.handleException(e);
        } finally {
            if (uuid != null) {
                broadcastMessage(uuid, uuid + " finished");
            }
            if (outPrint != null) {
                outPrint.flush();
                outPrint.close();
                outputData.setOutputData(byteOutStream.toByteArray());
            }
        }
    }

    private void buildProject(String projectId, ProjectDataType dataType, ClientMessageOutputStream outStream, Project project)
            throws SandboxServiceException, IOException {

        String sdkProfileId = project.getSdkProfileId();
        outStream.println("SDK profile id of project: " + sdkProfileId);
        outStream.println("Getting SDK for requested project...");
        FileData sdkFileData = cacheService.getSdk(project.getId());

        if (sdkFileData != null) {
            outStream.println("Successfully got SDK.");
            buildSdk(projectId, dataType, outStream, project, sdkFileData);
        } else {
            outStream.println("Unable to get/create SDK for requested project!");
        }
    }

    private void buildSdk(String projectId, ProjectDataType dataType, ClientMessageOutputStream outStream,
                          Project project, FileData sdkFileData) throws IOException, SandboxServiceException {

        File rootDir = createTempDirectory("demo-project");
        try {
            outStream.println("Processing project archive...");
            String sourceArchiveFile = Environment.getServerHomeDir() + "/" + DEMO_PROJECTS_FOLDER + "/" + project.getSourceArchive();
            String rootProjectDir = rootDir.getAbsolutePath();

            executeCommand(outStream, new String[]{"tar", "-C", rootProjectDir, "-xzvf", sourceArchiveFile}, null);

            File sdkFile = new File(rootProjectDir + "/" + project.getSdkLibDir() + "/" + sdkFileData.getFileName());
            FileOutputStream fos = FileUtils.openOutputStream(sdkFile);
            fos.write(sdkFileData.getFileData());
            fos.flush();
            fos.close();

            ProjectDataKey dataKey = new ProjectDataKey(projectId, dataType);
            if (dataType == ProjectDataType.SOURCE) {
                buildSdkSource(outStream, rootDir, sourceArchiveFile, rootProjectDir, dataKey);
            } else {
                buildBinary(outStream, project, rootDir, dataKey);
            }
        } finally {
            FileUtils.deleteDirectory(rootDir);
        }
    }

    private void buildBinary(ClientMessageOutputStream outStream, Project project, File rootDir, ProjectDataKey dataKey)
            throws SandboxServiceException, IOException {

        outStream.println("Building binary file...");
        File projectFolder = rootDir;
        if (project.getProjectFolder() != null && !project.getProjectFolder().trim().isEmpty()) {
            projectFolder = new File(rootDir, project.getProjectFolder());
        }

        // Build binary file with ant or gradle (with gradle goes only android projects now)
        if (project.getPlatforms().contains(Platform.ANDROID) && new File(projectFolder, "/gradlew").exists()) {
            LOG.info("Build with gradle.");
            executeCommand(outStream, new String[]{"chmod", "+x",  "gradlew"}, projectFolder);
            executeCommand(outStream, new String[]{"./gradlew", "clean", "assembleDebug"}, projectFolder);
        } else {
            LOG.info("Build with ant.");
            executeCommand(outStream, new String[]{"ant"}, projectFolder);
        }
        outStream.println("Build finished.");

        File binaryFile = new File(rootDir, project.getDestBinaryFile());
        byte[] binaryFileBytes = FileUtils.readFileToByteArray(binaryFile);
        FileData binaryFileData = new FileData();

        String binaryFileName = FilenameUtils.getName(binaryFile.getAbsolutePath());

        binaryFileData.setFileName(binaryFileName);
        binaryFileData.setFileData(binaryFileBytes);
        if (project.getSdkLanguage() == SdkLanguage.JAVA) {
            if (project.getPlatforms().contains(Platform.ANDROID)) {
                binaryFileData.setContentType("application/vnd.android.package-archive");
            } else {
                binaryFileData.setContentType("application/java-archive");
            }
        }
        cacheService.putProjectFile(dataKey, binaryFileData);
    }

    private void buildSdkSource(ClientMessageOutputStream outStream, File rootDir, String sourceArchiveFile, String rootProjectDir, ProjectDataKey dataKey) throws SandboxServiceException, IOException {
        String sourceArchiveName = FilenameUtils.getName(sourceArchiveFile);
        outStream.println("Compressing source project archive...");

        File sourceFile = new File(rootDir, sourceArchiveName);

        String[] files = rootDir.list();
        String[] command = (String[]) ArrayUtils.addAll(new String[]{"tar", "-czvf", sourceFile.getAbsolutePath(), "-C", rootProjectDir}, files);

        executeCommand(outStream, command, null);

        outStream.println("Source project archive compressed.");
        byte[] sourceFileBytes = FileUtils.readFileToByteArray(sourceFile);
        FileData sourceFileData = new FileData();
        sourceFileData.setFileName(sourceArchiveName);
        sourceFileData.setFileData(sourceFileBytes);
        sourceFileData.setContentType("application/x-compressed");
        cacheService.putProjectFile(dataKey, sourceFileData);
    }

    private static void executeCommand(ClientMessageOutputStream outStream,
                                       String[] command,
                                       File workingDir) throws SandboxServiceException {
        try {
            Execute exec = null;
            if (outStream != null) {
                exec = new Execute(new PumpStreamHandler(outStream));
            } else {
                exec = new Execute();
            }
            exec.setEnvironment(sandboxEnv);
            if (workingDir != null) {
                exec.setWorkingDirectory(workingDir);
            }
            exec.setCommandline(command);
            exec.execute();
            if (exec.isFailure()) {
                throw new SandboxServiceException("Process returned bad exit value: " + exec.getExitValue());
            }
        } catch (Exception e) {
            throw Utils.handleException(e);
        }
    }

    private static String executeCommand(String... command) throws SandboxServiceException {

        StringBuilder output = new StringBuilder();
        Process process;

        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
        } catch (Exception ex) {
            throw Utils.handleException(ex);
        }
        return output.toString();
    }

    private static File createTempDirectory(String prefix) throws IOException {
        final File temp = File.createTempFile(prefix, Long.toString(System.nanoTime()));
        if (!temp.delete()) {
            throw new IOException("Could not delete temp file: "
                    + temp.getAbsolutePath());
        }
        if (!temp.mkdir()) {
            throw new IOException("Could not create temp directory: "
                    + temp.getAbsolutePath());
        }
        return temp;
    }

    private static void broadcastMessage(String uuid, Object message) {
        if (uuid != null) {
            AtmosphereResource res = null;
            int waitTime = 0;
            while ((res = atmosphereResourceFactory.find(uuid)) == null
                    && waitTime < MESSAGE_BROADCAST_TIMEOUT) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                waitTime += 500;
            }
            if (res == null) {
                LOG.warn("Unable to find atmosphere resource for uuid: {}", uuid);
            } else {
                res.getBroadcaster().broadcast(message, res);
            }
        }
    }

    class ClientMessageOutputStream extends OutputStream {

        private String resourceUuid;
        private PrintStream out;

        ClientMessageOutputStream(String resourceUuid, PrintStream out) {
            this.resourceUuid = resourceUuid;
            this.out = out;
        }

        @Override
        public void write(int b) throws IOException {
        }

        @Override
        public void write(byte b[], int off, int len) throws IOException {
            byte[] data = new byte[len];
            System.arraycopy(b, off, data, 0, len);
            String message = new String(data);
            if (resourceUuid != null) {
                broadcastMessage(resourceUuid, message);
            }
            if (out != null) {
                out.print(message);
            }
        }

        public void println(String text) {
            if (resourceUuid != null) {
                broadcastMessage(resourceUuid, (text + "\n"));
            }
            if (out != null) {
                out.println(text);
            }
        }

    }

}
