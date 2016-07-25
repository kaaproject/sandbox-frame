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

package org.kaaproject.kaa.sandbox.web.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;

public class ResizeSupport {
    
    public static void injectResizeSupportScript() {
        ScriptInjector.fromString(Utils.resources.resizeSupportScript().getText()).inject();
    }
    
    public static HandlerRegistration addOnResizeListener(final Element e, OnResizeListener listener) {
        final JavaScriptObject jso = addOnResize(e, listener);
        return new HandlerRegistration() {
            @Override
            public void removeHandler() {
                removeOnResize(e, jso);
            }
        };
    }
    
    private static native JavaScriptObject addOnResize(Element e, OnResizeListener listener) /*-{
        myResizeFn = function(){
            listener.@org.kaaproject.kaa.sandbox.web.client.util.ResizeSupport.OnResizeListener::onResize(Lcom/google/gwt/dom/client/Element;)(e);
        };
        addResizeListener(e, myResizeFn);
        return myResizeFn;
    }-*/;
    
    private static native void removeOnResize(Element e, JavaScriptObject listener) /*-{
        removeResizeListener(e, listener);
    }-*/;
    
    public static interface OnResizeListener {
        
        void onResize(Element e);
        
    }

}
