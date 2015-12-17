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

package org.kaaproject.kaa.sandbox.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LogsServlet extends HttpServlet {

    private static final int BYTES_DOWNLOAD = 1024;

    private static final String ARCHIVE_NAME = "sandbox_logs.tar.gz";
    private static final String ARCHIVE_LOCATION = "/home/kaa/" + ARCHIVE_NAME;

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(LogsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        File archFile = new File(ARCHIVE_LOCATION);
        if (archFile.exists()) {
            try (InputStream is = new FileInputStream(ARCHIVE_LOCATION);
                 OutputStream os = response.getOutputStream()) {

                response.setContentType("application/x-gzip");
                response.setHeader("Content-Disposition", "attachment;filename=" + ARCHIVE_NAME);

                int read = 0;
                byte[] bytes = new byte[BYTES_DOWNLOAD];
                while ((read = is.read(bytes)) != -1) {
                    os.write(bytes, 0, read);
                }
            } catch (Exception ex) {
                logger.error("Unexpected error in LogsServlet.sendArchive: ", ex);
                throw new RuntimeException(ex);
            }
        } else {
            logger.error("Unexpected error in LogsServlet.doGet: log directory " + ARCHIVE_LOCATION + " doesn't exists.");
            throw new RuntimeException("Unexpected error in LogsServlet.doGet: log directory " + ARCHIVE_LOCATION + " doesn't exists.");
        }
    }
}
