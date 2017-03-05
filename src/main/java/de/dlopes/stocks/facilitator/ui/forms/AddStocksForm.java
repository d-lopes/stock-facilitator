package de.dlopes.stocks.facilitator.ui.forms;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import lombok.Data;
import de.dlopes.stocks.facilitator.config.ConfigurationSettings;
import de.dlopes.stocks.facilitator.services.FinanceDataExtractor;

@Data
public class AddStocksForm implements Serializable {

	private static final long serialVersionUID = 1764745848460626971L;
    
    @Autowired
    ConfigurationSettings config;
    
    private String url;
    private String listOfYahooSymbols;
 
    public void validateInput(FacesContext fc, UIComponent component, Object object) {
        FinanceDataExtractor finDataExtr = config.getFinanceDataExtractor();
	    if (!StringUtils.isEmpty(url) && !finDataExtr.isApplicable(url)) {
	       FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "URL not applicable", "URL '" + url + "' is not applicable for automatic processing.");
           FacesContext.getCurrentInstance().addMessage("messages", msg);
	    } else if (StringUtils.isEmpty(listOfYahooSymbols)) {
           FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No information provided", "Neither URL nor Yahoo Symbols where provided.");
           FacesContext.getCurrentInstance().addMessage("messages", msg);
	    }
    }
 
}
