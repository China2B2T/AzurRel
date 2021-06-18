package org.china2b2t.azurmgr.http.handlers.query;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.china2b2t.azurmgr.Main;
import org.china2b2t.azurmgr.http.utils.Streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class WhitelistQuery implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        OutputStream os = exchange.getResponseBody();
        Headers headers = exchange.getResponseHeaders();

        headers.set("Content-Type", "application/json; charset=utf8");
        headers.set("Server", "rio/0.1");

        StringBuilder outSb = new StringBuilder();

        outSb.append("[");
        Set<OfflinePlayer> whSet = Bukkit.getWhitelistedPlayers();

        for (var p : whSet) {
            outSb.append(p.getName() + ",");
        }

        outSb.deleteCharAt(outSb.length() - 1);
        outSb.append("]");

        String content = outSb.toString();

        exchange.sendResponseHeaders(200, content.length());
        os.write(content.getBytes());
        os.close();
    }
}
