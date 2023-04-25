package com.fatec.starvingless.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFire implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String cpf;
    private String address;
    private String password;
    @Column(unique = true)
    private String email;
    private String phone;
    private String signUpDate;

//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<Post> posts = new ArrayList<>();


}
