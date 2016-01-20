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

import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class LeftPanelWidget extends DockLayoutPanel {

    private boolean isOpen = true;
    
    private ScrollPanel contentScroll;
    private Label titleLabel;
    private Anchor anchor;
    
    protected boolean isActive = true;
    
    
    public LeftPanelWidget(Unit unit) {
        super(unit);
        addStyleName(Utils.sandboxStyle.leftPanel());
        HorizontalPanel titlePanel = new HorizontalPanel();
        titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        titlePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        titleLabel = new Label();
        titleLabel.addStyleName(Utils.sandboxStyle.title());
        titlePanel.add(titleLabel);
        titlePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        anchor = new Anchor();
        anchor.addStyleName(Utils.sandboxStyle.toggle());
        titlePanel.add(anchor);
        titlePanel.setSize("100%", "100%");
        addNorth(titlePanel, 40);
        
        contentScroll = new ScrollPanel();
        contentScroll.setHeight("100%");
        add(contentScroll);
        
        addStyleName(Utils.sandboxStyle.isOpen());
        
        anchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	if (isActive) {
            		setOpen(!isOpen);
            	}
            }
        });
    }
    
    public void setActive(boolean isActive) {
    	if (this.isActive != isActive) {
    		this.isActive = isActive;
    		setOpen(this.isActive);
    		if (!isActive) {
    			anchor.addStyleName(Utils.sandboxStyle.inactive());
    		} else {
    			anchor.removeStyleName(Utils.sandboxStyle.inactive());
    		}
    	}
    }
    
    private void setOpen(boolean isOpen) {
    	if (this.isOpen != isOpen) {
    		this.isOpen = isOpen;
	        if (this.isOpen) {
	            addStyleName(Utils.sandboxStyle.isOpen());
	        } else {
	            removeStyleName(Utils.sandboxStyle.isOpen());
	        }
    	}
    }
    
    public void setHeadTitle(String title) {
        titleLabel.setText(title);
    }
    
    public void setContent(Widget widget) {
        contentScroll.setWidget(widget);
    }

}
