package com.candy.sweet.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account_table")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String full_name;
    private String phone_number;
    private String email;
    private String username;
    private String password;

    @OneToMany(mappedBy = "theAccount")
    private List<Candy> candyList;

}
