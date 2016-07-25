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

package org.kaaproject.kaa.sandbox.web.servlet;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtils {

    public static void prepareDisposition(HttpServletRequest request, HttpServletResponse response, String fileName) {
        String userAgent = request.getHeader("user-agent");
        boolean isInternetExplorer = (userAgent.indexOf("MSIE") > -1);

        try {
            byte[] fileNameBytes = fileName.getBytes((isInternetExplorer) ? ("windows-1250") : ("utf-8"));
            String dispositionFileName = "";
            for (byte b: fileNameBytes) dispositionFileName += (char)(b & 0xff);

            String disposition = "attachment; filename=\"" + dispositionFileName + "\"";
            response.setHeader("Content-disposition", disposition);
        } catch(UnsupportedEncodingException ence) {
           //
        }
  }
}
