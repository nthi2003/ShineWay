package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.StringFilter;

public class TableCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter id;
    private StringFilter name;
    private IntegerFilter capacity;
    private IntegerFilter status;

    public TableCriteria() {
        // Default constructor
    }

    public TableCriteria(TableCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.capacity = other.capacity == null ? null : other.capacity.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public TableCriteria copy() {
        return new TableCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getCapacity() {
        return capacity;
    }

    public void setCapacity(IntegerFilter capacity) {
        this.capacity = capacity;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }
}
