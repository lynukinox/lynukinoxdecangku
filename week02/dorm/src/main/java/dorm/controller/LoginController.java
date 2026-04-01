package dorm.controller;

import dorm.common.Result;
import dorm.entity.User;
import dorm.mapper.UserMapper;
import dorm.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        User user = userMapper.findByUsernameAndPassword(username, password);

        if (user != null) {
            String token = JwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("userId", user.getId());
            data.put("username", user.getUsername());
            data.put("role", user.getRole());

            return Result.success(data);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
}