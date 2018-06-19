package com.example.multidatasource.postgresql;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl2")
@Data
public class Tbl2Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl2_id_generator")
    @SequenceGenerator(name = "tbl2_id_generator", sequenceName = "tbl2_id_seq")
    private Long id;

    private String contents;

    @Column(unique = true)
    private Integer uk;

}
