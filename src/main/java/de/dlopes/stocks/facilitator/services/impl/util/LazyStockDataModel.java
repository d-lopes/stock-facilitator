package de.dlopes.stocks.facilitator.services.impl.util;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import de.dlopes.stocks.facilitator.data.StockInfo;
import de.dlopes.stocks.facilitator.data.StockInfoRepository;

public class LazyStockDataModel extends LazyDataModel<StockInfo> {

	private static final long serialVersionUID = -8832831134966938627L;

    @Autowired
	private StockInfoRepository repo;

	private List<StockInfo> stocks;

	private StockInfo selected;

	@Override
	public List<StockInfo> load(int first, int pageSize, String sortField, SortOrder order, Map<String, Object> filters) {
		//this.searchCriteria.setCurrentPage(first / pageSize + 1);
		this.stocks = repo.findAll();//Hotels(searchCriteria, first, sortField, order.equals(SortOrder.ASCENDING));
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
		return stocks.size();
	}

	public StockInfo getSelected() {
		return selected;
	}

	public void setSelected(StockInfo selected) {
		this.selected = selected;
	}

	public int getCurrentPage() {
		return 1;
	}

	public int getPageSize() {
		return 1;
	}

}