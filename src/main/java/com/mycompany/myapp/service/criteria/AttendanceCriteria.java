package com.mycompany.myapp.service.criteria;

import java.io.Serializable;

import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import com.mycompany.myapp.service.filter.LocalTimeFilter;

public class AttendanceCriteria implements Serializable , Criteria {
    private static final long serialVersionUID = 1L;
    private StringFilter id;
    private LongFilter userId;
    private LocalTimeFilter checkIn;
    private LocalTimeFilter checkOut;   
    private StringFilter note;
    private LocalDateFilter day;
    
    public AttendanceCriteria() {}

    public AttendanceCriteria(AttendanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.checkIn = other.checkIn == null ? null : other.checkIn.copy();
        this.checkOut = other.checkOut == null ? null : other.checkOut.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.day = other.day == null ? null : other.day.copy();
    }

    @Override
    public AttendanceCriteria copy() {
        return new AttendanceCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LocalTimeFilter getCheckIn() {
        return checkIn;
    }

    public LocalTimeFilter getCheckOut() {
        return checkOut;
    }

    public StringFilter getNote() {
        return note;
    }

    public LocalDateFilter getDay() {
        return day;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public void setCheckIn(LocalTimeFilter checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalTimeFilter checkOut) {
        this.checkOut = checkOut;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public void setDay(LocalDateFilter day) {
        this.day = day;
    }

}
