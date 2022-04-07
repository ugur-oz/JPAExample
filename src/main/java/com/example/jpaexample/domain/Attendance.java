package com.example.jpaexample.domain;



import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table (name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}