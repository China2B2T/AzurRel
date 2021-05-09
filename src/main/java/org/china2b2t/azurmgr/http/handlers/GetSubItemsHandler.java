package org.china2b2t.azurmgr.http.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.china2b2t.azurmgr.api.ManTool;
import org.china2b2t.azurmgr.http.handlers.models.BGResponse;
import org.china2b2t.azurmgr.http.handlers.models.PluginList;
import org.china2b2t.azurmgr.http.handlers.models.Query;
import org.china2b2t.azurmgr.http.handlers.models.SubSettings;
import org.china2b2t.azurmgr.http.utils.*;
import org.china2b2t.azurmgr.remote.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class GetSubItemsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream is = httpExchange.getRequestBody();
        Headers headers = httpExchange.getResponseHeaders();
        OutputStream os = httpExchange.getResponseBody();
        StringBuilder response = new StringBuilder();

        headers.set("Content-Type", "application/json; charset=utf8");
        headers.set("Server", "rio/0.1");

        // 拒绝GET请求
        if (httpExchange.getRequestMethod().equals("GET")) {
            httpExchange.sendResponseHeaders(403, "{\"err\":\"shouldn't use GET\"}".length());
            os.write("{\"err\":\"shouldn't use GET\"}".getBytes());
            os.close();
            return;
        }
        // 拒绝除了POST以外的请求
        if (!httpExchange.getRequestMethod().equals("POST")) {
            httpExchange.sendResponseHeaders(403, "{\"err\":\"unknown operation\"}".length());
            os.write("{\"err\":\"unknown operation\"}".getBytes());
            os.close();
            return;
        }

        String args = null;
        try {
            args = Streams.is2string(is);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if(args == null) {
            httpExchange.sendResponseHeaders(500, "{\"err\":\"internal error (GetListHandler.java > 1)\"}".length());
            os.write("{\"err\":\"internal error (GetListHandler.java > 1)\"}".getBytes());
            os.close();
            return;
        }
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Query query = gson.fromJson(args, Query.class);
        if(Validate.validate(query.getName(), query.getKey())) {
            // Do stuff here
            List<String> pList = ManTool.getKeysFromPlugin();
            SubSettings callback = new SubSettings(0, pList);
            String msag = gson.toJson(callback);
            response.append(msag);
        } else {
            BGResponse callback = new BGResponse(1, "unauthorized operation");
            String msag = gson.toJson(callback);
            response.append(msag);
        }
        httpExchange.sendResponseHeaders(200, response.length());

        is.close();
        os.write(response.toString().getBytes());
        os.close();
    }
}