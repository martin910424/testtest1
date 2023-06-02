package com.example.testtest1.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    static {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConn() {
        Connection  conn = null;
        try {
            conn= DriverManager.getConnection("jdbc:mysql://192.168.137.1:3306/member","root","");
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn){
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
