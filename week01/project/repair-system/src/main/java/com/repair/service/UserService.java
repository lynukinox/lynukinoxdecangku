package com.repair.service;

import com.repair.mapper.UserMapper;
import com.repair.pojo.User;

public class UserService {
    private UserMapper userMapper = new UserMapper();

    // 注册
    public boolean register(User user) {
        // 简单校验：账号开头规则
        String account = user.getAccount();
        if (!account.startsWith("3125") && !account.startsWith("3225") && !account.startsWith("0025")) {
            return false;
        }
        // 密码长度≥6
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return false;
        }
        userMapper.addUser(user);
        return true;
    }

    // 登录
    public User login(String account, String password) {
        User user = userMapper.getUserByAccount(account);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // 修改密码
    public boolean updatePwd(String account, String oldPwd, String newPwd) {
        User user = userMapper.getUserByAccount(account);
        if (user == null || !user.getPassword().equals(oldPwd)) {
            return false;
        }
        if (newPwd == null || newPwd.length() < 6) {
            return false;
        }
        userMapper.updatePassword(account, newPwd);
        return true;
    }
}