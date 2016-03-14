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

package org.kaaproject.kaa.sandbox.web.client.util;

import java.io.Serializable;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;


public class DomainValidator implements Serializable {

    private static final long serialVersionUID = -4407125112880174009L;

    private static final String DOMAIN_LABEL_REGEX = "(^[a-zA-Z0-9-]{1,61}$|^[a-zA-Z0-9-]{1,61}[a-zA-Z0-9](?:\\.[a-zA-Z]{2,})+$)";

    private static final DomainValidator DOMAIN_VALIDATOR = new DomainValidator();
    private final RegExp domainRegex =
            RegExp.compile(DOMAIN_LABEL_REGEX);

    public static DomainValidator getInstance() {
        return DOMAIN_VALIDATOR;
    }

    private DomainValidator() {
    }

    public boolean isValid(String domain) {
        if (domain == null) {
            return false;
        }
        MatchResult matcher = domainRegex.exec(domain);
        if (matcher == null) {
            return false;
        }
        int count = matcher.getGroupCount();
        String[] groups = new String[count];
        for (int j = 0; j < count; j++) {
            groups[j] = matcher.getGroup(j + 1);
        }
        
        if (groups != null && groups.length > 0) {
            return true;
        } 
        return false;
    }

}