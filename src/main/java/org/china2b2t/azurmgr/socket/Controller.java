package org.china2b2t.azurmgr.socket;

import java.security.SecureRandom;
import java.util.*;

import org.china2b2t.azurmgr.Main;
import org.java_websocket.WebSocket;

/**
 * 连接池控制器
 */
public class Controller {
    private static Map<WebSocket, String> wsUserMap = new HashMap<WebSocket, String>();
    protected static Map<String, WebSocket> tkMap = new HashMap<String, WebSocket>();

    /**
     * 通过websocket连接获取其对应的用户
     * 
     * @param conn
     * @return
     */
    public static String getUserByWs(WebSocket conn) {
        return wsUserMap.get(conn);
    }

    /**
     * 根据userName获取WebSocket,这是一个list,此处取第一个
     * 因为有可能多个websocket对应一个userName（但一般是只有一个，因为在close方法中，我们将失效的websocket连接去除了）
     * 
     * @param user
     */
    public static WebSocket getWsByUser(String userName) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String cuser = wsUserMap.get(conn);
                if (cuser.equals(userName)) {
                    return conn;
                }
            }
        }
        return null;
    }

    /**
     * 向连接池中添加连接
     * 
     * @param inbound
     */
    public static void addUser(String userName, WebSocket conn) {
        conn.send("inf$ok");
        wsUserMap.put(conn, userName);
    }

    /**
     * 获取所有连接池中的用户，因为set是不允许重复的，所以可以得到无重复的user数组
     * 
     * @return
     */
    public static Collection<String> getOnlineUser() {
        List<String> setUsers = new ArrayList<String>();
        Collection<String> setUser = wsUserMap.values();
        for (String u : setUser) {
            setUsers.add(u);
        }
        return setUsers;
    }

    /**
     * 移除连接池中的连接
     * 
     * @param inbound
     */
    public static boolean removeUser(WebSocket conn) {
        if (wsUserMap.containsKey(conn)) {
            wsUserMap.remove(conn); // 移除连接
            return true;
        } else {
            return false;
        }
    }

    /**
     * 向特定的用户发送数据
     * 
     * @param user
     * @param message
     */
    public static void sendMessageToUser(WebSocket conn, String message) {
        if (null != conn && null != wsUserMap.get(conn)) {
            conn.send(message);
        }
    }

    /**
     * 向所有的用户发送消息
     * 
     * @param message
     */
    public static void sendMessageToAll(String message) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String user = wsUserMap.get(conn);
                if (user != null) {
                    conn.send(message);
                }
            }
        }
    }

    /**
     * 生成Token
     * @return 24位Token
     */
    public static String getRandomToken() {
        String codes = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = null;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (Exception e) {
            new Main().getLogger().info("Cannot get Random instance");
            e.printStackTrace();
        }
        StringBuilder randomStr = new StringBuilder();
        for (int i = 0; i < 24; i++) {
            randomStr.append(codes.charAt(random.nextInt(62)));
        }
        return randomStr.toString();
    }

    /**
     * 校验令牌
     * 
     * @param conn
     * @param token
     * @return
     */
    public static boolean validateToken(WebSocket conn, String token) {
        if(!tkMap.containsKey(token) || !tkMap.containsValue(conn)) {
            return false;
        } else if (tkMap.get(token) == conn) {
            return true;
        }
        return false;
    }

    /**
     * 添加令牌
     * 
     * @param conn
     * @param token
     */
    public static void addToken(WebSocket conn, String token) {
        tkMap.put(token, conn);
    }

    /**
     * 吊销令牌
     * 
     * @param token
     */
    public static void revokeToken(String token) {
        tkMap.remove(token);
    }

    /**
     * 吊销令牌
     * 
     * @param conn
     */
    public static void revokeToken(WebSocket conn) {
        Set<String> st = tkMap.keySet();
        synchronized(st) {
            for(String tok : st) {
                if(tkMap.get(tok) == conn) {
                    tkMap.remove(tok);
                    break;
                }
            }
        }
    }
}
