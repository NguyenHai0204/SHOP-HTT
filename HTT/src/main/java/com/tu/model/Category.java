package com.tu.model;


import lombok.Data;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2,max = 40)
    private String name;

    @NotBlank
    @Size(min = 10)
    private String description;
    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate ;
    @Column(name = "restore_date")
    private LocalDateTime restoreDate ;


    @OneToMany(mappedBy = "category")
    private List<Product> products;

    private CommonsMultipartFile image;


    private boolean deleted;

    @Override
    public String toString(){
        return this.getName();
    }

}
