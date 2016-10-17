package de.dlopes.stocks.facilitator.ui.views;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = StockInfoView.VIEW_NAME)
public class StockInfoView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "stock-info";

    private static final long serialVersionUID = -6819028369685823590L;

    @PostConstruct
    public void init() {
        setMargin(true);
        addComponent(new Label("Stock Info view"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}