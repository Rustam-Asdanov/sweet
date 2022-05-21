package com.candy.sweet.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "candy_table")
@Data
public class Candy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String category;
    private String price;
    private String imageLink;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name = "account_id")
    private Account theAccount;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinTable(
            name = "order_candy",
            joinColumns = @JoinColumn(name = "candy_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Order> orderList;

    @Override
    public String toString() {
        return "Candy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                ", imageLink='" + imageLink + '\'' +
                '}';
    }
}
