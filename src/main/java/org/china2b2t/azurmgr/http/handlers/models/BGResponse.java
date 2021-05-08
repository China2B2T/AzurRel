package org.china2b2t.azurmgr.http.handlers.models;

public class BGResponse {
    public final int code;
    public final String msg;

    public BGResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String toString() {
        return "[RESPONSE: code=" + this.code + ", msg=" + this.msg + "]";
    }
}