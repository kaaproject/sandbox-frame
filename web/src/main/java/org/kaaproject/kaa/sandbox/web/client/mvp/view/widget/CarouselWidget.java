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

package org.kaaproject.kaa.sandbox.web.client.mvp.view.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.HasProjectActionEventHandlers;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEvent;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEventHandler;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class CarouselWidget extends FlexTable implements HasProjectActionEventHandlers,
        ProjectActionEventHandler {

    private final int visibleRange = 5;
    private int left;
    private int right = visibleRange - 1;

    private FlowPanel carousel;

    private List<HandlerRegistration> registrations = new ArrayList<>();
    private List<DemoProjectWidget> projectWidgets;

    public CarouselWidget() {
        super();
//        setWidth("100%");

        Button left = new Button("");
        left.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                moveLeft();
            }
        });

        Button right = new Button("");
        right.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                moveRight();
            }
        });

        carousel = new FlowPanel();
        carousel.setWidth("100%");
        carousel.addStyleName(Utils.sandboxStyle.demoProjectsWidget());
        carousel.getElement().getStyle().setProperty("maxHeight", 320, Style.Unit.PX);

        setWidth("1200px");
        getColumnFormatter().setWidth(0, "20px");
        getColumnFormatter().setWidth(2, "20px");
        setWidget(0, 0, left);
        setWidget(0, 1, carousel);
        getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
        setWidget(0, 2, right);
    }

    public void setProjects(List<Project> projects) {
        List<Project> l = new ArrayList<>();
        l.addAll(projects);
        l.addAll(projects);
        loadProjects(l);
    }

    void loadProjects(List<Project> projects) {
        reset();
        projectWidgets = new ArrayList<>();
        for (Project project : projects) {
            DemoProjectWidget demoProjectWidget = new DemoProjectWidget();
            demoProjectWidget.setProjects(null, project);
            registrations.add(demoProjectWidget.addProjectActionHandler(this));
            carousel.setVisible(true);
            carousel.add(demoProjectWidget);
            projectWidgets.add(demoProjectWidget);
        }
        updateProjects(true);
    }

    private void updateProjects(boolean animate) {
        for (int i = 0; i < visibleRange; i++) {
            projectWidgets.get(i).show(animate);
        }
    }

    private void moveLeft() {
        if (left > 0) {
            projectWidgets.get(right--).hide(true);
            projectWidgets.get(--left).show(true);
        }
    }

    private void moveRight() {
        if (right < projectWidgets.size()-1) {
            projectWidgets.get(left++).hide(true);
            projectWidgets.get(++right).show(true);
        }
    }

    public void reset() {
        for (HandlerRegistration registration : registrations) {
            registration.removeHandler();
        }
        registrations.clear();
        carousel.clear();
    }

    public HasProjectActionEventHandlers getProjectsActionSource() {
        return this;
    }

    @Override
    public HandlerRegistration addProjectActionHandler(ProjectActionEventHandler handler) {
        return this.addHandler(handler, ProjectActionEvent.getType());
    }

    @Override
    public void onProjectAction(ProjectActionEvent event) {
        fireEvent(event);
    }
}
