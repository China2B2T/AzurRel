package org.china2b2t.azurmgr.http;

import com.sun.net.httpserver.HttpServer;

import org.china2b2t.azurmgr.http.handlers.AuthHandler;
import org.china2b2t.azurmgr.http.handlers.GeneralInfoHandler;
import org.china2b2t.azurmgr.http.handlers.GetOpHandler;
import org.china2b2t.azurmgr.http.handlers.IndexHandler;
import org.china2b2t.azurmgr.http.handlers.PingHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    /**
     * Start the HTTP server
     * @param port
     */
    public static boolean startServer(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/api/auth/basic.action", new AuthHandler());
            server.createContext("/api/general/info.action", new GeneralInfoHandler());
            server.createContext("/api/settings/op.action", new GetOpHandler());
            server.createContext("/api/ping.action", new PingHandler());
            server.createContext("/", new IndexHandler());
            server.setExecutor(Executors.newFixedThreadPool(4));
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}