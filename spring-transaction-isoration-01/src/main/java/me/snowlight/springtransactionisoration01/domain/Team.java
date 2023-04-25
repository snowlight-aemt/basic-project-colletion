package me.snowlight.springtransactionisoration01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

//@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
//    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer memberCount = 0;

    public Team(String name) {
        this.name = name;
    }

    public void increase() {
        this.memberCount = this.memberCount + 1;
    }

    public void renaming(String name) {
        this.name = name;
    }
}
