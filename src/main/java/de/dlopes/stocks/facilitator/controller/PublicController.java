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
package de.dlopes.stocks.facilitator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * This is how we handle our custom login screen. It has nothing to do with Vaadin at all.
 */
@Controller
public class PublicController {

    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    
    @Autowired
    ServerProperties serverProperties;

    @RequestMapping(value = PublicController.LOGIN_URL, method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "logged-out", required = false) String loggedOut) {
        ModelAndView modelAndView = new ModelAndView();
        if (error != null) {
            modelAndView.addObject("error", true);
        }
        if (loggedOut != null) {
            modelAndView.addObject("loggedOut", true);
        }
        modelAndView.addObject("contextPath", serverProperties.getContextPath());
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
