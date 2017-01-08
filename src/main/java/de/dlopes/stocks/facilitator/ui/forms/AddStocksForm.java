package de.dlopes.stocks.facilitator.ui.forms;

import java.io.Serializable;

import lombok.Data;

@Data
public class AddStocksForm implements Serializable {

	private static final long serialVersionUID = 1764745848460626971L;
    
    private String url;
    
}
