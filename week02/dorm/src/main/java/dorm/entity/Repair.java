package dorm.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Repair {
    private Integer id;
    private Integer userId;
    private String deviceType;
    private String description;
    private String imagePath;
    private String status;
    private LocalDateTime createTime;
}
