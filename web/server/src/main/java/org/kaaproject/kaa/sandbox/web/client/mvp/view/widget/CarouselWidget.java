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

package org.kaaproject.kaa.sandbox.web.client.mvp.view.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.HasProjectActionEventHandlers;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEvent;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEventHandler;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class CarouselWidget extends FlexTable implements HasProjectActionEventHandlers,
        ProjectActionEventHandler {
	
	public static final int DEFAULT_VISIBLE_RANGE = 3;

    private final int initialVisibleRange;
    private int visibleRange;
    private int left;
    private int right;

    private Button goLeftButton;
    private Button goRightButton;

    private HorizontalPanel carousel;

    private List<HandlerRegistration> registrations = new ArrayList<>();
    private List<DemoProjectWidget> projectWidgets;
    
    public CarouselWidget() {
    	this(DEFAULT_VISIBLE_RANGE);
    }

    public CarouselWidget(int visibleRange) {
        super();
        
        this.initialVisibleRange = visibleRange;

        goLeftButton = new Button();
        goLeftButton.addStyleName(Utils.sandboxStyle.carouselLeftButton());
        goLeftButton.setHeight("100px");
        goLeftButton.setEnabled(false);
        goLeftButton.getElement().getStyle().setMarginRight(25, Style.Unit.PX);
        goLeftButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                moveLeft();
            }
        });

        goRightButton = new Button();
        goRightButton.addStyleName(Utils.sandboxStyle.carouselRightButton());
        goRightButton.setHeight("100px");
        goRightButton.setEnabled(false);
        goRightButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                moveRight();
            }
        });

        carousel = new HorizontalPanel();
        carousel.setWidth("100%");
        carousel.addStyleName(Utils.sandboxStyle.demoProjectsWidget());
        carousel.getElement().getStyle().setProperty("maxHeight", 320, Style.Unit.PX);
        carousel.getElement().getStyle().setOverflow(Overflow.HIDDEN);

        SimplePanel container = new SimplePanel();
        container.setWidth("860px");
        container.add(carousel);

        getColumnFormatter().setWidth(0, "20px");
        getColumnFormatter().setWidth(2, "20px");
        setWidget(0, 0, goLeftButton);
        setWidget(0, 1, carousel);
        getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
        setWidget(0, 2, goRightButton);
        
        reset();
    }

    public void setProjects(List<Project> projects) {
        loadProjects(projects);
    }

    void loadProjects(List<Project> projects) {
        reset();
        this.visibleRange = Math.min(this.initialVisibleRange, projects.size());
        this.right = this.visibleRange - 1;
        projectWidgets = new ArrayList<>();
        for (Project project : projects) {
            DemoProjectWidget demoProjectWidget = new DemoProjectWidget();
            demoProjectWidget.setProjects(null, project);
            registrations.add(demoProjectWidget.addProjectActionHandler(this));
            carousel.setVisible(true);
            carousel.add(demoProjectWidget);
            projectWidgets.add(demoProjectWidget);
        }
        for (int i = 0; i < visibleRange; i++) {
            projectWidgets.get(i).show(true);
        }
        updateButtons();
    }

    private void moveLeft() {
        if (left > 0) {
            projectWidgets.get(right--).hide(true);
            projectWidgets.get(--left).show(true);
        }
        updateButtons();
    }

    private void moveRight() {
        if (right < projectWidgets.size()-1) {
            projectWidgets.get(left++).hide(true);
            projectWidgets.get(++right).show(true);
        }
        updateButtons();
    }

    private void updateButtons() {
    	if (this.visibleRange == projectWidgets.size()) {
    		goLeftButton.setVisible(false);
    		goRightButton.setVisible(false);
    	} else {
    		goLeftButton.setVisible(true);
    		goRightButton.setVisible(true);
	        if (left == 0) {
	            goLeftButton.setEnabled(false);
	        } else {
	            goLeftButton.setEnabled(true);
	        }
	        if (right == projectWidgets.size()-1) {
	            goRightButton.setEnabled(false);
	        } else {
	            goRightButton.setEnabled(true);
	        }
    	}
    }

    public void reset() {
        this.left = 0;
        this.right = this.initialVisibleRange - 1;
        this.visibleRange = this.initialVisibleRange;
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
