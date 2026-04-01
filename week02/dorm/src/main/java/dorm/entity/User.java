package dorm.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String dormBuilding;
    private String dormRoom;
}