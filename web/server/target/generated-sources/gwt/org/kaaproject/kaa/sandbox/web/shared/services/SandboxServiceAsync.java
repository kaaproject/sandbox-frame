package org.kaaproject.kaa.sandbox.web.shared.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SandboxServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void getKaaNodeWebPort( AsyncCallback<java.lang.Integer> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void changeKaaHostEnabled( AsyncCallback<java.lang.Boolean> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void showChangeKaaHostDialog( AsyncCallback<java.lang.Boolean> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void changeKaaHostDialogShown( AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void validateKaaHost( java.lang.String host, AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void changeKaaHost( java.lang.String uuid, java.lang.String host, AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void getLogsEnabled( AsyncCallback<java.lang.Boolean> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void getLogsArchive( AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void changeKaaLogLevel( java.lang.String uuid, java.lang.String logLevel, java.lang.Boolean removeOldLogs, AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void getKaaCurrentHost( AsyncCallback<java.lang.String> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void getKaaCurrentLogLevel( AsyncCallback<org.kaaproject.kaa.sandbox.web.client.util.LogLevel> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void getAnalyticsInfo( AsyncCallback<org.kaaproject.kaa.sandbox.web.shared.dto.AnalyticsInfo> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void getFilterData( AsyncCallback<org.kaaproject.kaa.sandbox.web.shared.dto.FilterData> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void getDemoProjects( AsyncCallback<java.util.List<org.kaaproject.kaa.examples.common.projects.Project>> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void getDemoProjectsData( AsyncCallback<org.kaaproject.kaa.sandbox.web.shared.dto.ProjectsData> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void getProjectsBundleDataByBundleId( java.lang.String bundleId, AsyncCallback<org.kaaproject.kaa.sandbox.web.shared.dto.BundleData> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void getDemoProject( java.lang.String projectId, AsyncCallback<org.kaaproject.kaa.examples.common.projects.Project> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void checkProjectDataExists( java.lang.String projectId, org.kaaproject.kaa.sandbox.web.shared.dto.ProjectDataType dataType, AsyncCallback<java.lang.Boolean> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.kaaproject.kaa.sandbox.web.shared.services.SandboxService
     */
    void buildProjectData( java.lang.String uuid, org.kaaproject.kaa.sandbox.web.shared.dto.BuildOutputData outputData, java.lang.String projectId, org.kaaproject.kaa.sandbox.web.shared.dto.ProjectDataType dataType, AsyncCallback<Void> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static SandboxServiceAsync instance;

        public static final SandboxServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (SandboxServiceAsync) GWT.create( SandboxService.class );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instantiated
        }
    }
}
