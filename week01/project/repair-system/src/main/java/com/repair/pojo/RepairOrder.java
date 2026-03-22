package com.repair.pojo;

import java.time.LocalDateTime;

public class RepairOrder {
    private Long id;
    private String dormNo;
    private String deviceType;
    private String description;
    private String status;
    private String applyUser;
    private LocalDateTime applyTime;
    private LocalDateTime updateTime;

    public RepairOrder() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDormNo() { return dormNo; }
    public void setDormNo(String dormNo) { this.dormNo = dormNo; }

    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getApplyUser() { return applyUser; }
    public void setApplyUser(String applyUser) { this.applyUser = applyUser; }

    public LocalDateTime getApplyTime() { return applyTime; }
    public void setApplyTime(LocalDateTime applyTime) { this.applyTime = applyTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}