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

package org.kaaproject.kaa.sandbox.web.client.mvp.view.main;

import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.HasProjectActionEventHandlers;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectFilter;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.MainView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.base.BaseViewImpl;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.widget.DemoProjectsWidget;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.ScrollPanel;

import org.kaaproject.kaa.sandbox.web.shared.dto.ProjectsData;

public class MainViewImpl extends BaseViewImpl implements MainView {

    protected static final int DEFAULT_TEXTBOX_SIZE = 255;

    private DemoProjectsWidget demoProjectsView;
    
    public MainViewImpl() {
        super(false);
        setBackEnabled(false);
    }

    @Override
    protected String getViewTitle() {
        return "";
    }
 
    @Override
    protected void initCenterPanel() {
        ScrollPanel centerScroll = new ScrollPanel();
        centerScroll.setWidth("100%");
        centerPanel.setWidget(centerScroll);        
        demoProjectsView = new DemoProjectsWidget();
        centerScroll.add(demoProjectsView);
        Element scrollContainer = demoProjectsView.getElement().getParentElement();
        scrollContainer.getStyle().setPaddingTop(15, Unit.PX);
        scrollContainer.getStyle().setPaddingBottom(15, Unit.PX);
        scrollContainer.getStyle().setPaddingLeft(30, Unit.PX);
        scrollContainer.getStyle().setPaddingRight(30, Unit.PX);
    }

    @Override
    protected void resetImpl() {
        demoProjectsView.reset();
    }

    @Override
    public void setProjects(ProjectsData projects) {
        demoProjectsView.setProjects(projects);
    }

    @Override
    public HasProjectActionEventHandlers getProjectsActionSource() {
        return demoProjectsView;
    }

    @Override
    public void updateProjectFilter(ProjectFilter filter) {
        demoProjectsView.updateFilter(filter);
    }
}
