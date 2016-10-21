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

package org.kaaproject.kaa.sandbox.web.client.mvp.view.project;

import java.util.List;

import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.Project;
import org.kaaproject.kaa.examples.common.projects.SdkLanguage;
import org.kaaproject.kaa.sandbox.web.client.mvp.event.project.HasProjectActionEventHandlers;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.ProjectBundleView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.base.BaseViewImpl;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.widget.CarouselWidget;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProjectBundleViewImpl extends BaseViewImpl implements ProjectBundleView {

    private CarouselWidget carousel;

    private Label bundleTitleLabel;
    private Image bundleImage;
    private Label descriptionLabel;
    private HTML projectDetailsPanel;
    private HorizontalPanel sdkLanguagesPanel;
    private HorizontalPanel platformsPanel;
    private HorizontalPanel featuresPanel;
    private HorizontalPanel complexityPanel;

    public ProjectBundleViewImpl() {
        super(false);
        setBackEnabled(false);
    }

    @Override
    protected String getViewTitle() {
        return null;
    }

    @Override
    protected void initCenterPanel() {

        FlexTable flexTable = new FlexTable();
        flexTable.addStyleName(sandboxStyle.contentPanel());
        
        flexTable.getColumnFormatter().setWidth(0, "60px");
        flexTable.getColumnFormatter().setWidth(1, "160px");
        flexTable.getColumnFormatter().setWidth(2, "700px");

        backButton.addStyleName(Utils.sandboxStyle.appBackButton());
        backButton.getElement().getStyle().setHeight(180, Unit.PX);
        setBackButton(backButton);

        flexTable.setWidget(0, 0, backButton);
        flexTable.getFlexCellFormatter().setRowSpan(0, 0, 2);
        flexTable.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);

        bundleTitleLabel = new Label();
        bundleTitleLabel.addStyleName(Utils.sandboxStyle.contentTitleLabel());
        bundleTitleLabel.getElement().getStyle().setPaddingBottom(15, Unit.PX);

        flexTable.setWidget(0, 1, bundleTitleLabel);
        flexTable.getFlexCellFormatter().setColSpan(0, 1, 2);
        flexTable.getFlexCellFormatter().setHeight(0, 1, "51px");

        VerticalPanel iconAndButtons = new VerticalPanel();
        iconAndButtons.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        bundleImage = new Image();
        iconAndButtons.add(bundleImage);
        
        flexTable.setWidget(1, 0, iconAndButtons);
        flexTable.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);

        FlexTable appDetailsPanel = new FlexTable();
        appDetailsPanel.getColumnFormatter().setWidth(0, "130px");
        appDetailsPanel.getColumnFormatter().setWidth(1, "610px");
        
        int row = -1;
        
        descriptionLabel = new Label();
        descriptionLabel.addStyleName(Utils.sandboxStyle.descriptionLabel());
        appDetailsPanel.setWidget(++row, 0, descriptionLabel);
        appDetailsPanel.getFlexCellFormatter().setColSpan(row, 0, 2);
        
        Label sdkLanguagesLabel = new Label(Utils.constants.sdkLanguages());
        sdkLanguagesLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appDetailsPanel.setWidget(++row, 0, sdkLanguagesLabel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 0).getStyle().setPaddingTop(15, Style.Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);

        sdkLanguagesPanel = new HorizontalPanel();
        appDetailsPanel.setWidget(row, 1, sdkLanguagesPanel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 1).getStyle().setPaddingTop(15, Style.Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_MIDDLE);

        Label platformsLabel = new Label(Utils.constants.platforms());
        platformsLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appDetailsPanel.setWidget(++row, 0, platformsLabel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 0).getStyle().setPaddingTop(15, Style.Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);

        platformsPanel = new HorizontalPanel();
        appDetailsPanel.setWidget(row, 1, platformsPanel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 1).getStyle().setPaddingTop(15, Style.Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_MIDDLE);

        Label featuresLabel = new Label(Utils.constants.features());
        featuresLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appDetailsPanel.setWidget(++row, 0, featuresLabel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 0).getStyle().setPaddingTop(10, Style.Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);

        featuresPanel = new HorizontalPanel();
        appDetailsPanel.setWidget(row, 1, featuresPanel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 1).getStyle().setPaddingTop(10, Style.Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_MIDDLE);

        Label complexityLabel = new Label(Utils.constants.complexity());
        complexityLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appDetailsPanel.setWidget(++row, 0, complexityLabel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 0).getStyle().setPaddingTop(10, Style.Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_MIDDLE);

        complexityPanel = new HorizontalPanel();
        appDetailsPanel.setWidget(row, 1, complexityPanel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 1).getStyle().setPaddingTop(10, Style.Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_MIDDLE);

        flexTable.setWidget(1, 1, appDetailsPanel);
        flexTable.getFlexCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

        carousel = new CarouselWidget();
        HorizontalPanel carouselPanel = new HorizontalPanel();
        carouselPanel.addStyleName(sandboxStyle.bundleProjectsPanel());
        carouselPanel.getElement().getStyle().setPadding(15, Unit.PX);
        carouselPanel.setHeight("330px");
        carouselPanel.setWidth("100%");
        carouselPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        carouselPanel.add(carousel);

        flexTable.setWidget(2, 1, carouselPanel);
        flexTable.getFlexCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_MIDDLE);
        
        projectDetailsPanel = new HTML();
        projectDetailsPanel.addStyleName(Utils.sandboxStyle.projectDetails());
        projectDetailsPanel.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);
        projectDetailsPanel.getElement().getStyle().setPaddingBottom(15, Style.Unit.PX);
        flexTable.setWidget(3, 1, projectDetailsPanel);
        flexTable.getFlexCellFormatter().setColSpan(2, 1, 2);

        VerticalPanel content = new VerticalPanel();
        content.add(flexTable);
        content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        content.getElement().getStyle().setOverflowX(Overflow.AUTO);

        DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.PX);
        dockPanel.add(new ScrollPanel(content));

        centerPanel.add(dockPanel);
        centerPanel.getWidgetContainerElement(dockPanel).getStyle().setOverflowY(Overflow.AUTO);
    }

    @Override
    protected void resetImpl() {
        sdkLanguagesPanel.clear();
        platformsPanel.clear();
        featuresPanel.clear();
        complexityPanel.clear();
        descriptionLabel.setText("");
        projectDetailsPanel.setHTML("");
        bundleTitleLabel.setText("");
        bundleImage.setUrl("");

    }
    
	@Override
	public void setSdkLanguages(List<SdkLanguage> sdkLanguages) {
        for (int i=0;i<sdkLanguages.size();i++) {
        	SdkLanguage sdkLanguage  = sdkLanguages.get(i);
            Image image = new Image(Utils.getSdkLanguageIcon(sdkLanguage));
            image.setTitle(Utils.getSdkLanguageText(sdkLanguage));
            image.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
            Label label = new Label(Utils.getSdkLanguageText(sdkLanguage));
            sdkLanguagesPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            sdkLanguagesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            sdkLanguagesPanel.add(image);
            sdkLanguagesPanel.setCellWidth(image, "32px");
            sdkLanguagesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
            sdkLanguagesPanel.add(label);
            if (i < sdkLanguages.size()-1) {
                label.getElement().getStyle().setPaddingRight(10, Unit.PX);
            }
            label.getElement().getStyle().setPaddingLeft(8, Unit.PX);
        }
	}
    
    @Override
    public void setPlatforms(List<Platform> platforms) {
        for (int i=0;i<platforms.size();i++) {
        	Platform platform  = platforms.get(i);
            Image image = new Image(Utils.getPlatformIcon(platform));
            image.setTitle(Utils.getPlatformText(platform));
            image.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
            Label label = new Label(Utils.getPlatformText(platform));
            platformsPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            platformsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            platformsPanel.add(image);
            platformsPanel.setCellWidth(image, "32px");
            platformsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
            platformsPanel.add(label);
            if (i < platforms.size()-1) {
                label.getElement().getStyle().setPaddingRight(10, Unit.PX);
            }
            label.getElement().getStyle().setPaddingLeft(8, Unit.PX);
        }
    }

    @Override
    public void setFeatures(List<Feature> features) {
        for (int i=0;i<features.size();i++) {
            Feature feature = features.get(i);
            Image image = new Image(Utils.getFeatureIcon(feature));
            image.setTitle(Utils.getFeatureText(feature));
            image.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
            Label label = new Label(Utils.getFeatureText(feature));
            featuresPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            featuresPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            featuresPanel.add(image);
            featuresPanel.setCellWidth(image, "32px");
            featuresPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
            featuresPanel.add(label);
            if (i < features.size()-1) {
                label.getElement().getStyle().setPaddingRight(10, Unit.PX);
            }
            label.getElement().getStyle().setPaddingLeft(8, Unit.PX);
            
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
    public void setBundleTitle(String title) {
        bundleTitleLabel.setText(title);
    }

    @Override
    public Image getBundleImage() {
        return bundleImage;
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
