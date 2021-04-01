package com.tu.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String configPassword;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String gender;
    @NotEmpty
    private String phoneNumber;

    @Transient
    private CommonsMultipartFile[] imageMulti;

    private String image;
    @NotEmpty
    private String dateOfBirth;

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Column(name = "restore_date")
    private LocalDateTime restoreDate;

    private boolean deleted;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "customerUpdate")
    private List<Product> productUpdate;

    @OneToMany(mappedBy = "customerDelete")
    private List<Product> productDelete;

    @OneToMany(mappedBy = "customerRestore")
    private List<Product> productRestore;

    @OneToMany(mappedBy = "customerCreate")
    private List<Product> productCreate;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", configPassword='" + configPassword + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", image='" + image + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", deleteDate=" + deleteDate +
                ", restoreDate=" + restoreDate +
                ", deleted=" + deleted +
                '}';
    }
}
