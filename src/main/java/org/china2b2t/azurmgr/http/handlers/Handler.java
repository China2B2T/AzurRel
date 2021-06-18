package org.china2b2t.azurmgr.http.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.china2b2t.azurmgr.http.utils.Streams;
import org.china2b2t.azurmgr.http.utils.TokenMgr;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.security.DenyAll;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * Template for handler context
 *
 * @author netease
 * @since 1.1
 */
public abstract class Handler implements HttpHandler {
    private boolean privilege = false;

    /**
     * Constructor for handler template
     *
     * @param requiresPrivilege
     */
    public Handler(boolean requiresPrivilege) {
        this.privilege = requiresPrivilege;
    }

    @DenyAll
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream is = httpExchange.getRequestBody();
        Headers headers = httpExchange.getResponseHeaders();
        OutputStream os = httpExchange.getResponseBody();
        StringBuilder response = new StringBuilder();

        var h = getHeaders();
        for (var k : h.keySet()) {
            headers.set(k, h.get(k));
        }

        String args = null;

        try {
            args = Streams.is2string(is);
        } catch (Exception e1) {
            // Ignore
        }

        JSONObject json = null;

        try {
            json = new JSONObject(args);
        } catch(JSONException e) {
            // Ignore
        }

        String token = null;

        try {
            token = json.getString("token");
        } catch(JSONException e) {
            // e.printStackTrace();
            if (this.privilege) {
                httpExchange.sendResponseHeaders(500, "{\"err\":\"unauthorized\"}".length());
                os.write("{\"err\":\"unauthorized\"}".getBytes());
                os.close();
                return;
            }
        }

        if (this.privilege) {
            if (TokenMgr.validate(token)) {
                response = getContent(json);
            } else {
                response.append("{\"err\":\"unauthorized\"}");
            }
        } else {
            response = getContent(json);
        }

        httpExchange.sendResponseHeaders(200, response.toString().length());

        is.close();
        os.write(response.toString().getBytes());
        os.close();
    }

    /**
     * Get http headers
     *
     * @return Headers
     */
    public Hashtable<String, String> getHeaders() {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("Content-Type", "application/json; charset=utf8");
        hashtable.put("Server", "rio/1.0");

        return hashtable;
    }

    /**
     * Get http content
     *
     * @param json
     * @return Content
     */
    public abstract StringBuilder getContent(JSONObject json);
}
