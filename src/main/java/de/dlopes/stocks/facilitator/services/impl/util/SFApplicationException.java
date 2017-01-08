package de.dlopes.stocks.facilitator.services.impl.util;

public class SFApplicationException extends Exception {
    
    private static final long serialVersionUID = 1154145118464626971L;

    public SFApplicationException(String msg, Exception ex) {
        super(msg, ex);
    }

}
