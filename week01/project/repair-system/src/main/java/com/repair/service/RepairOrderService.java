package com.repair.service;

import com.repair.mapper.RepairOrderMapper;
import com.repair.pojo.RepairOrder;
import java.util.List;

public class RepairOrderService {
    private RepairOrderMapper orderMapper = new RepairOrderMapper();

    // 提交报修单
    public boolean addOrder(RepairOrder order) {
        if (order.getDormNo() == null || order.getDeviceType() == null || order.getDescription() == null) {
            return false;
        }
        orderMapper.addRepairOrder(order);
        return true;
    }

    // 学生查自己的单
    public List<RepairOrder> getMyOrders(String account) {
        return orderMapper.findOrderByUser(account);
    }

    // 管理员查所有单
    public List<RepairOrder> getAllOrders() {
        return orderMapper.findAllOrders();
    }

    // 管理员改状态
    public boolean updateStatus(Long id, String status) {
        if (!status.equals("待处理") && !status.equals("维修中") && !status.equals("已完成") && !status.equals("已取消")) {
            return false;
        }
        orderMapper.updateOrderStatus(id, status);
        return true;
    }
}