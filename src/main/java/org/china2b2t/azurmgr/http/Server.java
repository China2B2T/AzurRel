package org.china2b2t.azurmgr.http;

import com.sun.net.httpserver.HttpServer;

import org.china2b2t.azurmgr.http.handlers.*;
import org.china2b2t.azurmgr.http.handlers.query.GeneralQuery;
import org.china2b2t.azurmgr.http.handlers.query.WhitelistQuery;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    public static HttpServer server;

    /**
     * Start the HTTP server
     * @param port
     */
    public static boolean startServer(int port) {
        synchronized (server) {
            try {
                server = HttpServer.create(new InetSocketAddress(port), 0);

                // Setting
                server.createContext("/api/auth/basic.action", new AuthHandler());
                server.createContext("/api/settings/whitelist.action", new WhitelistHandler());

                // Query
                server.createContext("/api/query/whitelist.action", new WhitelistQuery());
                server.createContext("/api/query/general.action", new GeneralQuery());

                // Index
                server.createContext("/", new IndexHandler());

                server.setExecutor(Executors.newFixedThreadPool(4));
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public static void stopServer(int delay) {
        synchronized (server) {
            server.stop(delay);
        }
    }
}