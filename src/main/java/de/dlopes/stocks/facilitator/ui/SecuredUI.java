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
package de.dlopes.stocks.facilitator.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.dlopes.stocks.facilitator.controller.PublicController;
import de.dlopes.stocks.facilitator.ui.views.AccessDeniedView;
import de.dlopes.stocks.facilitator.ui.views.ErrorView;
import de.dlopes.stocks.facilitator.ui.views.HomeView;

@Theme(ValoTheme.THEME_NAME)
@SpringUI(path = SecuredUI.PATH)
public class SecuredUI extends UI implements ErrorHandler {

    public static final String PATH = "/secured";
    
	private static final long serialVersionUID = -2429969248343588277L;

    @Autowired
    SpringViewProvider viewProvider;
    
    @Autowired
    ErrorView errorView;
    
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        layout.addComponent(buttons);
        layout.setComponentAlignment(buttons, Alignment.TOP_RIGHT);
        
        /*buttons.addComponent(new Button("Load stock info", event -> {
            getNavigator().navigateTo(HomeView.VIEW_NAME);
        }));*/
        buttons.addComponent(new Button("Logout", event -> {
            getPage().setLocation(PublicController.LOGOUT_URL);
        }));
        
        Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        layout.addComponent(viewContainer);
        layout.setExpandRatio(viewContainer, 1.0f);

        setContent(layout);
        getPage().setTitle("Stock Facilitator");
        setErrorHandler(this);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(errorView);
        viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
        
        navigator.navigateTo(HomeView.VIEW_NAME);
	}

    public void error(com.vaadin.server.ErrorEvent event) {
        Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
        if (t instanceof AccessDeniedException) {
            Notification.show("You do not have permission to perform this operation",
                Notification.Type.WARNING_MESSAGE);
        } else {
            DefaultErrorHandler.doDefault(event);
        }
    }
}
