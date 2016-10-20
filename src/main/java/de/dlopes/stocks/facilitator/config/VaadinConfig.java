/*******************************************************************************
 * Copyright (c) 2016 Dominique Lopes.
 * All rights reserved. 
 *
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Dominique Lopes - initial API and implementation
 *******************************************************************************/
package de.dlopes.stocks.facilitator.config;

import javax.servlet.ServletException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.stereotype.Component;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessagesProvider;
import com.vaadin.spring.server.SpringVaadinServlet;

import de.dlopes.stocks.facilitator.StockFacilitatorApplication;
import de.dlopes.stocks.facilitator.controller.PublicController;

@Component
public class VaadinConfig extends SpringBootServletInitializer {

    /**
     * provide an adapted SpringVaadingServlet with slightly modified configuration
     * regarding HTTP session expiration and showing of messages 
     */
    @Component("vaadinServlet")
    public class MyVaadinServlet extends SpringVaadinServlet {

        private static final long serialVersionUID = -2423927501277L;
        
        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();
            getService().setSystemMessagesProvider((SystemMessagesProvider) systemMessagesInfo -> {
                CustomizedSystemMessages messages = new CustomizedSystemMessages();
                // Don't show any messages, redirect immediately to the session expired URL
                messages.setSessionExpiredNotificationEnabled(false);
                // Force a logout to also end the HTTP session and not only the Vaadin session
                messages.setSessionExpiredURL(PublicController.LOGOUT_URL);
                // Don't show any message, reload the page instead
                messages.setCommunicationErrorNotificationEnabled(false);
                return messages;
            });
        }
        
    }

    /** 
     * configure the application for packaging as deployable war  
     */ 
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(StockFacilitatorApplication.class);
    }

}