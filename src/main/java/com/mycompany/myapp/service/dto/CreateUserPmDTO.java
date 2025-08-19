package com.mycompany.myapp.service.dto;

import java.io.Serializable;

public class CreateUserPmDTO implements Serializable {
    private Long userId;
    private String permissionId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
