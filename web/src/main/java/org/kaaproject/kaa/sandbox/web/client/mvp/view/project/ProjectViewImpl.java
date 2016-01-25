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

import java.util.List;

import org.kaaproject.kaa.examples.common.projects.Complexity;
import org.kaaproject.kaa.examples.common.projects.Feature;
import org.kaaproject.kaa.examples.common.projects.Platform;
import org.kaaproject.kaa.examples.common.projects.SdkLanguage;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.ProjectView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.base.BaseViewImpl;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProjectViewImpl extends BaseViewImpl implements ProjectView {
    
    private Label projectTitleLabel;
    private Image applicationImage;
    private Label descriptionLabel;
    private HTML projectDetailsPanel;
    private HorizontalPanel sdkLanguagePanel;
    private HorizontalPanel platformsPanel;
    private HorizontalPanel featuresPanel;
    private HorizontalPanel complexityPanel;
    private Button backButton;
    private Button getSourceButton;
    private Button getBinaryButton;
    
    public ProjectViewImpl() {
        super(true);
        setBackEnabled(false);
    }

    @Override
    protected String getViewTitle() {
        return "";
    }

    @Override
    protected void initCenterPanel() {
        
        FlexTable flexTable = new FlexTable();
        
        flexTable.getColumnFormatter().setWidth(0, "60px");
        flexTable.getColumnFormatter().setWidth(1, "160px");
        flexTable.getColumnFormatter().setWidth(2, "700px");
        
        backButton = new Button();
        backButton.addStyleName(Utils.sandboxStyle.appBackButton());
        backButton.getElement().getStyle().setHeight(180, Unit.PX);
        setBackButton(backButton);
        
        flexTable.setWidget(0, 0, backButton);
        flexTable.getFlexCellFormatter().setRowSpan(0, 0, 2);
        flexTable.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        
        projectTitleLabel = new Label();
        projectTitleLabel.addStyleName(Utils.sandboxStyle.contentTitleLabel());
        projectTitleLabel.getElement().getStyle().setPaddingBottom(15, Unit.PX);
        
        flexTable.setWidget(0, 1, projectTitleLabel);
        flexTable.getFlexCellFormatter().setColSpan(0, 1, 2);
        flexTable.getFlexCellFormatter().setHeight(0, 1, "51px");
        
        VerticalPanel iconAndButtons = new VerticalPanel();
        iconAndButtons.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        
        applicationImage = new Image();
        iconAndButtons.add(applicationImage);
        
        VerticalPanel buttonsPanel = new VerticalPanel();
        buttonsPanel.getElement().getStyle().setPaddingTop(30, Unit.PX);
        buttonsPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        buttonsPanel.setWidth("100%");
        
        getSourceButton = new Button(Utils.constants.getSourceCode());
        getSourceButton.setSize("128px", "32px");
        getSourceButton.getElement().getStyle().setPaddingTop(0, Unit.PX);
        getSourceButton.getElement().getStyle().setPaddingBottom(0, Unit.PX);
        getSourceButton.getElement().getStyle().setFontWeight(FontWeight.BOLD);
        getBinaryButton = new Button(Utils.constants.getBinary());
        getBinaryButton.setSize("128px", "32px");
        getBinaryButton.getElement().getStyle().setPaddingTop(0, Unit.PX);
        getBinaryButton.getElement().getStyle().setPaddingBottom(0, Unit.PX);
        getBinaryButton.getElement().getStyle().setFontWeight(FontWeight.BOLD);
        
        buttonsPanel.add(getSourceButton);
        SimplePanel spacingPanel = new SimplePanel();
        spacingPanel.setHeight("10px");
        buttonsPanel.add(spacingPanel);
        buttonsPanel.add(getBinaryButton);
        
        iconAndButtons.add(buttonsPanel);

        flexTable.setWidget(1, 0, iconAndButtons);
        flexTable.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        
        FlexTable appDetailsPanel = new FlexTable();
        appDetailsPanel.getColumnFormatter().setWidth(0, "120px");
        appDetailsPanel.getColumnFormatter().setWidth(1, "610px");
        
        int row = -1;
        
        descriptionLabel = new Label();
        descriptionLabel.addStyleName(Utils.sandboxStyle.descriptionLabel());
        appDetailsPanel.setWidget(++row, 0, descriptionLabel);
        appDetailsPanel.getFlexCellFormatter().setColSpan(row, 0, 2);
        
        Label sdkLanguageLabel = new Label(Utils.constants.sdkLanguage());
        sdkLanguageLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appDetailsPanel.setWidget(++row, 0, sdkLanguageLabel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 0).getStyle().setPaddingTop(15, Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_MIDDLE);
        
        sdkLanguagePanel = new HorizontalPanel();
        appDetailsPanel.setWidget(row, 1, sdkLanguagePanel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 1).getStyle().setPaddingTop(15, Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_MIDDLE);
        
        Label platformsLabel = new Label(Utils.constants.platforms());
        platformsLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appDetailsPanel.setWidget(++row, 0, platformsLabel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 0).getStyle().setPaddingTop(15, Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_MIDDLE);        
        
        platformsPanel = new HorizontalPanel();
        appDetailsPanel.setWidget(row, 1, platformsPanel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 1).getStyle().setPaddingTop(15, Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_MIDDLE);
        
        Label featuresLabel = new Label(Utils.constants.features());
        featuresLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appDetailsPanel.setWidget(++row, 0, featuresLabel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 0).getStyle().setPaddingTop(10, Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_MIDDLE);
        
        featuresPanel = new HorizontalPanel();
        appDetailsPanel.setWidget(row, 1, featuresPanel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 1).getStyle().setPaddingTop(10, Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_MIDDLE);
        
        Label complexityLabel = new Label(Utils.constants.complexity());
        complexityLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appDetailsPanel.setWidget(++row, 0, complexityLabel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 0).getStyle().setPaddingTop(10, Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_MIDDLE);
        
        complexityPanel = new HorizontalPanel();
        appDetailsPanel.setWidget(row, 1, complexityPanel);
        appDetailsPanel.getFlexCellFormatter().getElement(row, 1).getStyle().setPaddingTop(10, Unit.PX);
        appDetailsPanel.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_MIDDLE);

        projectDetailsPanel = new HTML();
        projectDetailsPanel.addStyleName(Utils.sandboxStyle.projectDetails());
        projectDetailsPanel.getElement().getStyle().setPaddingTop(15, Unit.PX);
        
        appDetailsPanel.setWidget(++row, 0, projectDetailsPanel);
        appDetailsPanel.getFlexCellFormatter().setColSpan(row, 0, 2);

        flexTable.setWidget(1, 1, appDetailsPanel);
        flexTable.getFlexCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);
        
        detailsPanel.add(flexTable);
    }

    @Override
    protected void resetImpl() {
    	sdkLanguagePanel.clear();
        platformsPanel.clear();
        featuresPanel.clear();
        complexityPanel.clear();
        descriptionLabel.setText("");
        projectDetailsPanel.setHTML("");
        projectTitleLabel.setText("");        
        applicationImage.setUrl("");
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
    public HasClickHandlers getSourceButton() {
        return getSourceButton;
    }

    @Override
    public HasClickHandlers getBinaryButton() {
        return getBinaryButton;
    }

    @Override
    public void setBinaryButtonVisible(boolean visible) {
        getBinaryButton.setVisible(visible);
    }
    
	@Override
	public void setSdkLanguage(SdkLanguage sdkLanguage) {
		Image image = new Image(Utils.getSdkLanguageIcon(sdkLanguage));
		image.setTitle(Utils.getSdkLanguageText(sdkLanguage));
		image.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		Label label = new Label(Utils.getSdkLanguageText(sdkLanguage));
        sdkLanguagePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        sdkLanguagePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        sdkLanguagePanel.add(image);
        sdkLanguagePanel.setCellWidth(image, "32px");
        sdkLanguagePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        sdkLanguagePanel.add(label);
        label.getElement().getStyle().setPaddingLeft(8, Unit.PX);
	}

    @Override
    public void setPlatforms(List<Platform> platforms) {
        for (int i=0;i<platforms.size();i++) {
            Platform platform = platforms.get(i);
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
    	image.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
    	Label label = new Label(Utils.getComplexityText(complexity));
    	complexityPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    	complexityPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    	complexityPanel.add(image);
    	complexityPanel.setCellWidth(image, "32px");
    	complexityPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
    	complexityPanel.add(label);
        label.getElement().getStyle().setPaddingLeft(8, Unit.PX);
    };

    @Override
    public Image getApplicationImage() {
        return applicationImage;
    }

    @Override
    public void setProjectTitle(String title) {
        projectTitleLabel.setText(title);
        
    }



}
