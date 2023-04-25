package me.snowlight.springtransactionisoration01.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class TeamCommand {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class RegisterTeam {
        private String name;

        public Team toEntity() {
            return new Team(this.name);
        }
    }
}
