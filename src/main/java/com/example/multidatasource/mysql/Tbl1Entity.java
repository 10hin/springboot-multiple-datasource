package com.example.multidatasource.mysql;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl1")
@Data
public class Tbl1Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contents;

    @Column(unique = true)
    private Integer uk;

}
