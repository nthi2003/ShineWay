package com.mycompany.myapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "position")
public class Position extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String positionId;

    private String name;
    private String description;

    public String getId() {
        return positionId;
    }

    public void setId(String id) {
        this.positionId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Position() {
        // Default constructor
    }

    public Position(String positionId, String name, String description) {
        this.positionId = positionId;
        this.name = name;
        this.description = description;
    }
}
