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

package org.kaaproject.kaa.sandbox.web.client.mvp.view.settings;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.ChangeKaaHostView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.base.BaseViewImpl;
import org.kaaproject.kaa.sandbox.web.client.util.LogLevel;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

public class ChangeKaaHostViewImpl extends BaseViewImpl implements ChangeKaaHostView {

    private static final String FULL_WIDTH = "100%";

    private FlexTable changeHostPanel;
    private TextBox kaaHost;
    private Button changeKaaHostButton;

    private FlexTable logsPanel;
    private Button getLogsButton;
    private Button changeLogLevelButton;
    private ValueListBox<LogLevel> levelListBox;
    private CheckBox oldLogsCheckBox;

    private Element ipSpan;

    public ChangeKaaHostViewImpl() {
        super(true);
        setBackEnabled(true);
    }
    
    @Override
    protected String getViewTitle() {
        return Utils.constants.sandboxManagement();
    }

    @Override
    protected void initCenterPanel() {

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.getElement().getStyle().setWidth(75, Unit.PCT);

        CaptionPanel kaaHostPanel = new CaptionPanel(Utils.constants.kaaHostIp());
        kaaHostPanel.setWidth(FULL_WIDTH);
        kaaHostPanel.getElement().getStyle().setMarginBottom(20, Unit.PX);
        changeHostPanel = new FlexTable();
        changeHostPanel.setCellSpacing(10);
        changeHostPanel.getColumnFormatter().setWidth(0, "230px");
        changeHostPanel.getColumnFormatter().setWidth(1, "150px");
        changeHostPanel.getColumnFormatter().setWidth(2, FULL_WIDTH);
        int row = 0;

        String changeHostMessage = "<div>" +
                Utils.messages.changeKaaHostMessagePt1() +
                " <b><span id=ip></span></b>" +
                ".<br>" +
                Utils.messages.changeKaaHostMessagePt2() +
                "</div>";

        HTMLPanel changeKaaHostHtmlPanel = new HTMLPanel(changeHostMessage);
        ipSpan = changeKaaHostHtmlPanel.getElementById("ip");

        changeKaaHostHtmlPanel.addStyleName(Utils.sandboxStyle.descriptionLabel());
        changeKaaHostHtmlPanel.getElement().getStyle().setProperty("text-align", "justify");
        changeKaaHostHtmlPanel.getElement().getStyle().setMarginBottom(20, Unit.PX);
        changeHostPanel.setWidget(row, 0, changeKaaHostHtmlPanel);
        changeHostPanel.getFlexCellFormatter().setColSpan(row++, 0, 3);
        kaaHost = new TextBox();
        kaaHost.setWidth("200px");
        kaaHost.getElement().getStyle().setMarginRight(20, Unit.PX);
        changeHostPanel.setWidget(row, 0, kaaHost);
        changeKaaHostButton = new Button(Utils.constants.update());
        changeHostPanel.setWidget(row, 1, changeKaaHostButton);

        kaaHostPanel.add(changeHostPanel);
        mainPanel.add(kaaHostPanel);

        CaptionPanel kaaLogsPanel = new CaptionPanel(Utils.constants.kaaServerLogs());
        kaaLogsPanel.setWidth(FULL_WIDTH);
        logsPanel = new FlexTable();
        logsPanel.setCellSpacing(10);
        logsPanel.getColumnFormatter().setWidth(0, "100px");
        logsPanel.getColumnFormatter().setWidth(1, "20px");
        logsPanel.getColumnFormatter().setWidth(2, "110px");
        logsPanel.getColumnFormatter().setWidth(3, "150px");
        logsPanel.getColumnFormatter().setWidth(4, FULL_WIDTH);

        row = 0;
        HTML getLogsLabel = new HTML(Utils.messages.logsMessage());
        getLogsLabel.addStyleName(Utils.sandboxStyle.descriptionLabel());
        getLogsLabel.getElement().getStyle().setProperty("text-align", "justify");
        getLogsLabel.getElement().getStyle().setPaddingBottom(20, Style.Unit.PX);
        logsPanel.setWidget(row, 0, getLogsLabel);
        logsPanel.getFlexCellFormatter().setColSpan(row++, 0, 5);

        levelListBox = new ValueListBox<>();
        levelListBox.setWidth("100px");
        for (LogLevel level : LogLevel.values()) {
            levelListBox.setValue(level);
        }
        logsPanel.setWidget(row, 0, levelListBox);

        oldLogsCheckBox = new CheckBox();
        oldLogsCheckBox.setWidth("20px");
        oldLogsCheckBox.addStyleName(Utils.avroUiStyle.legendCheckBox());
        logsPanel.setWidget(row, 1, oldLogsCheckBox);

        HTML checkBoxLabel = new HTML(Utils.constants.cleanUpOldLogfiles());
        checkBoxLabel.addStyleName(Utils.avroUiStyle.legendCheckBox());
        checkBoxLabel.setWidth("90px");
        logsPanel.setWidget(row, 2, checkBoxLabel);

        changeLogLevelButton = new Button(Utils.constants.update());
        logsPanel.setWidget(row++, 3, changeLogLevelButton);

        getLogsButton = new Button(Utils.constants.downloadLogs());
        getLogsButton.getElement().getStyle().setMarginTop(20, Unit.PX);
        logsPanel.setWidget(row, 0, getLogsButton);
        logsPanel.getFlexCellFormatter().setColSpan(row, 0, 5);

        kaaLogsPanel.add(logsPanel);
        mainPanel.add(kaaLogsPanel);
        detailsPanel.add(mainPanel);
    }

    @Override
    protected void resetImpl() {
        setChangeKaaHostEnabled(false);
        kaaHost.setText("");
        setGetLogsEnabled(false);
    }

    @Override
    public HasClickHandlers getChangeKaaHostButton() {
        return changeKaaHostButton;
    }

    @Override
    public void setChangeKaaHostEnabled(boolean enabled) {
        changeHostPanel.setVisible(enabled);
        updateHeaderHeight();
    }

    @Override
    public HasValue<String> getKaaHost() {
        return kaaHost;
    }

    @Override
    public HasClickHandlers getGetLogsButton() {
        return getLogsButton;
    }

    public HasClickHandlers getChangeLogLevelButton() {
        return changeLogLevelButton;
    }

    @Override
    public void setGetLogsEnabled(Boolean enabled) {
        logsPanel.setVisible(enabled);
        updateHeaderHeight();
    }

    @Override
    public ValueListBox<LogLevel> getLevelListBox() {
        return levelListBox;
    }

    @Override
    public CheckBox getOldLogsCheckBox() {
        return oldLogsCheckBox;
    }

    @Override
    public Element getIpSpan() {
        return ipSpan;
    }
}
