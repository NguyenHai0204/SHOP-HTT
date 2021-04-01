package com.tu.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "expected_date")
    private LocalDateTime expectedDate;

    @Column(name = "reality_date")
    private LocalDateTime realityDate;
    @NotEmpty
    private String address;

    private String phone;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderDetail> orderDetails;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", createDate=" + createDate +
                ", expectedDate=" + expectedDate +
                ", realityDate=" + realityDate +
                ", address='" + address + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
