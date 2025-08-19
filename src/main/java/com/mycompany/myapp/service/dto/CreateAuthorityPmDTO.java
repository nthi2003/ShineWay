package com.mycompany.myapp.service.dto;

import java.io.Serializable;

public class CreateAuthorityPmDTO implements Serializable {
    private String authorityName;
    private String permissionId;


    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
