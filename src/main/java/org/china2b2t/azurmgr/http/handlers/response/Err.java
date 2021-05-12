package org.china2b2t.azurmgr.http.handlers.response;

public class Err {
    public int err;
    public String reason;

    /**
     * The default model of error response
     * 
     * @param err
     * @param reason
     */
    public Err(int err, String reason) {
        this.err = err;
        this.reason = reason;
    }
}
