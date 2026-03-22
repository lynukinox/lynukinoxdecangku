package com.repair;

import com.repair.pojo.User;
import com.repair.pojo.RepairOrder;
import com.repair.service.UserService;
import com.repair.service.RepairOrderService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static UserService userService = new UserService();
    private static RepairOrderService orderService = new RepairOrderService();
    private static Scanner sc = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("===== 宿舍报修管理系统 =====");
            System.out.println("1. 注册");
            System.out.println("2. 登录");
            System.out.println("0. 退出");
            System.out.print("请选择：");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 0:
                    System.out.println("退出系统！");
                    return;
                default:
                    System.out.println("输入错误！");
            }
        }
    }

    // 注册
    private static void register() {
        System.out.println("===== 注册 =====");
        System.out.print("角色（1-学生 2-管理员）：");
        int roleChoice = sc.nextInt();
        sc.nextLine();
        String role = roleChoice == 1 ? "student" : "admin";

        System.out.print("账号：");
        String account = sc.nextLine();
        System.out.print("密码：");
        String pwd = sc.nextLine();
        System.out.print("确认密码：");
        String pwd2 = sc.nextLine();

        if (!pwd.equals(pwd2)) {
            System.out.println("两次密码不一致！");
            return;
        }

        User user = new User();
        user.setAccount(account);
        user.setPassword(pwd);
        user.setRole(role);
        if (role.equals("student")) {
            System.out.print("宿舍号：");
            user.setDormNo(sc.nextLine());
        }

        boolean res = userService.register(user);
        System.out.println(res ? "注册成功！" : "注册失败（账号格式/密码长度）！");
    }

    // 登录
    private static void login() {
        System.out.println("===== 登录 =====");
        System.out.print("账号：");
        String account = sc.nextLine();
        System.out.print("密码：");
        String pwd = sc.nextLine();

        currentUser = userService.login(account, pwd);
        if (currentUser != null) {
            System.out.println("登录成功！");
            if (currentUser.getRole().equals("student")) {
                studentMenu();
            } else {
                adminMenu();
            }
        } else {
            System.out.println("账号/密码错误！");
        }
    }

    // 学生菜单
    private static void studentMenu() {
        while (true) {
            System.out.println("===== 学生菜单 =====");
            System.out.println("1. 提交报修单");
            System.out.println("2. 查看我的报修单");
            System.out.println("3. 修改密码");
            System.out.println("0. 退出登录");
            System.out.print("选择：");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    RepairOrder order = new RepairOrder();
                    order.setDormNo(currentUser.getDormNo());
                    System.out.print("设备类型（水电/家具/网络）：");
                    order.setDeviceType(sc.nextLine());
                    System.out.print("问题描述：");
                    order.setDescription(sc.nextLine());
                    order.setStatus("待处理");
                    order.setApplyUser(currentUser.getAccount());
                    order.setApplyTime(LocalDateTime.now());
                    System.out.println(orderService.addOrder(order) ? "提交成功！" : "提交失败！");
                    break;
                case 2:
                    List<RepairOrder> myOrders = orderService.getMyOrders(currentUser.getAccount());
                    if (myOrders.isEmpty()) {
                        System.out.println("暂无报修单！");
                    } else {
                        for (RepairOrder o : myOrders) {
                            System.out.println("ID：" + o.getId() + " | 类型：" + o.getDeviceType() + " | 状态：" + o.getStatus());
                        }
                    }
                    break;
                case 3:
                    System.out.print("旧密码：");
                    String oldPwd = sc.nextLine();
                    System.out.print("新密码：");
                    String newPwd = sc.nextLine();
                    System.out.println(userService.updatePwd(currentUser.getAccount(), oldPwd, newPwd) ? "密码修改成功！" : "修改失败！");
                    break;
                case 0:
                    currentUser = null;
                    System.out.println("退出登录！");
                    return;
                default:
                    System.out.println("输入错误！");
            }
        }
    }

    // 管理员菜单
    private static void adminMenu() {
        while (true) {
            System.out.println("===== 管理员菜单 =====");
            System.out.println("1. 查看所有报修单");
            System.out.println("2. 修改报修单状态");
            System.out.println("3. 修改密码");
            System.out.println("0. 退出登录");
            System.out.print("选择：");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    List<RepairOrder> allOrders = orderService.getAllOrders();
                    if (allOrders.isEmpty()) {
                        System.out.println("暂无报修单！");
                    } else {
                        for (RepairOrder o : allOrders) {
                            System.out.println("ID：" + o.getId() + " | 宿舍：" + o.getDormNo() + " | 状态：" + o.getStatus());
                        }
                    }
                    break;
                case 2:
                    System.out.print("报修单ID：");
                    Long id = sc.nextLong();
                    sc.nextLine();
                    System.out.print("新状态（待处理/维修中/已完成/已取消）：");
                    String status = sc.nextLine();
                    System.out.println(orderService.updateStatus(id, status) ? "状态修改成功！" : "修改失败！");
                    break;
                case 3:
                    System.out.print("旧密码：");
                    String oldPwd = sc.nextLine();
                    System.out.print("新密码：");
                    String newPwd = sc.nextLine();
                    System.out.println(userService.updatePwd(currentUser.getAccount(), oldPwd, newPwd) ? "密码修改成功！" : "修改失败！");
                    break;
                case 0:
                    currentUser = null;
                    System.out.println("退出登录！");
                    return;
                default:
                    System.out.println("输入错误！");
            }
        }
    }
}