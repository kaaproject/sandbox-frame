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

package org.kaaproject.kaa.sandbox.web.client.mvp.view.widget;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kaaproject.avro.ui.gwt.client.widget.nav.ListItem;
import org.kaaproject.avro.ui.gwt.client.widget.nav.UnorderedList;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;

public abstract class FilterPanel<T> extends NavWidget implements HasValueChangeHandlers<Boolean> {

    private FilterItem<T> headerItem;
    private UnorderedList itemsUl;
    private List<FilterItem<T>> items = new ArrayList<>();
    
    private List<HandlerRegistration> registrations = new ArrayList<>();
    
    private boolean isExpanded = true;
    
    public FilterPanel(String title) {
        super();
        addStyleName(Utils.sandboxStyle.navPrimary());

        UnorderedList headerUl = new UnorderedList();
        headerUl.addStyleName(Utils.sandboxStyle.nav());
        add(headerUl);
        
        headerItem = new FilterItem<T>(null, Utils.resources.collapse(), null, title);
        headerItem.addStyleName(Utils.sandboxStyle.primary());
        headerItem.setValue(false);
        headerUl.add(headerItem);
        headerItem.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                sendAnalytic();
                setExpanded(event.getValue());
            }
        });
        itemsUl = new UnorderedList();
        itemsUl.addStyleName(Utils.sandboxStyle.nav());
        headerItem.add(itemsUl);
    }
    
    public void updateItemsHeight() {
        itemsUl.getElement().getStyle().setPropertyPx("maxHeight", itemsUl.getElement().getClientHeight());
        setExpanded(false);
    }
    
    public void setExpanded(boolean expanded) {
        if (isExpanded != expanded) {
            isExpanded = expanded;
            if (expanded) {
                removeStyleName(Utils.sandboxStyle.collapsed());
            } else {
                addStyleName(Utils.sandboxStyle.collapsed());   
            }
        }
    }

    abstract protected void sendAnalytic();
    
    public void addItem(T filterEntity, ImageResource imageRes, String bgClass, String text) {
        FilterItem<T> item = new FilterItem<T>(filterEntity, imageRes, bgClass, text);
        itemsUl.add(item);
        registrations.add(item.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                fireEvent(event);
            }
        }));
        items.add(item);
    }
    
    public void reset() {
        registrations.clear();
        for (FilterItem<T> item : items) {
            itemsUl.remove(item);
        }        
        items.clear();
    }
    
    @Override
    public HandlerRegistration addValueChangeHandler(
            ValueChangeHandler<Boolean> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }
    
    public List<FilterItem<T>> getFilterItems() {
        return items;
    }
    
    public Set<T> getEnabledFilterEntities() {
        Set<T> enabledFilterEntities = new HashSet<>();
        for (FilterItem<T> item : items) {
            if (item.getValue()) {
                enabledFilterEntities.add(item.getFilterEntity());
            }
        }
        return enabledFilterEntities;
    }
    
    public void setActive(boolean active) {
        headerItem.setActive(active);
    	for (FilterItem<T> item : items) {
    		item.setActive(active);
    	}
    }

    public class FilterItem<E> extends ListItem implements HasValue<Boolean> {
        
        private Anchor anchor;
        
        private boolean valueChangeHandlerInitialized;
        private boolean value = false;
        private boolean active = true;
        private boolean isSendAnalytic = true;
        private E filterEntity;
        
        FilterItem(E filterEntity, ImageResource imageRes, String bgClass, String text) {
            this.filterEntity = filterEntity;
            anchor = new Anchor();
            add(anchor);
            Element span = DOM.createElement("span");
            span.setInnerText(text);
            DOM.insertChild(anchor.getElement(), span, 0);
            
            span = DOM.createElement("span");
            DOM.insertChild(anchor.getElement(), span, 0);
            span.addClassName(Utils.sandboxStyle.icon());
            span.addClassName(Utils.sandboxStyle.fa());

            if (bgClass != null) {
                Element b = DOM.createElement("b");
                b.addClassName(bgClass);
                DOM.insertChild(span, b, 0);
            }

            if (imageRes != null) {
                Image image = new Image(imageRes);
                image.setTitle(text);
                DOM.insertChild(span, image.getElement(), 0);
            }
        }
        
        protected void ensureDomEventHandlers() {
            anchor.addClickHandler(new ClickHandler() {
              @Override
              public void onClick(ClickEvent event) {
            	  if (FilterItem.this.active) {
            		  setValue(!FilterItem.this.value, true);
            	  }
              }
            });
        }

        @Override
        public HandlerRegistration addValueChangeHandler(
                ValueChangeHandler<Boolean> handler) {
            if (!valueChangeHandlerInitialized) {
                ensureDomEventHandlers();
                valueChangeHandlerInitialized = true;
              }
              return addHandler(handler, ValueChangeEvent.getType());
        }
        
        public E getFilterEntity() {
            return filterEntity;
        }
        
        public void setActive(boolean active) {
        	if (this.active != active) {
        		this.active = active;
        	}
        }

        public boolean isSendAnalytic() {
            return isSendAnalytic;
        }

        public void setSendAnalytic(boolean sendAnalytic) {
            isSendAnalytic = sendAnalytic;
        }

        @Override
        public Boolean getValue() {
            return this.value;
        }

        @Override
        public void setValue(Boolean value) {
            setValue(value, false);
        }

        @Override
        public void setValue(Boolean value, boolean fireEvents) {
            if (this.value != value) {
                this.value = value;
                if (this.value) {
                    addStyleName(Utils.sandboxStyle.active());
                } else {
                    isSendAnalytic=true;
                    removeStyleName(Utils.sandboxStyle.active());
                }
            }
            if (fireEvents) {
                ValueChangeEvent.fire(this, this.value);
            }
        }
    }


    
}
