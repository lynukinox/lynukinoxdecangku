package com.repair.mapper;

import com.repair.pojo.RepairOrder;
import com.repair.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepairOrderMapper {
    // 新增报修单
    public void addRepairOrder(RepairOrder order) {
        Connection conn = JDBCUtils.getConn();
        String sql = "insert into repair_order(dorm_no,device_type,description,status,apply_user,apply_time) values(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, order.getDormNo());
            ps.setString(2, order.getDeviceType());
            ps.setString(3, order.getDescription());
            ps.setString(4, order.getStatus());
            ps.setString(5, order.getApplyUser());
            ps.setObject(6, order.getApplyTime());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 学生查自己的报修单
    public List<RepairOrder> findOrderByUser(String account) {
        List<RepairOrder> list = new ArrayList<>();
        Connection conn = JDBCUtils.getConn();
        String sql = "select * from repair_order where apply_user=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RepairOrder order = new RepairOrder();
                order.setId(rs.getLong("id"));
                order.setDormNo(rs.getString("dorm_no"));
                order.setDeviceType(rs.getString("device_type"));
                order.setDescription(rs.getString("description"));
                order.setStatus(rs.getString("status"));
                order.setApplyUser(rs.getString("apply_user"));
                order.setApplyTime(rs.getObject("apply_time", LocalDateTime.class));
                order.setUpdateTime(rs.getObject("update_time", LocalDateTime.class));
                list.add(order);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 管理员查所有报修单
    public List<RepairOrder> findAllOrders() {
        List<RepairOrder> list = new ArrayList<>();
        Connection conn = JDBCUtils.getConn();
        String sql = "select * from repair_order";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RepairOrder order = new RepairOrder();
                order.setId(rs.getLong("id"));
                order.setDormNo(rs.getString("dorm_no"));
                order.setDeviceType(rs.getString("device_type"));
                order.setDescription(rs.getString("description"));
                order.setStatus(rs.getString("status"));
                order.setApplyUser(rs.getString("apply_user"));
                order.setApplyTime(rs.getObject("apply_time", LocalDateTime.class));
                order.setUpdateTime(rs.getObject("update_time", LocalDateTime.class));
                list.add(order);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 管理员改报修单状态
    public void updateOrderStatus(Long orderId, String status) {
        Connection conn = JDBCUtils.getConn();
        String sql = "update repair_order set status=?,update_time=? where id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setObject(2, LocalDateTime.now());
            ps.setLong(3, orderId);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}