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

import org.kaaproject.kaa.sandbox.web.client.util.ResizeSupport;
import org.kaaproject.kaa.sandbox.web.client.util.ResizeSupport.OnResizeListener;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
    
    private HandlerRegistration resizeHandler;
    
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
        
        contentScroll = new ScrollPanel() {
        	@Override
        	public void onResize() {
        		super.onResize();
        		int scrollHeight = this.getScrollableElement().getClientHeight();
        		int elementHeight = this.getContainerElement().getClientHeight();
        		boolean isScroll = elementHeight > scrollHeight;
        		if (isScroll && !(LeftPanelWidget.this.isOpen && LeftPanelWidget.this.isActive)) {
        			LeftPanelWidget.this.addStyleName(Utils.sandboxStyle.isScroll());
        		} else {
        			LeftPanelWidget.this.removeStyleName(Utils.sandboxStyle.isScroll());
        		}
        	}
        };
        contentScroll.setHeight("100%");
        add(contentScroll);
        
        addStyleName(Utils.sandboxStyle.isOpen());
        
        anchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	if (isActive) {
            		setOpen(!isOpen, false);
            	}
            }
        });
    }
    
    public void setActive(boolean isActive) {
    	if (this.isActive != isActive) {
    		this.isActive = isActive;
    		if (!isActive) {
    			setOpen(false, true);
    			addStyleName(Utils.sandboxStyle.isInactive());
    		} else {
    			setOpen(this.isOpen, true);
    			removeStyleName(Utils.sandboxStyle.isInactive());
    		}
    	}
    }
    
    private void setOpen(boolean isOpen, boolean force) {
    	if (this.isOpen != isOpen || force) {
    		if (!force) {
    			this.isOpen = isOpen;
    		}
	        if (isOpen) {
	            addStyleName(Utils.sandboxStyle.isOpen());
	            removeStyleName(Utils.sandboxStyle.isScroll());
	        	if (resizeHandler != null) {
	        		resizeHandler.removeHandler();
	        		resizeHandler = null;
	        	}
	        } else {
	            removeStyleName(Utils.sandboxStyle.isOpen());
	        	contentScroll.onResize();
	        	if (resizeHandler == null) {
    	            resizeHandler = ResizeSupport.addOnResizeListener(contentScroll.getElement(), new OnResizeListener() {
    	                
    	                @Override
    	                public void onResize(Element e) {
    	                    contentScroll.onResize();
    	                    
    	                }
    	            });
	        	}
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
