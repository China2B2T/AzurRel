package org.china2b2t.azurmgr.sql;

import java.sql.*;

import org.china2b2t.azurmgr.Main;

public class Connector {
    String host;
    String username;
    String password;
    String table;
    Connection conn;

    public Connector() {
        String host = Main.instance.getConfig().getString("mysql.host");
        this.host = host;
        String username = Main.instance.getConfig().getString("mysql.username");
        this.username = username;
        String password = Main.instance.getConfig().getString("mysql.password");
        this.password = password;
        String table = Main.instance.getConfig().getString("mysql.table");
        this.table = table;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.host, this.username, this.password);

        } catch(ClassNotFoundException e) {
            throw new IllegalStateException("Cannot create MySQL driver");
        } catch(SQLException e) {
            throw new IllegalStateException("Cannot create MySQL connection");
        }
    }

    // insert and query here!
}
