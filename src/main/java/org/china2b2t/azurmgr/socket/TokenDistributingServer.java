package org.china2b2t.azurmgr.socket;

import java.net.InetSocketAddress;

import org.bukkit.plugin.java.JavaPlugin;
import org.china2b2t.azurmgr.Main;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class TokenDistributingServer extends WebSocketServer {
    public JavaPlugin instance;

    public TokenDistributingServer(int port) {
        super(new InetSocketAddress(port));
    }

    public TokenDistributingServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onStart() {
        instance = new Main().getInstance();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        instance.getLogger().info("New management connection: " + conn.getRemoteSocketAddress().getAddress().toString());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        //断开连接时候触发代码
        userLeave(conn);
        System.out.println(reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(message);
        if(null != message && message.startsWith("online")){
            String userName = message.replaceFirst("online", message);//用户名
            userJoin(conn,userName);//用户加入
        }else if(null != message && message.startsWith("offline")){
            userLeave(conn);
        }

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //错误时候触发的代码
        System.out.println("on error");
        ex.printStackTrace();
    }
    /**
     * 去除掉失效的websocket链接
     * @param conn
     */
    private void userLeave(WebSocket conn){
        Controller.removeUser(conn);
    }
    /**
     * 将websocket加入用户池
     * @param conn
     * @param userName
     */
    private void userJoin(WebSocket conn,String userName){
        Controller.addUser(userName, conn);
    }

}