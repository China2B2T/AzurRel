package org.china2b2t.azurmgr.http.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
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

public class ProfileHandler implements HttpHandler {
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
            httpExchange.sendResponseHeaders(500, "{\"err\":\"internal error (GeneralInfoHandler.java > 1)\"}".length());
            os.write("{\"err\":\"internal error (GeneralInfoHandler.java > 1)\"}".getBytes());
            os.close();
            return;
        }

        JSONObject json = null;
        try {
            json = new JSONObject(args);
        } catch(JSONException e) {
            httpExchange.sendResponseHeaders(500, "{\"err\":\"internal error (ProfileHandler.java > 2)\"}".length());
            os.write("{\"err\":\"internal error (ProfileHandler.java > 2)\"}".getBytes());
            os.close();
            return;
        }
        String token = null;
        try {
            token = json.getString("token");
        } catch(JSONException e) {
            httpExchange.sendResponseHeaders(500, "{\"err\":\"internal error (ProfileHandler.java > 2)\"}".length());
            os.write("{\"err\":\"internal error (ProfileHandler.java > 2)\"}".getBytes());
            os.close();
            return;
        }

        if (TokenMgr.validate(token)) {
            User user = TokenMgr.getUser(token);
            String nickname = user.nickname;
            response.append("{\"nickname\":\"" + nickname + "\"}");
        } else {
            response.append("{\"err\":\"unauthorized\"}");
        }
        httpExchange.sendResponseHeaders(200, response.toString().length());

        is.close();
        os.write(response.toString().getBytes());
        os.close();
    }
}
