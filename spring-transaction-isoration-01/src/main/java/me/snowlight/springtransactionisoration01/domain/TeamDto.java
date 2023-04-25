package me.snowlight.springtransactionisoration01.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private Long id;
    private String name;
    private Integer memberCount;

    public Team toEntity() {
        return new Team(id, name, memberCount);
    }
}
