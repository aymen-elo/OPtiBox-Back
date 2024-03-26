package com.example.tdspring.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "stock")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product; // product reference
    private Boolean available; //true: available, false: not available
    private Integer status; //0: NOK, 1: OK, 2: HS
    private Date creationDate; //date of creation



}
