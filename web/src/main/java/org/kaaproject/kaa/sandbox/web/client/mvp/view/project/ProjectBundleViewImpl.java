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

package org.kaaproject.kaa.sandbox.web.client.mvp.view.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.HasProjectActionEventHandlers;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.ProjectBundleView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.base.BaseViewImpl;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.widget.CarouselWidget;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import java.util.List;

public class ProjectBundleViewImpl extends BaseViewImpl implements ProjectBundleView {

    interface ProjectBundleViewImplBinder extends UiBinder<Widget, ProjectBundleViewImpl> { }
    private static ProjectBundleViewImplBinder uiBinder = GWT.create(ProjectBundleViewImplBinder.class);

    @UiField public CaptionPanel  carouselPanel;

    private CarouselWidget carousel;

    private Label projectTitleLabel;
    private Image applicationImage;
    private Label descriptionLabel;
    private HTML projectDetailsPanel;
    private VerticalPanel targetPlatformPanel;
    private VerticalPanel featuresPanel;
    private HorizontalPanel complexityPanel;

    public ProjectBundleViewImpl() {
        super(true);
        setBackEnabled(false);
    }

    @Override
    protected Widget createAndBindUi() {
        return uiBinder.createAndBindUi(this);
    }

    @Override
    protected String getViewTitle() {
        return null;
    }

    @Override
    protected void initCenterPanel() {

        FlexTable flexTable = new FlexTable();

        flexTable.getColumnFormatter().setWidth(0, "60px");
        flexTable.getColumnFormatter().setWidth(1, "400px");
        flexTable.getColumnFormatter().setWidth(2, "660px");

        backButton.addStyleName(Utils.sandboxStyle.appBackButton());
        backButton.getElement().getStyle().setHeight(180, Style.Unit.PX);
        setBackButton(backButton);

        flexTable.setWidget(0, 0, backButton);
        flexTable.getFlexCellFormatter().setRowSpan(0, 0, 3);
        flexTable.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);

        projectTitleLabel = new Label();
        projectTitleLabel.addStyleName(Utils.sandboxStyle.contentTitleLabel());
        projectTitleLabel.getElement().getStyle().setPaddingBottom(15, Style.Unit.PX);

        flexTable.setWidget(0, 1, projectTitleLabel);
        flexTable.getFlexCellFormatter().setColSpan(0, 1, 2);
        flexTable.getFlexCellFormatter().setHeight(0, 1, "51px");

        VerticalPanel iconAndButtons = new VerticalPanel();
        iconAndButtons.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        applicationImage = new Image();
        iconAndButtons.add(applicationImage);
        iconAndButtons.setCellHorizontalAlignment(applicationImage, HasHorizontalAlignment.ALIGN_CENTER);

        FlexTable appFeaturesPanel = new FlexTable();
        appFeaturesPanel.getColumnFormatter().setWidth(0, "110px");
        appFeaturesPanel.getColumnFormatter().setWidth(1, "110px");

