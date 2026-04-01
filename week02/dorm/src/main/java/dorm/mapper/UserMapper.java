package dorm.mapper;

import dorm.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User findByUsernameAndPassword(String username, String password);

    @Update("UPDATE user SET dorm_building = #{dormBuilding}, dorm_room = #{dormRoom} WHERE id = #{id}")
    int updateById(User user);
}