package me.snowlight.springdistribute.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "friend")
@ToString
@NoArgsConstructor
public class Friend {
    @Id
    private Long id;

    private String name;

    public Friend(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
