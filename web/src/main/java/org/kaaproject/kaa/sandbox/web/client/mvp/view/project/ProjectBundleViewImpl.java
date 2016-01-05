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

package org.kaaproject.kaa.sandbox.web.client.mvp.view.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
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

    private CarouselWidget carousel;

    private Label projectTitleLabel;
    private Image applicationImage;
    private Label descriptionLabel;
    private HTML projectDetailsPanel;
    private HorizontalPanel targetPlatformPanel;
    private VerticalPanel featuresPanel;
    private HorizontalPanel complexityPanel;

    public ProjectBundleViewImpl() {
        super(true);
        setBackEnabled(false);
    }

    @Override
    protected String getViewTitle() {
        return null;
    }

    @Override
    protected void initCenterPanel() {

        FlexTable flexTable = new FlexTable();

        flexTable.getColumnFormatter().setWidth(0, "60px");
        flexTable.getColumnFormatter().setWidth(1, "220px");
        flexTable.getColumnFormatter().setWidth(2, "660px");

        backButton = new Button();
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

        Label platformLabel = new Label(Utils.constants.platform());
        platformLabel.addStyleName(Utils.sandboxStyle.contentLabel());
        appFeaturesPanel.setWidget(1, 0, platformLabel);
        appFeaturesPanel.getFlexCellFormatter().getElement(1, 0).getStyle().setPaddingTop(15, Style.Unit.PX);
        appFeaturesPanel.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);


        targetPlatformPanel = new HorizontalPanel();
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

        CaptionPanel captionPanel = new CaptionPanel("LOL!");
        captionPanel.setWidth("100%");

        Button left = new Button("");
//        left.setHTML("<img height=\"32\" width=\"32\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAQAAABpN6lAAAAAAmJLR0QAAKqNIzIAAAAJcEhZcwAAAEgAAABIAEbJaz4AAAJdSURBVHja5Z1LSkQxEAALEfEM3sQLufQsbl16GY/iGRTBcdGK+JnxvZmXdCdVe8EpqChM0g0zcclD9q+QyRWP7LJ/iTyueWLnFXDDCzurgAvuPz68UkCUrxXwWb5UwFf5QgHfy9cJ+Fm+TMDv8lUC/ipfI2Bf+RIB+8tXCDhUvkDA4fInF/B/+VMLWFL+xAKWlT+tgKXlTylgTfkTClhX/nQC1pY/mYD15U8k4LjypxFwbPmTCDi+/CkEnFL+8AJOLX9wAaeXP7SALcofWMA25Q8qYLvyhxSwZfkDCti2/OEEbF3+UAJalD+QgDblDyOgVfmDCGhX/gAC2pZfXkDr8osLaF9+aQE9yi8roFf5RQX0K7+kgJ7lFxTQt/xiAvqXX0pARvmFBOSUX0ZAVvklBGSWX0BAbvnpArLLTxaQX36igBrlpwmoUn6SgDrlpwioVH53AdXK7yygXvldBVQsv6OAW17TP+SGAs5WC3jmrb3l2sgTAP0hCPo/g4H6H6Gg4mnQmXqnQXeqnQYpVDoNkqhzGqRR5TRIpMZpkEz+aZBO9mlQAPmXo6D/ejxQX5AI5FdkQH9JCvTX5AL1RclAflUW9JelQX9dPlA/mAjkT2ZA/2gK9M/mAvXDyUD+dBb0j6dB/3w+UA9QCOQjNEA/RAX0Y3QC9SClQD5KC/TD1EA/Ti9QD1QM5CM1QT9UFfRjdQP1YOVAPlob9MPVQT9eP1AvWAjkKzZAv2QF9Gt2AvWipUC+agv0y9ZAv24vUC9cDOQrN0G/dBX0a3cD9eLlQL56G/TL1wHOuVv7I+/QoKAuzyzOJgAAACV0RVh0ZGF0ZTpjcmVhdGUAMjAxNi0wMS0wNVQxMToyMToyMC0wNjowMOMTCbEAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMTYtMDEtMDVUMTE6MjE6MjAtMDY6MDCSTrENAAAAAElFTkSuQmCC\" />");
        left.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                carousel.moveLeft();
            }
        });

        Button right = new Button("");
