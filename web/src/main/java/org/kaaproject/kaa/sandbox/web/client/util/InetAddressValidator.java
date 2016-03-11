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

public class InetAddressValidator implements Serializable {

    private static final long serialVersionUID = -919201640201914789L;

    private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";

    private static final InetAddressValidator VALIDATOR = new InetAddressValidator();

    private final RegExp ipv4Pattern = RegExp.compile(IPV4_REGEX);

    public static InetAddressValidator getInstance() {
        return VALIDATOR;
    }

    public boolean isValid(String inetAddress) {
        return isValidInet4Address(inetAddress);
    }

    public boolean isValidInet4Address(String inet4Address) {
        // verify that address conforms to generic IPv4 format
        if (inet4Address == null) {
            return false;
        }

        MatchResult matcher = ipv4Pattern.exec(inet4Address);
        if (matcher == null) {
            return false;
        }
        int count = matcher.getGroupCount();
        String[] groups = new String[count];
        for (int j = 0; j < count; j++) {
            groups[j] = matcher.getGroup(j + 1);
        }

        // verify that address subgroups are legal
        for (int i = 0; i <= 3; i++) {
            String ipSegment = groups[i];
            if (ipSegment == null || ipSegment.length() <= 0) {
                return false;
            }

            int iIpSegment = 0;

            try {
                iIpSegment = Integer.parseInt(ipSegment);
            } catch (NumberFormatException e) {
                return false;
            }

            if (iIpSegment > 255) {
                return false;
            }

        }

        return true;
    }
}