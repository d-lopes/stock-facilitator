package de.dlopes.stocks.facilitator.ui.forms;

import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import de.dlopes.stocks.facilitator.data.StockInfo;
import de.dlopes.stocks.facilitator.data.StockInfoRepository;
import de.dlopes.stocks.facilitator.services.StockInfoService;
import de.dlopes.stocks.facilitator.services.impl.StockInfoServiceImpl;

public class LazyStockDataModel extends LazyDataModel<StockInfo> {

	private static final long serialVersionUID = -8832831134966938627L;

    // service needs to be declared as 'transient' in order to avoid Serialization Issue 
    // with org.springframework.dao.support.PersistenceExceptionTranslationInterceptor
    private transient StockInfoService stockInfoService;

	private List<StockInfo> stocks;

	private StockInfo selected;

    @Autowired
	public void setStockInfoService(StockInfoService stockInfoService) {
		this.stockInfoService = stockInfoService;
	}

	@Override
	public List<StockInfo> load(int first, int pageSize, String sortField, SortOrder order, Map<String, Object> filters) {
		// let's keep it easy for now: we neglect any pagination, sorting or filters
		this.stocks = stockInfoService.findAll();
		return stocks;
	}

	@Override
	public StockInfo getRowData(String rowKey) {
		for (StockInfo stock : this.stocks){
			if (stock.getIsin().equals(rowKey)) {
				return stock;
			}
 		}
		return null;
	}

	@Override
	public Object getRowKey(StockInfo stock) {
		return stock.getIsin();
	}

	@Override
	public int getRowCount() {
		return stockInfoService.count();
	}

	public StockInfo getSelected() {
		return selected;
	}

	public void setSelected(StockInfo selected) {
		this.selected = selected;
	}

	public int getCurrentPage() {
		// let's keep it easy for now: there is only one page
		return 1;
	}

	public int getPageSize() {
	    // let's keep it easy for now: page size = row count
		return getRowCount();
	}

}