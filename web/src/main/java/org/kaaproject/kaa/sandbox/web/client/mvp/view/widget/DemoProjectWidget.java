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

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.kaaproject.kaa.examples.common.projects.Bundle;
import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.examples.common.projects.SdkLanguage;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.FilterableItem;
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

public class DemoProjectWidget extends AbsolutePanel implements HasProjectActionEventHandlers {

    private VerticalPanel detailsPanel;
    private Image applicationImage;
    private Image complexityImage;
    
    private VerticalPanel bottomPanel;
    private HorizontalPanel platformPanel;
    private HorizontalPanel featuresPanel;

    private Anchor projectTitle;
    private Button getSourceButton;
    private Button getBinaryButton;

    private Project project;
    private FilterableItem filterItem;
    
    
    
    private ProjectWidgetAnimation projectWidgetAnimation;

    public DemoProjectWidget() {
        super();

        addStyleName(Utils.sandboxStyle.demoProjectWidget());
        setVisible(false);
        projectWidgetAnimation = new ProjectWidgetAnimation(this, 230, 10.0);

        SimplePanel backPanel = new SimplePanel();
        backPanel.addStyleName(Utils.sandboxStyle.projectCard());
        add(backPanel, 20, 20);        
        backPanel = new SimplePanel();
        backPanel.addStyleName(Utils.sandboxStyle.projectCard());
        add(backPanel, 10, 10);        
        backPanel = new SimplePanel();
        backPanel.addStyleName(Utils.sandboxStyle.projectCard());
        add(backPanel, 0, 0);
        
        getWidget(0).setVisible(false);
        getWidget(1).setVisible(false);
        
        VerticalPanel rootPanel = new VerticalPanel();
        rootPanel.setHeight("100%");
        backPanel.add(rootPanel);

        detailsPanel = new VerticalPanel();
        detailsPanel.addStyleName(Utils.sandboxStyle.details());
        detailsPanel.sinkEvents(Event.ONCLICK);

        detailsPanel.setWidth("100%");

        AbsolutePanel layoutPanel = new AbsolutePanel();

        VerticalPanel platformImagePanel = new VerticalPanel();
        platformImagePanel.addStyleName(Utils.sandboxStyle.detailsInnerTop());
        platformImagePanel.setWidth("100%");
        applicationImage = new Image();
        complexityImage = new Image();
        applicationImage.getElement().getStyle().setHeight(128, Unit.PX);
        platformImagePanel
                .setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        
        platformImagePanel.add(applicationImage);

        layoutPanel.add(platformImagePanel);
        layoutPanel.add(complexityImage, 3, 3);
        SimplePanel platformImageHoverPanel = new SimplePanel();
        platformImageHoverPanel.addStyleName(Utils.sandboxStyle
                .platformImageHover());
        layoutPanel.add(platformImageHoverPanel);
        platformImageHoverPanel.setSize("100%", "100%");
        layoutPanel.setSize("100%", "100%");

        detailsPanel.add(layoutPanel);
        VerticalPanel titlePanel = new VerticalPanel();
        titlePanel.addStyleName(Utils.sandboxStyle.detailsInnerCenter());
        titlePanel.addStyleName(Utils.sandboxStyle.titlePanel());
        projectTitle = new Anchor();
        projectTitle.addStyleName(Utils.sandboxStyle.title());
        titlePanel.add(projectTitle);
        titlePanel.setCellVerticalAlignment(projectTitle, HasVerticalAlignment.ALIGN_MIDDLE);

        detailsPanel.add(titlePanel);

        rootPanel.add(detailsPanel);
        
        bottomPanel = new VerticalPanel();
        bottomPanel.setWidth("100%");

        HorizontalPanel iconsPanel = new HorizontalPanel();
        iconsPanel.setWidth("100%");
        iconsPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        iconsPanel.addStyleName(Utils.sandboxStyle.detailsInnerCenter());
        iconsPanel.getElement().getStyle().setPaddingTop(10, Unit.PX);
        platformPanel = new HorizontalPanel();
        platformPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        iconsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        iconsPanel.add(platformPanel);
        
        featuresPanel = new HorizontalPanel();
        featuresPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        iconsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        iconsPanel.add(featuresPanel);
        
        bottomPanel.add(iconsPanel);
        
        rootPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        rootPanel.add(bottomPanel);
        rootPanel.setCellHeight(bottomPanel, "100%");
    }

