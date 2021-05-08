package org.china2b2t.azurmgr.http;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    /**
     * Start the HTTP server
     * @param port
     */
    public void startServer(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            // server.createContext("/authorize", new PostHandler());
            // server.createContext("/modify", new ConfigHandler());
            server.setExecutor(Executors.newFixedThreadPool(4));
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}