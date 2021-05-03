package org.china2b2t.azurmgr.socket;

import java.net.InetSocketAddress;

import org.bukkit.plugin.java.JavaPlugin;
import org.china2b2t.azurmgr.Main;
import org.china2b2t.azurmgr.api.ManTool;
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
    public void onStart() { instance = new Main().getInstance(); }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        instance.getLogger().info("New management connection: " + conn.getRemoteSocketAddress().getAddress().toString());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) { userLeave(conn); }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if (null != message && message.startsWith("tk_val")) {
            String tok = message.substring(6); // Token
            userJoin(conn, tok);//用户加入
            String token = Controller.getRandomToken();
            conn.send("token$"+token);
        } else if (null != message && message.startsWith("tk_revoke")) {
            userLeave(conn);
        }
        if(message != null && message.startsWith("operation_req")) {
            // U2FsdGVkX1++CgIiNDkBRFV8JerKuD/WzxH4WhY+GMWsty9sjB1xQRnJmnz4+avl/1YqK8KQMJJg2WR71hOm0yaSc3sxzEdoiuIzA6h0zD2Dkn6I2FawZxE5pymjOKZ7WJi1k7kFcXSd2Q==
            String operation = message.substring(13);
            int ret = ManTool.passConfigModify(operation.split(":")[0], operation.split(":")[1], operation.split(":")[2]);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
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
            Controller.addUser(userName.split(":")[0], conn);
        } else {
            conn.send("err$access_denied");
        }
    }
}