        Label platformLabel = new Label(Utils.constants.platform() + "s");
        platformLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appFeaturesPanel.setWidget(1, 0, platformLabel);
        appFeaturesPanel.getFlexCellFormatter().getElement(1, 0).getStyle().setPaddingTop(15, Style.Unit.PX);
        appFeaturesPanel.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);

        targetPlatformPanel = new VerticalPanel();
        appFeaturesPanel.setWidget(1, 1, targetPlatformPanel);
        appFeaturesPanel.getFlexCellFormatter().getElement(1, 1).getStyle().setPaddingTop(15, Style.Unit.PX);
        appFeaturesPanel.getFlexCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_MIDDLE);

        Label featuresLabel = new Label(Utils.constants.features());
        featuresLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appFeaturesPanel.setWidget(2, 0, featuresLabel);
        appFeaturesPanel.getFlexCellFormatter().getElement(2, 0).getStyle().setPaddingTop(10, Style.Unit.PX);
        appFeaturesPanel.getFlexCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);

        featuresPanel = new VerticalPanel();
        appFeaturesPanel.setWidget(2, 1, featuresPanel);
        appFeaturesPanel.getFlexCellFormatter().getElement(2, 1).getStyle().setPaddingTop(10, Style.Unit.PX);
        appFeaturesPanel.getFlexCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);

        Label complexityLabel = new Label(Utils.constants.complexity());
        complexityLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appFeaturesPanel.setWidget(3, 0, complexityLabel);
        appFeaturesPanel.getFlexCellFormatter().getElement(3, 0).getStyle().setPaddingTop(10, Style.Unit.PX);
        appFeaturesPanel.getFlexCellFormatter().setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_MIDDLE);

        complexityPanel = new HorizontalPanel();
        appFeaturesPanel.setWidget(3, 1, complexityPanel);
        appFeaturesPanel.getFlexCellFormatter().getElement(3, 1).getStyle().setPaddingTop(10, Style.Unit.PX);
        appFeaturesPanel.getFlexCellFormatter().setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_MIDDLE);

        iconAndButtons.add(appFeaturesPanel);

        flexTable.setWidget(1, 0, iconAndButtons);
        flexTable.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        flexTable.getFlexCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);

        FlexTable appDetailsPanel = new FlexTable();
        appDetailsPanel.getColumnFormatter().setWidth(0, "90px");
        appDetailsPanel.getColumnFormatter().setWidth(1, "610px");

        descriptionLabel = new Label();
        descriptionLabel.addStyleName(Utils.sandboxStyle.descriptionLabel());
        appDetailsPanel.setWidget(0, 0, descriptionLabel);
        appDetailsPanel.getFlexCellFormatter().setColSpan(0, 0, 2);

        projectDetailsPanel = new HTML();
        projectDetailsPanel.addStyleName(Utils.sandboxStyle.projectDetails());
        projectDetailsPanel.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);

        appDetailsPanel.setWidget(4, 0, projectDetailsPanel);
        appDetailsPanel.getFlexCellFormatter().setColSpan(4, 0, 2);

        flexTable.setWidget(1, 1, appDetailsPanel);
        flexTable.getFlexCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);


        detailsPanel.add(flexTable);

        carousel = new CarouselWidget();
        carouselPanel.add(carousel);
    }

    @Override
    protected void resetImpl() {

        targetPlatformPanel.clear();
        featuresPanel.clear();
        complexityPanel.clear();
        descriptionLabel.setText("");
        projectDetailsPanel.setHTML("");
        projectTitleLabel.setText("");
        applicationImage.setUrl("");

    }

    @Override
    public void setPlatforms(List<Platform> platforms) {

        for (Platform platform : platforms) {
            Image image = new Image(Utils.getPlatformIcon(platform));
            image.setTitle(Utils.getPlatformText(platform));
            image.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);
            Label label = new Label(Utils.getPlatformText(platform));
            HorizontalPanel platformPanel = new HorizontalPanel();
            platformPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            platformPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            platformPanel.getElement().getStyle().setPaddingBottom(5, Style.Unit.PX);
            platformPanel.add(image);
            platformPanel.setCellWidth(image, "32px");
            platformPanel.add(label);
            targetPlatformPanel.add(platformPanel);
            label.getElement().getStyle().setPaddingLeft(8, Style.Unit.PX);
        }
    }

    @Override
    public void setFeatures(List<Feature> features) {
        for (int i=0;i<features.size();i++) {
            Feature feature = features.get(i);
            Image image = new Image(Utils.getFeatureIcon(feature));
            image.setTitle(Utils.getFeatureText(feature));
            image.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);
            Label label = new Label(Utils.getFeatureText(feature));
            HorizontalPanel featurePanel = new HorizontalPanel();
            featurePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            featurePanel.getElement().getStyle().setPaddingBottom(5, Style.Unit.PX);
            featurePanel.add(image);
            featurePanel.setCellWidth(image, "32px");
            featurePanel.add(label);
            featuresPanel.add(featurePanel);
            if (i < features.size()-1) {
                label.getElement().getStyle().setPaddingRight(10, Style.Unit.PX);
            }
            label.getElement().getStyle().setPaddingLeft(8, Style.Unit.PX);

        }
    }

    @Override
    public void setComplexity(Complexity complexity) {
        Image image = new Image(Utils.getFilterComplexityIcon(complexity));
        image.setTitle(Utils.getComplexityText(complexity));
        image.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);
        Label label = new Label(Utils.getComplexityText(complexity));
        complexityPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        complexityPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        complexityPanel.add(image);
        complexityPanel.setCellWidth(image, "32px");
        complexityPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        complexityPanel.add(label);
        label.getElement().getStyle().setPaddingLeft(8, Style.Unit.PX);
    }

    @Override
    public void setProjectTitle(String title) {
        projectTitleLabel.setText(title);
    }

    @Override
    public Image getApplicationImage() {
        return applicationImage;
    }

    @Override
    public HasText getDescription() {
        return descriptionLabel;
    }

    @Override
    public HasHTML getDetails() {
        return projectDetailsPanel;
    }

    @Override
    public void setProjects(List<Project> projects) {
        carousel.setProjects(projects);
    }

    @Override
    public HasProjectActionEventHandlers getProjectsActionSource() {
        return carousel.getProjectsActionSource();
    }

}
