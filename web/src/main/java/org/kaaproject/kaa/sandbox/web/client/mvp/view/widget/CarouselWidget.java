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

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.HasProjectActionEventHandlers;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEvent;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEventHandler;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class CarouselWidget extends FlowPanel implements HasProjectActionEventHandlers,
        ProjectActionEventHandler {

    private final int visibleRange = 3;
    private int left;
    private int right = visibleRange - 1;

    private List<HandlerRegistration> registrations = new ArrayList<>();
    private List<AbstractDemoProjectWidget> projectWidgets;

    public CarouselWidget() {
        super();
        setWidth("100%");

        addStyleName(Utils.sandboxStyle.demoProjectsWidget());
    }

    public void setProjects(List<Project> projects) {
        loadProjects(projects);
    }

    void loadProjects(List<Project> projects) {
        reset();
        projectWidgets = new ArrayList<>();
        for (Project project : projects) {
            AbstractDemoProjectWidget demoProjectWidget = new DemoProjectWidget();
            demoProjectWidget.setProject(project);
            registrations.add(demoProjectWidget.addProjectActionHandler(this));
            setVisible(true);
            add(demoProjectWidget);
            projectWidgets.add(demoProjectWidget);
        }
        updateProjects(true);
    }

    void updateProjects(boolean animate) {
        for (int i = 0; i < visibleRange; i++) {
            projectWidgets.get(i).show(animate);
        }
    }

//    TODO: fix this later
    public void moveLeft() {
        if (left != 0) {
            projectWidgets.get(right--).hide(true);
            projectWidgets.get(--left).show(true);
        }
    }

    public void moveRight() {
        if (right != projectWidgets.size()-1) {
            projectWidgets.get(left++).hide(true);
            projectWidgets.get(++right).show(true);
        }
    }

    @Override
    public HandlerRegistration addProjectActionHandler(ProjectActionEventHandler handler) {
        return this.addHandler(handler, ProjectActionEvent.getType());
    }

    @Override
    public void onProjectAction(ProjectActionEvent event) {
        fireEvent(event);
    }

    public void reset() {
        for (HandlerRegistration registration : registrations) {
            registration.removeHandler();
        }
        registrations.clear();
        clear();
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
}
