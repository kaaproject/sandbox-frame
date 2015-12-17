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
import org.kaaproject.kaa.sandbox.web.client.mvp.view.ChangeKaaHostView;
import org.kaaproject.kaa.sandbox.web.client.mvp.view.base.BaseViewImpl;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChangeKaaHostViewImpl extends BaseViewImpl implements ChangeKaaHostView {
    
    private VerticalPanel changeHostPanel;
    private TextBox kaaHost;
    private Button changeKaaHostButton;

    private VerticalPanel logsPanel;
    private Button getLogsButton;
    
    public ChangeKaaHostViewImpl() {
        super(true);
        setBackEnabled(true);
    }
    
    @Override
    protected String getViewTitle() {
        return Utils.constants.changeKaaHost();
    }

    @Override
    protected void initCenterPanel() {

        VerticalPanel mainPanel = new VerticalPanel();

        changeHostPanel = new VerticalPanel();
        HTML changeKaaHostLabel = new HTML(Utils.messages.changeKaaHostMessage());
        changeKaaHostLabel.addStyleName(Utils.sandboxStyle.descriptionLabel());
        changeHostPanel.add(changeKaaHostLabel);
        HorizontalPanel changeHostInputPanel = new HorizontalPanel();
        changeHostPanel.add(changeHostInputPanel);
        changeHostInputPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        changeHostInputPanel.getElement().getStyle().setPaddingTop(20, Unit.PX);
        kaaHost = new TextBox();
        kaaHost.setWidth("200px");
        kaaHost.getElement().getStyle().setMarginRight(20, Unit.PX);
        changeHostInputPanel.add(kaaHost);
        changeKaaHostButton = new Button(Utils.constants.change());
        changeHostInputPanel.add(changeKaaHostButton);

        mainPanel.add(changeHostPanel);

        logsPanel = new VerticalPanel();
        logsPanel.getElement().getStyle().setPaddingTop(20, Style.Unit.PX);
        HTML getLogsLabel = new HTML(Utils.messages.getLogsMessage());
        getLogsLabel.addStyleName(Utils.sandboxStyle.descriptionLabel());
        getLogsLabel.getElement().getStyle().setPaddingBottom(20, Style.Unit.PX);
        logsPanel.add(getLogsLabel);

        getLogsButton = new Button(Utils.constants.getLogs());
        logsPanel.add(getLogsButton);

        mainPanel.add(logsPanel);

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

    @Override
    public void setGetLogsEnabled(Boolean enabled) {
        logsPanel.setVisible(enabled);
        updateHeaderHeight();
    }

}