//        right.setHTML("<img height=\"32\" width=\"32\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAYAAADDPmHLAAAABmJLR0QA/wD/AP+gvaeTAAADKklEQVR4nO2du21cMRQFLwRDcA3uxA25HqcK3YxLcQ+OVomCxYJv931IDsk7A0ysB55rnGMlimjP74j41uHnyKDcIuJvRPygP0QYbl/+i4if8LcIwO3O/xHxi/0c6c2t4EdEvJMfJf0oHYC7IBFbB+AuSMKzA3AXJODVAbgLFmfvAbgLFuXIAbgLFuToAbgLFuPMAbgLFuLKAbgLFuDqAbgLJqfGAbgLJqbWAbgLJqX2AbgLJqPFAbgLJqLVAbgLJqHlAbgLJqDHAbgLBqbXAbgLBqXnAbgLBqT3AbgLBoM6AHfBIJAH4C4YAPoA3AUwdPj3ugsA6NAfdRd0hg68pLugI3TYW7oLOkEH/Up3QWPogPfoLmgIHe5e3QWNoIM9orugAXSoZ3QXVIQO86zugkrQQV7RXVABOsSrugsuQgdYS3fBSejgauouOAEdWm3dBQehA2uhu+AAdFgtdRfsgA6pte6CF9AB9dBd8AQ6nF66Czagg+mtu+ABOhBCd8EddBiU7oIv6CBI3QXBhzCCqXcB/fijmHYX0A8/kil3Af3oo5luF9APPqppdgH90CObYhfQjzy6y+8C+oFncOldQD/uTC65C+hHnc3ldgH9oDO61C6gH3NWu+yCt9Y/QE7zFhHf6Y+oAf0vaUatgMQ6AhPrfwOT6i+CErtU35egH3hkl+v7EvQjj+qSfV+CfujRXLrvS9APPpLL930J+tFHMUXfl6AffgTT9H0J+vFJ0/V9CToEypR9X4IOgjBt35egw+ht6r4vQQfSS/t+AzqYHtr3T6DDaa19/wI6oJba9zugQ2qhfX8AOqza2vcHoQOrqX1/Ajq0Wtr3J6GDu6p9fxE6wCva9xWgQzyrfV8JOsgz2vcVocM8on3fADrUvdr3jaCD3aN93xA63Ffa942hA97Svu8EHXRJ+74jdNiP2vedoQO/174HoEO/hX2PQodv38OQ4dv3A2DfJ8e+T459nxz7Pjn2fXLs++TY98mx75Nj3yfHvk+OfZ8c+z459n1y7Pvk2PfJse+TY98nx75Pjn2fHPs+OfZ9cuz75Nj3ybHvk/MnFvkz6CvyCTNonjj7VG51AAAAAElFTkSuQmCC\" />");
        right.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                carousel.moveRight();
//                GWT.log("left = " + carousel.getLeft() + " right = " + carousel.getRight());
            }
        });

        carousel = new CarouselWidget();
        carousel.getElement().getStyle().setProperty("maxHeight", 320, Style.Unit.PX);

        FlexTable c = new FlexTable();
        c.setWidth("100%");
        c.getColumnFormatter().setWidth(0, "40px");
        c.getColumnFormatter().setWidth(2, "40px");
        c.setWidget(0, 0, left);
        c.setWidget(0, 1, carousel);
        c.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
        c.setWidget(0, 2, right);

        captionPanel.add(c);
        flexTable.setWidget(2, 0, captionPanel);
        flexTable.getFlexCellFormatter().setColSpan(2, 0, 3);

        detailsPanel.add(flexTable);

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
    public void setPlatform(Platform platform) {
        Image image = new Image(Utils.getPlatformIcon(platform));
        image.setTitle(Utils.getPlatformText(platform));
        image.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);
        Label label = new Label(Utils.getPlatformText(platform));
        targetPlatformPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        targetPlatformPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        targetPlatformPanel.add(image);
        targetPlatformPanel.setCellWidth(image, "32px");
        targetPlatformPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        targetPlatformPanel.add(label);
        label.getElement().getStyle().setPaddingLeft(8, Style.Unit.PX);
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
        return carousel;
    }

}