    public void setProjects(Bundle bundle, Project... projects) {
        getWidget(0).setVisible(bundle != null);
        getWidget(1).setVisible(bundle != null);    	
        constructButtons(bundle != null);

        Set<SdkLanguage> sdkLanguages = new HashSet<>();
        Set<Platform> platforms = new HashSet<>();
        Set<Feature> features = new HashSet<>();
        for (Project project : projects) {
        	sdkLanguages.add(project.getSdkLanguage());
            platforms.addAll(project.getPlatforms());
            features.addAll(project.getFeatures());
        }
        
        Image sdkLanguageImage;
        if (sdkLanguages.size() > 1) {
        	sdkLanguageImage = new Image(Utils.resources.multipleSdkLanguages());
        	sdkLanguageImage.setTitle(Utils.constants.multipleSdkLanguages());
        } else {
        	SdkLanguage sdkLanguage = sdkLanguages.iterator().next();
        	sdkLanguageImage = new Image(Utils.getSdkLanguageIcon(sdkLanguage));
        	sdkLanguageImage.setTitle(Utils.getSdkLanguageText(sdkLanguage));
        }
        sdkLanguageImage.getElement().getStyle().setPaddingRight(4, Unit.PX);
        platformPanel.add(sdkLanguageImage);
        
        Image platformImage;
        if (platforms.size() > 1) {
        	platformImage = new Image(Utils.resources.multiplePlatforms());
        	platformImage.setTitle(Utils.constants.multiplePlatforms());
        } else {
        	Platform platform = platforms.iterator().next();
        	platformImage = new Image(Utils.getPlatformIcon(platform));
            platformImage.setTitle(Utils.getPlatformText(platform));
        }
        platformPanel.add(platformImage);
        
        Iterator<Feature> featureIterator = features.iterator();
        while (featureIterator.hasNext()) {
            Feature feature = featureIterator.next();
            Image image = new Image(Utils.getFeatureIcon(feature));
            image.setTitle(Utils.getFeatureText(feature));
            image.getElement().getStyle().setPaddingRight(4, Unit.PX);
            featuresPanel.add(image);
        }

        project = projects[0];

        Complexity complexity = null;
        if (bundle == null) {
            if (project.getIconBase64() != null && project.getIconBase64().length() > 0) {
                applicationImage.setUrl("data:image/png;base64," + project.getIconBase64());
            } else {
                applicationImage.setResource(Utils.getProjectIconBig(project));
            }
            complexity = project.getComplexity();
            complexityImage.setResource(Utils.getComplexityStarIcon(complexity));
            projectTitle.setText(project.getName());
            projectTitle.setTitle(project.getName());

            getBinaryButton.setVisible(project.getDestBinaryFile() != null &&
                    project.getDestBinaryFile().length() > 0);
        } else {
            Set<Complexity> bundleComplexities = new HashSet<>();
            for (Project project : projects) {
                bundleComplexities.add(project.getComplexity());
            }
            if (bundle.getIconBase64() != null && bundle.getIconBase64().length() > 0) {
                applicationImage.setUrl("data:image/png;base64," + bundle.getIconBase64());
            } else {
                applicationImage.setResource(Utils.getProjectIconBig(project));
            }
            complexity = Collections.max(bundleComplexities);
            complexityImage.setResource(Utils.getComplexityStarIcon(complexity));
            projectTitle.setText(bundle.getName());
            projectTitle.setTitle(bundle.getName());
        }
        filterItem = new FilterableItem(sdkLanguages, platforms, features, complexity);
    }

    private void constructButtons(final boolean isBundle) {

        if (!isBundle) {
        	HorizontalPanel buttonsPanel = new HorizontalPanel();
            buttonsPanel.setWidth("100%");
            buttonsPanel.addStyleName(Utils.sandboxStyle.detailsInnerBottom());
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
            bottomPanel.add(buttonsPanel);

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
        
        detailsPanel.addHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (project != null) {
                    ProjectActionEvent action = new ProjectActionEvent(project,
                    		isBundle ? ProjectAction.OPEN_BUNDLE_DETAILS : ProjectAction.OPEN_DETAILS);
                    fireEvent(action);
                }
            }
        }, ClickEvent.getType());
    }

    public FilterableItem getFilterItem() {
        return filterItem;
    }

    @Override
    public HandlerRegistration addProjectActionHandler(
            ProjectActionEventHandler handler) {
        return this.addHandler(handler, ProjectActionEvent.getType());
    }

    public void show(boolean animate) {
        projectWidgetAnimation.show(animate);
    }

    public void hide(boolean animate) {
        projectWidgetAnimation.hide(animate);
    }

    static class ProjectWidgetAnimation extends Animation {
        
        private static final int ANIMATION_DURATION = 300;

        private Widget widget;

        private double opacityIncrement;
        private double targetOpacity;
        private double baseOpacity;

        private double marginIncrement;
        private double targetMargin;
        private double baseMargin;

        private int width;
        private double rightMargin;

        private boolean show;

        public ProjectWidgetAnimation(Widget widget, int width,
                double rightMargin) {
            this.widget = widget;
            this.show = widget.isVisible();
            this.width = width;
            this.rightMargin = rightMargin;
        }

        @Override
        protected void onUpdate(double progress) {
            widget.getElement().getStyle()
                    .setOpacity(baseOpacity + progress * opacityIncrement);
            widget.getElement()
                    .getStyle()
                    .setMarginRight(baseMargin + progress * marginIncrement,
                            Unit.PX);
        }

        @Override
        protected void onComplete() {
            super.onComplete();
            widget.getElement().getStyle().setOpacity(targetOpacity);
            widget.getElement().getStyle()
                    .setMarginRight(targetMargin, Unit.PX);
            if (!show) {
                widget.setVisible(false);
            }
        }

        public void show(boolean animate) {
            if (!show) {
                show = true;
                widget.setVisible(true);
                animate(0.0, 1.0, -width, rightMargin, animate ? ANIMATION_DURATION : 0);
            }
        }

        public void hide(boolean animate) {
            if (show) {
                show = false;
                animate(1.0, 0.0, rightMargin, -width, animate ? ANIMATION_DURATION : 0);
            }
        }

        private void animate(double baseOpacity, double targetOpacity, double baseMargin,
                double targetMargin, int duration) {
            this.baseOpacity = baseOpacity;
            this.targetOpacity = targetOpacity;
            this.baseMargin = baseMargin;
            this.targetMargin = targetMargin;
            widget.getElement().getStyle().setOpacity(this.baseOpacity);
            widget.getElement().getStyle()
                    .setMarginRight(this.baseMargin, Unit.PX);
            this.opacityIncrement = this.targetOpacity - this.baseOpacity;
            this.marginIncrement = this.targetMargin - this.baseMargin;
            if (duration > 0) {
                run(duration);
            } else {
                onComplete();
            }
        }

    }

}
