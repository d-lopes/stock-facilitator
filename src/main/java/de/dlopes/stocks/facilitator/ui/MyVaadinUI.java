package de.dlopes.stocks.facilitator.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@Theme("valo")
@SpringUI
public class MyVaadinUI extends UI {

	private static final long serialVersionUID = -2429969248343588277L;

	@Override
	protected void init(VaadinRequest request) {
		setContent(new Label("Hello! I'm the root UI"));
	}

}
