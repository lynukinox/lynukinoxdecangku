package com.repair.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtils {
    // 改成你的MySQL账号密码
    private static final String USER = "root";
    private static final String PWD = "123456";

    public static Connection getConn() {
        Connection conn = null;
        try {
            // 强制加载驱动（避免IDEA没自动加载）
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 兼容MySQL8.0的URL（加了时区/编码/allowPublicKeyRetrieval）
            String url = "jdbc:mysql://localhost:3306/dorm_repair?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&allowPublicKeyRetrieval=true";
            conn = DriverManager.getConnection(url, USER, PWD);
            System.out.println("数据库连接成功！"); // 加日志，验证是否连得上
        } catch (Exception e) {
            System.out.println("数据库连接失败：" + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}