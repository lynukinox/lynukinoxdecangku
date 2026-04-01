package dorm.controller;

import dorm.common.Result;
import dorm.entity.Repair;
import dorm.mapper.RepairMapper;
import dorm.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private RepairMapper repairMapper;

    @GetMapping("/repairs")
    public Result<List<Repair>> getAllRepairs(@RequestParam(required = false) String status,
                                              HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String role = JwtUtil.getRole(token);

        if (!"admin".equals(role)) {
            return Result.error(403, "无权限");
        }

        List<Repair> list;
        if (status != null && !status.isEmpty()) {
            list = repairMapper.findRepairsByStatus(status);
        } else {
            list = repairMapper.findAllRepairs();
        }

        return Result.success(list);
    }

    @PutMapping("/repair/status")
    public Result<String> updateStatus(@RequestBody Map<String, Object> params,
                                       HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String role = JwtUtil.getRole(token);

        if (!"admin".equals(role)) {
            return Result.error(403, "无权限");
        }

        Integer id = (Integer) params.get("id");
        String status = (String) params.get("status");

        int rows = repairMapper.updateStatus(id, status);

        if (rows > 0) {
            return Result.success("状态修改成功", null);
        } else {
            return Result.error("修改失败");
        }
    }

    @DeleteMapping("/repair/{id}")
    public Result<String> deleteRepair(@PathVariable Integer id,
                                       HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String role = JwtUtil.getRole(token);

        if (!"admin".equals(role)) {
            return Result.error(403, "无权限");
        }

        int rows = repairMapper.deleteById(id);

        if (rows > 0) {
            return Result.success("删除成功", null);
        } else {
            return Result.error("删除失败");
        }
    }
}