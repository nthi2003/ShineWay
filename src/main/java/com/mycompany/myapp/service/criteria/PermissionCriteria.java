package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.StringFilter;

public class PermissionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;
    private StringFilter id;
    private StringFilter code;
    private StringFilter resource;
    private StringFilter description;

    public PermissionCriteria() {}

    public PermissionCriteria(PermissionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.resource = other.resource == null ? null : other.resource.copy();
        this.description = other.description == null ? null : other.description.copy();
    }

    @Override
    public PermissionCriteria copy() {
        return new PermissionCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getResource() {
        return resource;
    }

    public void setResource(StringFilter resource) {
        this.resource = resource;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }
}
