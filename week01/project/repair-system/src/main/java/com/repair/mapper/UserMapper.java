package com.repair.mapper;

import com.repair.pojo.User;
import com.repair.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserMapper {
    // 新增用户（注册）
    public void addUser(User user) {
        Connection conn = JDBCUtils.getConn();
        String sql = "insert into user(account,password,role,dorm_no) values(?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getAccount());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getDormNo());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 根据账号查用户（登录）
    public User getUserByAccount(String account) {
        Connection conn = JDBCUtils.getConn();
        String sql = "select * from user where account=?";
        User user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setDormNo(rs.getString("dorm_no"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // 修改密码
    public void updatePassword(String account, String newPwd) {
        Connection conn = JDBCUtils.getConn();
        String sql = "update user set password=? where account=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPwd);
            ps.setString(2, account);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}