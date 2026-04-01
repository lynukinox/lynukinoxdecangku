package dorm.controller;

import dorm.common.Result;
import dorm.entity.Repair;
import dorm.entity.User;
import dorm.mapper.RepairMapper;
import dorm.mapper.UserMapper;
import dorm.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/repair")
public class RepairController {

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private UserMapper userMapper;

    // 绑定宿舍
    @PutMapping("/bindDorm")
    public Result<String> bindDorm(@RequestBody Map<String, String> request,
                                   HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").substring(7);
        Integer userId = JwtUtil.getUserId(token);

        String dormBuilding = request.get("dormBuilding");
        String dormRoom = request.get("dormRoom");

        User user = new User();
        user.setId(userId);
        user.setDormBuilding(dormBuilding);
        user.setDormRoom(dormRoom);

        int rows = userMapper.updateById(user);

        if (rows > 0) {
            return Result.success("绑定成功", null);
        } else {
            return Result.error("绑定失败");
        }
    }

    // 创建报修单
    @PostMapping("/create")
    public Result<Integer> createRepair(@RequestParam String deviceType,
                                        @RequestParam String description,
                                        @RequestParam(required = false) MultipartFile image,
                                        HttpServletRequest httpRequest) throws IOException {
        String token = httpRequest.getHeader("Authorization").substring(7);
        Integer userId = JwtUtil.getUserId(token);

        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File destFile = new File(uploadDir + fileName);
            image.transferTo(destFile);
            imagePath = "/uploads/" + fileName;
        }

        Repair repair = new Repair();
        repair.setUserId(userId);
        repair.setDeviceType(deviceType);
        repair.setDescription(description);
        repair.setImagePath(imagePath);
        repair.setStatus("pending");
        repair.setCreateTime(LocalDateTime.now());

        repairMapper.insert(repair);

        return Result.success("报修单创建成功", repair.getId());
    }

    // 获取我的报修列表
    @GetMapping("/myList")
    public Result<List<Repair>> getMyRepairList(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").substring(7);
        Integer userId = JwtUtil.getUserId(token);

        List<Repair> list = repairMapper.findByUserId(userId);

        return Result.success(list);
    }

    // 取消报修
    @PutMapping("/cancel/{id}")
    public Result<String> cancelRepair(@PathVariable Integer id,
                                       HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").substring(7);
        Integer userId = JwtUtil.getUserId(token);

        int rows = repairMapper.cancelRepair(id, userId);

        if (rows > 0) {
            return Result.success("取消成功", null);
        } else {
            return Result.error("取消失败，只有待处理的报修单可以取消");
        }
    }
}