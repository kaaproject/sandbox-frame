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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.kaaproject.avro.ui.gwt.client.widget.dialog.AvroUiDialog;
import org.kaaproject.kaa.sandbox.web.client.util.LogLevel;
import org.kaaproject.kaa.sandbox.web.client.util.Utils;

public class LogLevelDialog extends AvroUiDialog {

    private ListBox levelListBox;

    public static LogLevelDialog show(Listener listener) {
        LogLevelDialog dialog = new LogLevelDialog(listener);

        dialog.center();
        dialog.show();

        return dialog;
    }

    LogLevelDialog(final Listener listener){
        super(false, true);

        setText(Utils.constants.changeLogLevel());

        VerticalPanel root = new VerticalPanel();
        setWidget(root);

        FlexTable grid = new FlexTable();
        grid.setCellSpacing(15);
        grid.getColumnFormatter().setWidth(0, "115px");
        grid.getColumnFormatter().setWidth(1, "115px");

        Label label = new Label(Utils.constants.logLevel());

        levelListBox = new ListBox();
        levelListBox.setWidth("100%");
        for (LogLevel level: LogLevel.values()) {
            levelListBox.addItem(level.name());
        }

        grid.setWidget(0, 0, label);
        grid.setWidget(0, 1, levelListBox);
        root.add(grid);

        Button changeButton = new Button(Utils.constants.change(), new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
                if (listener != null) {
                    listener.onChangeLogLevel(levelListBox.getSelectedValue());
                }
            }
        });

        Button cancel = new Button(Utils.constants.cancel(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();

                if (listener != null) {
                    listener.onCancel();
                }
            }
        });

        this.addButton(changeButton);
        this.addButton(cancel);
    }

    public interface Listener {

        void onChangeLogLevel(String value);

        void onCancel();
    }
}
