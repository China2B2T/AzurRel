package org.china2b2t.azurmgr.http.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.china2b2t.azurmgr.Main;
import org.china2b2t.azurmgr.config.Admin;
import org.china2b2t.azurmgr.http.model.User;
import org.china2b2t.azurmgr.http.utils.Streams;
import org.china2b2t.azurmgr.http.utils.TokenMgr;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class WhitelistHandler implements HttpHandler {
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
            e1.printStackTrace();
            httpExchange.sendResponseHeaders(500, "{\"err\":\"internal error (WhitelistHandler.java > 1)\"}".length());
            os.write("{\"err\":\"internal error (WhitelistHandler.java > 1)\"}".getBytes());
            os.close();
            return;
        }

        JSONObject json = null;
        try {
            json = new JSONObject(args);
        } catch(JSONException e) {
            httpExchange.sendResponseHeaders(500, "{\"err\":\"internal error (WhitelistHandler.java > 2)\"}".length());
            os.write("{\"err\":\"internal error (WhitelistHandler.java > 2)\"}".getBytes());
            os.close();
            return;
        }
        String token = null;
        try {
            token = json.getString("token");
        } catch(JSONException e) {
            // e.printStackTrace();
            httpExchange.sendResponseHeaders(500, "{\"err\":\"unauthorized\"}".length());
            os.write("{\"err\":\"unauthorized\"}".getBytes());
            os.close();
            return;
        }

        if (TokenMgr.validate(token)) {
            if (json.has("add")) {
                try {
                    for (Iterator<Object> it = json.getJSONArray("add").iterator(); it.hasNext(); ) {
                        Object player = it.next();
                        if (player instanceof String) {
                            ((String)player).replace("\n", "");
                            BukkitScheduler scheduler = Bukkit.getScheduler();
                            scheduler.scheduleSyncDelayedTask(Main.instance, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + player), 0L);
                        }
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }

            if (json.has("remove")) {
                try {
                    for (Iterator<Object> it = json.getJSONArray("remove").iterator(); it.hasNext(); ) {
                        Object player = it.next();
                        if (player instanceof String) {
                            ((String)player).replace("\n", "");
                            BukkitScheduler scheduler = Bukkit.getScheduler();
                            scheduler.scheduleSyncDelayedTask(Main.instance, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist remove " + player), 0L);
                        }
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            response.append("{\"err\":\"unauthorized\"}");
        }
        httpExchange.sendResponseHeaders(200, response.toString().length());

        is.close();
        os.write(response.toString().getBytes());
        os.close();
    }
}
