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

package org.kaaproject.kaa.sandbox.web.client.mvp.view.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectAction;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEvent;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import java.util.HashSet;
import java.util.Set;

public class ProjectsBundleWidget extends AbstractDemoProjectWidget {

    private Set<Platform> platforms = new HashSet<>();
    private Set<Feature> features = new HashSet<>();

    public ProjectsBundleWidget() {
        super();

        HorizontalPanel buttonsPanel = new HorizontalPanel();
        buttonsPanel.setWidth("100%");
        buttonsPanel.setHeight("54px");
        add(buttonsPanel);

        detailsPanel.addHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (project != null) {
                    ProjectActionEvent action = new ProjectActionEvent(project,
                            ProjectAction.OPEN_BUNDLE_DETAILS);
                    fireEvent(action);
                }
            }
        }, ClickEvent.getType());
    }

    @Override
    public void setProject(Project project) {
        super.setProject(project);
        projectTitle.setText(project.getBundle());
        projectTitle.setTitle(project.getBundle());
        Platform platform = project.getPlatform();
        if (!platforms.contains(platform)) {
            platforms.add(platform);
            Image platformImage = new Image(Utils.getPlatformIcon(platform));
            platformImage.setTitle(Utils.getPlatformText(platform));
            platformPanel.add(platformImage);
        }
        for (Feature feature : project.getFeatures()) {
            if (!features.contains(feature)) {
                features.add(feature);
                Image image = new Image(Utils.getFeatureIcon(feature));
                image.setTitle(Utils.getFeatureText(feature));
                image.getElement().getStyle().setPaddingRight(4, Style.Unit.PX);
                featuresPanel.add(image);
            }
        }
    }
}
