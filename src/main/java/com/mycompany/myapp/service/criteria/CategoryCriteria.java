package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.StringFilter;

public class CategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter id;
    private StringFilter name;


    public CategoryCriteria() {}

    public CategoryCriteria(CategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();

    }

    @Override
    public CategoryCriteria copy() {
        return new CategoryCriteria(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryCriteria)) return false;
        final CategoryCriteria that = (CategoryCriteria) o;
        return Objects.equals(id, that.id)
            && Objects.equals(name, that.name);
 
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CategoryCriteria{" +
            "id=" + id +
            ", name=" + name +
            '}';
    }
}