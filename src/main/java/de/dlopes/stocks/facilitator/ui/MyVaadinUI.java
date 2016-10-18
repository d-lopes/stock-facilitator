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
import de.dlopes.stocks.facilitator.ui.views.IndexView;

@Theme(ValoTheme.THEME_NAME)
@SpringUI
//@Push(transport = Transport.WEBSOCKET_XHR) // Websocket would bypass the filter chain, Websocket+XHR works
public class MyVaadinUI extends UI implements ErrorHandler {

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

        buttons.addComponent(new Button("Load stock info", event -> {
            getNavigator().navigateTo(HomeView.VIEW_NAME);
        }));
        buttons.addComponent(new Button("Logout", event -> {
            // Let Spring Security handle the logout by redirecting to the logout URL
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
        
        navigator.navigateTo(IndexView.VIEW_NAME);
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
