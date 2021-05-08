package org.china2b2t.azurmgr.http.handlers.models;

public class BGResponse {
    public final int status;
    public final String msg;

    public BGResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String toString() {
        return "[RESPONSE: code=" + this.status + ", msg=" + this.msg + "]";
    }
}