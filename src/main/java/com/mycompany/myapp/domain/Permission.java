package com.mycompany.myapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permission extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    // mã code để  VD như CATEGORY_CREATE
    @Column(name = "code")
    private String code;
    @Column(name = "resource")
    private String resource;
    @Column(name = "description")
    private String description;
    public String getId() {
        return id;
    }
    public void setId (String id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
