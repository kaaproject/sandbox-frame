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

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.HasProjectActionEventHandlers;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectAction;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEvent;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.ProjectActionEventHandler;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

public class AbstractDemoProjectWidget extends VerticalPanel implements
        HasProjectActionEventHandlers {

    protected Image applicationImage;
    protected Image complexityImage;
    protected HorizontalPanel platformPanel;
    protected HorizontalPanel featuresPanel;
    protected Anchor projectTitle;

    protected VerticalPanel detailsPanel;

    protected Project project;

    private ProjectWidgetAnimation projectWidgetAnimation;

    public AbstractDemoProjectWidget() {
        super();

        addStyleName(Utils.sandboxStyle.demoProjectWidget());
        setVisible(false);

        projectWidgetAnimation = new ProjectWidgetAnimation(this, 190, 10.0);

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

        add(detailsPanel);

        HorizontalPanel iconsPanel = new HorizontalPanel();
        iconsPanel.setWidth("100%");
        iconsPanel.addStyleName(Utils.sandboxStyle.detailsInnerCenter());
        iconsPanel.getElement().getStyle().setPaddingTop(10, Unit.PX);
        platformPanel = new HorizontalPanel();
        iconsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        iconsPanel.add(platformPanel);
        
        featuresPanel = new HorizontalPanel();
        iconsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        iconsPanel.add(featuresPanel);
        
        add(iconsPanel);
    }

    public void setProject(Project project) {
        this.project = project;
        if (project.getIconBase64() != null
                && project.getIconBase64().length() > 0) {
            applicationImage.setUrl("data:image/png;base64,"
                    + project.getIconBase64());
        } else {
            applicationImage.setResource(Utils.getPlatformIconBig(project
                    .getPlatform()));
        }
        complexityImage.setResource(Utils.getComplexityStarIcon(project.getComplexity()));
    }

    public Project getProject() {
        return project;
    }

    @Override
    public HandlerRegistration addProjectActionHandler(ProjectActionEventHandler handler) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractDemoProjectWidget widgets = (AbstractDemoProjectWidget) o;

        if (applicationImage != null ? !applicationImage.equals(widgets.applicationImage) : widgets.applicationImage != null)
            return false;
        if (complexityImage != null ? !complexityImage.equals(widgets.complexityImage) : widgets.complexityImage != null)
            return false;
        if (platformPanel != null ? !platformPanel.equals(widgets.platformPanel) : widgets.platformPanel != null)
            return false;
        if (featuresPanel != null ? !featuresPanel.equals(widgets.featuresPanel) : widgets.featuresPanel != null)
            return false;
        if (projectTitle != null ? !projectTitle.equals(widgets.projectTitle) : widgets.projectTitle != null)
            return false;
        if (detailsPanel != null ? !detailsPanel.equals(widgets.detailsPanel) : widgets.detailsPanel != null)
            return false;
        return !(project != null ? !project.equals(widgets.project) : widgets.project != null);

    }

    @Override
    public int hashCode() {
        int result = applicationImage != null ? applicationImage.hashCode() : 0;
        result = 31 * result + (complexityImage != null ? complexityImage.hashCode() : 0);
        result = 31 * result + (platformPanel != null ? platformPanel.hashCode() : 0);
        result = 31 * result + (featuresPanel != null ? featuresPanel.hashCode() : 0);
        result = 31 * result + (projectTitle != null ? projectTitle.hashCode() : 0);
        result = 31 * result + (detailsPanel != null ? detailsPanel.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        return result;
    }
}
