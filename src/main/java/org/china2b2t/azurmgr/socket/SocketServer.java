package org.china2b2t.azurmgr.socket;

import java.net.InetSocketAddress;

import org.bukkit.plugin.java.JavaPlugin;
import org.china2b2t.azurmgr.Main;
import org.china2b2t.azurmgr.remote.Validate;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class SocketServer extends WebSocketServer {
    public JavaPlugin instance;

    public SocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    public SocketServer(InetSocketAddress address) {
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
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if (null != message && message.startsWith("tk_val")){
            String tok = message.substring(6); // Token
            userJoin(conn, tok);//用户加入
        } else if (null != message && message.startsWith("tk_revoke")){
            userLeave(conn);
        }

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //错误时候触发的代码
        System.out.println("Management tool caught an error!");
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
    private void userJoin(WebSocket conn, String userName){
        if(Validate.validate(userName.split(":")[0], userName.split(":")[1])) {
            Controller.addUser(userName, conn);
        } else {
            conn.send("err$access_denied");
        }
    }

}