package de.dlopes.stocks.facilitator.ui.views;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.dlopes.stocks.facilitator.config.ConfigurationSettings;
import de.dlopes.stocks.facilitator.data.StockInfo;
import de.dlopes.stocks.facilitator.data.StockInfoRepository;
import de.dlopes.stocks.facilitator.services.StockDataCollector;

@SpringView(name = HomeView.VIEW_NAME)
public class HomeView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "home";

    private static final long serialVersionUID = -6819028369685823590L;

    @Autowired
    private ConfigurationSettings cs;

    @Autowired
	private StockInfoRepository siRepo;

    private Table table;

    @PostConstruct
    public void init() {
        System.out.println("init() invoked");
        setMargin(true);
        
        table = new Table("Stock Info view:");
		table.setSizeFull();
		        
		// Define columns for the built-in container
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("WKN",  String.class, null);
        table.addContainerProperty("Bid",  Double.class, null);
        table.addContainerProperty("Ask",  Double.class, null);
        
		table.setVisibleColumns(new Object[] { "Name", "WKN", "Bid", "Ask"}); 
		
		StockDataCollector dataCollector = cs.getDataCollector();
		List<StockInfo> siList = dataCollector.getData();
		
		// save and load data to/from database
        siRepo.save(siList);
		siRepo.flush();
		siList = siRepo.findAll();
		
		// add data as rows into table structure
		for (StockInfo si : siList) {
		  // Create the table row.
          table.addItem(new Object[] {
                si.getId(), 
                si.getName(), 
                si.getWKN(), 
                si.getBid(), 
                si.getAsk()}, null);
		}

		// Show exactly the currently contained rows (items)
        table.setPageLength(table.size());
		
		addComponent(table);
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        System.out.println("enter() invoked");
        
    }
}