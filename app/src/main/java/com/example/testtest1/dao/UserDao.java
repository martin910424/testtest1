package com.example.testtest1.dao;
import com.example.testtest1.entity.User;
import com.example.testtest1.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserDao {
    public boolean login(String username,String password){

        String sql = "select * from user where username = ? and password = ?";

        Connection  con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,username);
            pst.setString(2,password);

            if(pst.executeQuery().next()){

                return true;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }

        return false;
    }

    public boolean register(User user){

        String sql = "insert into user(name,username,password) values (?,?,?)";

        Connection  con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,user.getName());
            pst.setString(2,user.getUsername());
            pst.setString(3,user.getPassword());

            int value = pst.executeUpdate();

            if(value>0){
                return true;
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return false;
    }

    public User findUser(String username){

        String sql = "select * from user where username = ?";

        Connection  con = JDBCUtils.getConn();
        User user = null;
        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,username);

            ResultSet rs = pst.executeQuery();

            while (rs.next()){

                int id = rs.getInt(0);
                String namedb = rs.getString(1);
                String usernamedb = rs.getString(2);
                String passworddb  = rs.getString(3);
                user = new User(id,namedb,usernamedb,passworddb);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }

        return user;
    }
}
