package de.dlopes.stocks.facilitator.ui.views;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

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

    @Autowired
    private EntityManager em;

    private HorizontalLayout hints;
    private Table table;
    private JPAContainer<StockInfo> stockinfo;
    
    /**
     * build the view with a basic setup of the view components
     */
    @PostConstruct
    public void init() {
        setMargin(true);
        
        hints = new HorizontalLayout();
        Label label = new Label("Stock info not loaded yet. Click here to refresh stock information:");
	    Button link = new Button("Load", event -> {
	        loadStockInfo();
	    });
	    link.addStyleName(ValoTheme.BUTTON_LINK);
	    hints.addComponent(label);
	    hints.addComponent(link);
	    addComponent(hints);
	    
	    // Create a persistent stock info container
        stockinfo = JPAContainerFactory.make(StockInfo.class, em);
        stockinfo.sort(new String[]{"name", "price"}, new boolean[]{true, true});
		
		// bind it to a table
		table = new Table("Stock Info view:", stockinfo);
		table.setVisibleColumns(new Object[] { "id", "name", "WKN", "ISIN", "price", "bid", "ask", "changePercentage", "changeAbsolute", "time"});
	    table.setSizeFull();
		table.setVisible(false);

		// add the table to the view
		addComponent(table);
        
    }

    /**
     * initialize the view according to the 
     */
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!CollectionUtils.isEmpty(siRepo.findAll())) {
		    loadStockInfo();
		}
    }

    /*
     * load the stock information from our services
     */
    protected void loadStockInfo() {
        
        StockDataCollector dataCollector = cs.getDataCollector();
	    List<StockInfo> siList = dataCollector.getData();
	
		// save and load data to/from database via Spring Data JPA repository
        siRepo.save(siList);
		siRepo.flush();

		// Show exactly the currently contained rows (items)
        stockinfo.refresh();
        table.refreshRowCache();
        table.setPageLength(siList.size());
        
        // update the view to show the table instead of the "load data" hint and link
        table.setVisible(true);
        hints.setVisible(false);
    }

}