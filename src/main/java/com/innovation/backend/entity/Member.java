package com.innovation.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cart> carts;
}
