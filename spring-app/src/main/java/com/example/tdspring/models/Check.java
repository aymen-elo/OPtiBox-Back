package com.example.tdspring.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "check")
@Table(name = "checks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Check implements Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // user reference who did the check
    private Integer status; //true: OK, false: NOK
    private String comment;

    @Override
    public String getType() {
        return "check";
    }
}
