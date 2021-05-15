package org.china2b2t.azurmgr.http.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.china2b2t.azurmgr.Main;
import org.china2b2t.azurmgr.http.utils.Streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IndexHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        OutputStream os = exchange.getResponseBody();
        Headers headers = exchange.getResponseHeaders();

        headers.set("Content-Type", "text/html; charset=utf8");
        headers.set("Server", "nginx/1.12.2");

        StringBuilder outSb = new StringBuilder();
        String args = null;

        try {
            args = Streams.is2string(is);
        } catch (Exception e1) {
            e1.printStackTrace();
            exchange.sendResponseHeaders(500, "{\"err\":\"internal error (IndexHandler.java > 1)\"}".length());
            os.write("{\"err\":\"internal error (IndexHandler.java > 1)\"}".getBytes());
            os.close();
            return;
        }

        // SO WHAT DOES ARGS DO?
        outSb.append("<html><head><title>Azur Web Server on Bukkit</title><style>*{ font-family: Consolas }</style></head><body><center><h2>Azur Web Server</h2></center><hr>Powered by Rabbit0w0 and China2B2T development team<br>Running on bukkit " + Main.instance.getServer().getBukkitVersion());
        outSb.append("<br/><code>Your args: " + args + "</code></body><html>");
        String content = outSb.toString();

        exchange.sendResponseHeaders(200, content.length());
        os.write(content.getBytes());
        os.close();
    }
}
