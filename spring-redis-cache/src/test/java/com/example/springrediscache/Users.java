package com.example.springrediscache;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Users(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }
}
