/*
 * Copyright 2014-2016 CyberVision, Inc.
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

package org.kaaproject.kaa.sandbox.web.client.mvp.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import org.kaaproject.kaa.examples.common.projects.Bundle;
import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.sandbox.web.client.Sandbox;
import org.kaaproject.kaa.sandbox.web.client.mvp.ClientFactory;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEvent;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEventHandler;
import org.kaaproject.kaa.sandbox.web.client.mvp.place.BundlePlace;
import org.kaaproject.kaa.sandbox.web.client.mvp.place.MainPlace;
import org.kaaproject.kaa.sandbox.web.client.mvp.place.ProjectPlace;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.ProjectBundleView;
import org.kaaproject.kaa.sandbox.web.client.util.Analytics;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;
import org.kaaproject.kaa.sandbox.web.shared.dto.BundleData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BundleActivity extends AbstractActivity {

    private final ClientFactory clientFactory;
    private final BundlePlace place;
    private ProjectBundleView view;

    private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

    public BundleActivity(BundlePlace place, ClientFactory clientFactory) {
        this.place = place;
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        view = clientFactory.getProjectsBundleView();
        bind(eventBus);
        containerWidget.setWidget(view.asWidget());
    }

    @Override
    public void onStop() {
        for (HandlerRegistration registration : registrations) {
            registration.removeHandler();
        }
        registrations.clear();
    }

    private void bind(final EventBus eventBus) {

        view.reset();

        registrations.add(view.getProjectsActionSource().addProjectActionHandler(new ProjectActionEventHandler() {
            @Override
            public void onProjectAction(ProjectActionEvent event) {
                switch(event.getAction()) {
                    case OPEN_DETAILS:
                        ProjectPlace projectPlace = new ProjectPlace(event.getProject().getId());
                        projectPlace.setPreviousPlace(place);
                        clientFactory.getPlaceController().goTo(projectPlace);
                        break;
                    default:
                        break;
                }
            }
        }));

        registrations.add(view.getBackButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clientFactory.getPlaceController().goTo(new MainPlace());
            }
        }));

        fillView();
    }

    private void fillView() {

        Sandbox.getSandboxService().getProjectsBundleDataByBundleId(place.getBundleId(), new AsyncCallback<BundleData>() {
            @Override
            public void onFailure(Throwable throwable) {
                String message = Utils.getErrorMessage(throwable);
                view.setErrorMessage(message);
                Analytics.sendException(message);
            }

            @Override
            public void onSuccess(BundleData bundleData) {
                Bundle bundle = bundleData.getBundle();
                List<Project> projects = bundleData.getBundleProjects();

                if (bundle.getIconBase64() != null && bundle.getIconBase64().length() > 0) {
                    view.getBundleImage().setUrl("data:image/png;base64," + bundle.getIconBase64());
                } else {
                    view.getBundleImage().setResource(Utils.getPlatformIconBig(projects.get(0).getPlatform()));
                }
                view.getDescription().setText(bundle.getDescription());
                view.setBundleTitle(bundle.getName());
                view.getDetails().setHTML(bundle.getDetails());

                Analytics.switchBundleScreen(bundle);

                Set<Platform> platforms = new HashSet<>();
                Set<Feature> features = new HashSet<>();
//                Set<Complexity> complexities = new HashSet<Complexity>();
                for (Project project : projects) {
                    platforms.add(project.getPlatform());
                    for (Feature feature : project.getFeatures()) {
                        features.add(feature);
                    }
                }
                view.setPlatforms(new ArrayList<Platform>(platforms));
                view.setFeatures(new ArrayList<Feature>(features));
                view.setComplexity(projects.get(0).getComplexity());
                view.setProjects(projects);
            }
        });
    }
}
