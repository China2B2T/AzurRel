package org.china2b2t.azurmgr.http.handlers.models;

import java.util.Arrays;
import java.util.List;

public class SubSettings {
    public final int status;
    public List<String> list = Arrays.asList("undefined");

    public SubSettings(int status, List<String> list) {
        this.status = status;
        this.list = list;
    }
}