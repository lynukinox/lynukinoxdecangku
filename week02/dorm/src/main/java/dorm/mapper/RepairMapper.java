package dorm.mapper;

import dorm.entity.Repair;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface RepairMapper {

    // 插入报修单
    @Insert("INSERT INTO repair(user_id, device_type, description, image_path, status, create_time) " +
            "VALUES(#{userId}, #{deviceType}, #{description}, #{imagePath}, #{status}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Repair repair);

    // 查询用户的报修列表
    @Select("SELECT * FROM repair WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Repair> findByUserId(Integer userId);

    // 取消报修
    @Update("UPDATE repair SET status = 'cancelled' WHERE id = #{id} AND user_id = #{userId} AND status = 'pending'")
    int cancelRepair(@Param("id") Integer id, @Param("userId") Integer userId);

    // ↓↓↓ 添加这3个管理员方法 ↓↓↓

    // 管理员：查询所有报修单
    @Select("SELECT * FROM repair ORDER BY create_time DESC")
    List<Repair> findAllRepairs();

    // 管理员：按状态筛选
    @Select("SELECT * FROM repair WHERE status = #{status} ORDER BY create_time DESC")
    List<Repair> findRepairsByStatus(String status);

    // 管理员：修改状态
    @Update("UPDATE repair SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") String status);

    // 管理员：删除报修单
    @Delete("DELETE FROM repair WHERE id = #{id}")
    int deleteById(Integer id);
}