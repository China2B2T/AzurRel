package org.china2b2t.azurmgr.http.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.china2b2t.azurmgr.http.Model.User;
import org.china2b2t.azurmgr.http.handlers.request.Auth;
import org.china2b2t.azurmgr.http.handlers.response.Err;
import org.china2b2t.azurmgr.http.handlers.response.Token;
import org.china2b2t.azurmgr.http.utils.Streams;
import org.china2b2t.azurmgr.http.utils.TokenMgr;
import org.china2b2t.azurmgr.remote.Validate;

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
        Auth auth = gson.fromJson(args, Auth.class);

        if(Validate.validate(auth.username, auth.password)) {
            // Do stuff here
            User user = new User(auth.username, "webmaster@china2b2t.org", System.currentTimeMillis() + 12000000);
            String tmpTk = TokenMgr.newToken(user);
            Token callback = new Token(tmpTk);
            String msag = gson.toJson(callback);
            response.append(msag);
        } else {
            Err callback = new Err(1, "wrong authorize information");
            String msag = gson.toJson(callback);
            response.append(msag);
        }
        httpExchange.sendResponseHeaders(200, response.length());

        is.close();
        os.write(response.toString().getBytes());
        os.close();
    }
}
