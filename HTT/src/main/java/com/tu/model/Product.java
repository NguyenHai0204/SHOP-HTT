package com.tu.model;


import lombok.Data;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 2,max = 40)
    private String name;
    @NotBlank
    @Size(min = 10)
    private String description;
    @NotBlank
    private String color;

    @Transient
    private CommonsMultipartFile[] imageMulti;

    private String image;

    @Min(value = 0)
    private double price;
    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column(name = "delete_date")
    private LocalDateTime deleteDate;
    @Column(name = "is_delete")
    private boolean isDelete;
    @Column(name = "restore_date")
    private LocalDateTime restoreDate ;

    private boolean status;
    @Min(value = 0)
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean deleted;


    @ManyToOne
    @JoinColumn(name = "create_id")
    private Customer customerCreate;
    @ManyToOne
    @JoinColumn(name = "update_id")
    private Customer customerUpdate;
    @ManyToOne
    @JoinColumn(name = "delete_id")
    private Customer customerDelete;
    @ManyToOne
    @JoinColumn(name = "restore_id")
    private Customer customerRestore;



    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

}
