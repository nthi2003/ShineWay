package com.mycompany.myapp.domain;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance")
public class Attendance extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "check_in")
    private LocalTime checkIn;
    @Column(name = "check_out")
    private LocalTime checkOut;
    @Column(name = "day")
    private LocalDate day;

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

   
    public void setDay(LocalDate day) {
        this.day = day;
    }
}
