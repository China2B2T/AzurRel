package org.china2b2t.azurmgr.http.handlers;

import org.json.JSONObject;

public class PriorHandler extends Handler {
    public PriorHandler() {
        super(true);
    }

    @Override
    public StringBuilder getContent(JSONObject json) {
        if (json.has("add")) {
            //
        }
    }
}
