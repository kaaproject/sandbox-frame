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

import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.HasProjectActionEventHandlers;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectAction;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEvent;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEventHandler;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DemoProjectWidget extends AbstractDemoProjectWidget {

    private Button getSourceButton;
    private Button getBinaryButton;

    public DemoProjectWidget() {
        super();

        HorizontalPanel buttonsPanel = new HorizontalPanel();
        buttonsPanel.setWidth("100%");
        buttonsPanel.addStyleName(Utils.sandboxStyle.detailsInnerBottom());
        getSourceButton = new Button(Utils.constants.getSourceCode());
        getSourceButton.addStyleName(Utils.sandboxStyle.action());
        getSourceButton.getElement().getStyle().setMarginRight(20, Unit.PX);
        getBinaryButton = new Button(Utils.constants.getBinary());
        getBinaryButton.addStyleName(Utils.sandboxStyle.action());
        buttonsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        buttonsPanel.add(getSourceButton);
        buttonsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        buttonsPanel.add(getBinaryButton);
        add(buttonsPanel);

        detailsPanel.addHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (project != null) {
                    ProjectActionEvent action = new ProjectActionEvent(project,
                                                    ProjectAction.OPEN_DETAILS);
                    fireEvent(action);
                }
            }
        }, ClickEvent.getType());

        getSourceButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (project != null) {
                    ProjectActionEvent action = new ProjectActionEvent(project,
                                                    ProjectAction.GET_SOURCE_CODE);
                    fireEvent(action);
                }
            }
        });

        getBinaryButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (project != null) {
                    ProjectActionEvent action = new ProjectActionEvent(project,
                                                    ProjectAction.GET_BINARY);
                    fireEvent(action);
                }
            }
        });
    }

    @Override
    public void setProject(Project project) {
        super.setProject(project);
        projectTitle.setText(project.getName());
        projectTitle.setTitle(project.getName());
        Image platformImage = new Image(Utils.getPlatformIcon(project.getPlatform()));
        platformImage.setTitle(Utils.getPlatformText(project.getPlatform()));
        platformPanel.add(platformImage);
        for (Feature feature : project.getFeatures()) {
            Image image = new Image(Utils.getFeatureIcon(feature));
            image.setTitle(Utils.getFeatureText(feature));
            image.getElement().getStyle().setPaddingRight(4, Unit.PX);
            featuresPanel.add(image);
        }
        getBinaryButton.setVisible(project.getDestBinaryFile() != null &&
                project.getDestBinaryFile().length() > 0);
    }

    public Project getProject() {
        return project;
    }

    @Override
    public HandlerRegistration addProjectActionHandler(
            ProjectActionEventHandler handler) {
        return this.addHandler(handler, ProjectActionEvent.getType());
    }

}
