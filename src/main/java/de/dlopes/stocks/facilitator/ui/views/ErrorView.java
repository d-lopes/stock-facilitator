package de.dlopes.stocks.facilitator.ui.views;

import javax.annotation.PostConstruct;

import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;

@Component // No SpringView annotation because this view can not be navigated to
@UIScope
public class ErrorView extends VerticalLayout implements View {

    private static final long serialVersionUID = -5838491679685423590L;

    private Label errorLabel;

    @PostConstruct
    public void init() {
        setMargin(true);
        errorLabel = new Label();
        errorLabel.addStyleName(ValoTheme.LABEL_FAILURE);
        errorLabel.setSizeUndefined();
        addComponent(errorLabel);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        errorLabel.setValue(String.format("No such view: %s", event.getViewName()));
    }
}
