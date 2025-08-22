package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

public class UserPermissionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;
    private StringFilter id;
    private LongFilter userId;
    private StringFilter permissionId;

    public UserPermissionCriteria() {}

    public UserPermissionCriteria(UserPermissionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.permissionId = other.permissionId == null ? null : other.permissionId.copy();
    }

    @Override
    public UserPermissionCriteria copy() {
        return new UserPermissionCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public StringFilter getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(StringFilter permissionId) {
        this.permissionId = permissionId;
    }
}
