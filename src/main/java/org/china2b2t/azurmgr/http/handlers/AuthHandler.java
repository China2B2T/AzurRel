package org.china2b2t.azurmgr.http.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.china2b2t.azurmgr.http.model.User;
import org.china2b2t.azurmgr.http.utils.Streams;
import org.china2b2t.azurmgr.http.utils.TokenMgr;
import org.china2b2t.azurmgr.remote.Validate;
import org.json.JSONObject;

public class AuthHandler implements HttpHandler {
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
            httpExchange.sendResponseHeaders(500, "{\"err\":\"internal error (GetListHandler.java > 1)\"}".length());
            os.write("{\"err\":\"internal error (GetListHandler.java > 1)\"}".getBytes());
            os.close();
            return;
        }

        JSONObject json = new JSONObject(args);
        String username = json.getString("username");
        String password = json.getString("password");

        if(Validate.validate(username, password)) {
            // Do stuff here
            User user = new User(username, "webmaster@china2b2t.org", System.currentTimeMillis() + 12000000);
            String tmpTk = TokenMgr.newToken(user);
            response.append("{\"status\":0,\"token\":\"" + tmpTk + "\"}");
        } else {
            response.append("{\"err\":\"unauthorized\"}");
        }
        httpExchange.sendResponseHeaders(200, response.toString().length());

        is.close();
        os.write(response.toString().getBytes());
        os.close();
    }
}
