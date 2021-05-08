package org.china2b2t.azurmgr.http.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.china2b2t.azurmgr.http.utils.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GetSubItemsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream is = httpExchange.getRequestBody();
        Headers headers = httpExchange.getResponseHeaders();
        OutputStream os = httpExchange.getResponseBody();
        StringBuilder response = new StringBuilder();

        headers.set("Content-Type", "application/json; charset=utf8");
        headers.set("Server", "rio/0.1");

        if (httpExchange.getRequestMethod().equals("GET")) {
            httpExchange.sendResponseHeaders(200, "{\"err\":\"shouldn't use GET\"}".length());
            os.write("{\"err\":\"shouldn't use GET\"}".getBytes());
            os.close();
            return;
        }

        try {
            response.append(Streams.is2string(is));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        httpExchange.sendResponseHeaders(200, response.length());

        is.close();
        os.write(response.toString().getBytes());
        os.close();
    }
}