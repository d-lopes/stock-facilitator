package de.dlopes.stocks.facilitator.ui.views;
        
import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = IndexView.VIEW_NAME)
public class IndexView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "index";

    private static final long serialVersionUID = -7819028369685823590L;

    @PostConstruct
    public void init() {
        setMargin(true);
        addComponent(new Label("Please use the button 'Load stock info' from above to show an overview of stocks"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}